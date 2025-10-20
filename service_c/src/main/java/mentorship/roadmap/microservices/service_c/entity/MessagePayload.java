package mentorship.roadmap.microservices.service_c.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class MessagePayload {

    private String id;
    private String type;
    private String payload;
    private long ts;
}