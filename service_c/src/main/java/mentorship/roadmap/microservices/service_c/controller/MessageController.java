package mentorship.roadmap.microservices.service_c.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_c.entity.MessageEntity;
import mentorship.roadmap.microservices.service_c.entity.MessagePayload;
import mentorship.roadmap.microservices.service_c.repository.MessageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MessageController {
    private final MessageRepository repository;
    private final KafkaTemplate<String, MessagePayload> kafka;

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody MessagePayload msg) {
        repository.save(toEntity(msg));
        log.info("C: saved to Postgres id={}", msg.getId());
        kafka.send("out", msg.getId(), msg);
        log.info("C: published to Kafka 'out'");
        return ResponseEntity.accepted().build();

    }

    private MessageEntity toEntity(MessagePayload m){
        MessageEntity e = new MessageEntity();
        e.setId(m.getId()); e.setType(m.getType()); e.setPayload(m.getPayload()); e.setTs(m.getTs());
        return e;
    }
}
