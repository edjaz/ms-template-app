package fr.edjaz.document.repository;

import fr.edjaz.document.domain.DocumentEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the DocumentEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentEntityRepository extends MongoRepository<DocumentEntity, String> {

}
