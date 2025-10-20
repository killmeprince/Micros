package mentorship.roadmap.microservices.service_b.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_b.dto.MessagePayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class ProcessController {
    private final StringRedisTemplate redis;
    @Value("${app.c-url}") String cUrl;
    private final RestClient http;

    @PostMapping("/process")
    public ResponseEntity<Void> process(@RequestBody MessagePayload msg){
        if ("important".equalsIgnoreCase(msg.getType())) {
            redis.opsForValue().set("imp:" + msg.getId(), msg.getPayload(), Duration.ofMinutes(5));
            log.info("B: cached important id={} TTL=5m", msg.getId());
        }
        http.post().uri(cUrl + "/api/save").body(msg).retrieve().toBodilessEntity();
        log.info("B: forwarded to C");
        return ResponseEntity.accepted().build();
    }
}
