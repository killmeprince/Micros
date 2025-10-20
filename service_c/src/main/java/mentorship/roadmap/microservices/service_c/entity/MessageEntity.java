package mentorship.roadmap.microservices.service_c.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "messages")
public class MessageEntity {
    @Id
    private String id;

    private String type;

    @Column(columnDefinition = "text")
    private String payload;

    private long ts;



}
