package mentorship.roadmap.microservices.service_a.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessagePayload {

    private String id;
    private String type;
    private String payload;
    private long ts;


}
