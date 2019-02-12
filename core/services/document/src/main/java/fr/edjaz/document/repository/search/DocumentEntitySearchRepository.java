package fr.edjaz.document.repository.search;

import fr.edjaz.document.domain.DocumentEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DocumentEntity entity.
 */
public interface DocumentEntitySearchRepository extends ElasticsearchRepository<DocumentEntity, String> {
}
