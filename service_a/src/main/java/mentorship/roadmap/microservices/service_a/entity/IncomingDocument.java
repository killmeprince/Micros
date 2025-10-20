package mentorship.roadmap.microservices.service_a.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("in")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IncomingDocument {
    @Id
    private String id;
    private String type;
    private String payload;
    private long ts;
}
