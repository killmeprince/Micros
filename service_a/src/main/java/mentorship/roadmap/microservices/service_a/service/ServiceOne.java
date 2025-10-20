package mentorship.roadmap.microservices.service_a.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_a.entity.IncomingDocument;
import mentorship.roadmap.microservices.service_a.entity.MessagePayload;
import mentorship.roadmap.microservices.service_a.repository.InRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceOne {
    private final InRepository repository;
    @Value("${app.b-url}")
    private String bUrl;
    private final RestClient http;


    @KafkaListener(topics = "in", groupId = "service_a", containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(MessagePayload payload){
        repository.save(toDoc(payload));
        log.info("A: saved to Mongo id={}", payload.getId());
        http.post().uri(bUrl + "/api/process").body(payload).retrieve().toBodilessEntity();
        log.info("A: forwarded to B");

    }
    private IncomingDocument toDoc(MessagePayload m){
        IncomingDocument d = new IncomingDocument();
        d.setId(m.getId()); d.setType(m.getType()); d.setPayload(m.getPayload()); d.setTs(m.getTs());
        return d;
    }
}
