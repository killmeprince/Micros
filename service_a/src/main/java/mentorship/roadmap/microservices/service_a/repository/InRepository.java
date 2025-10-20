package mentorship.roadmap.microservices.service_a.repository;

import mentorship.roadmap.microservices.service_a.entity.IncomingDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InRepository extends MongoRepository<IncomingDocument, String > {
}
