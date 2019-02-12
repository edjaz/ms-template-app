package fr.edjaz.document.service;

import fr.edjaz.document.service.dto.DocumentEntityDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DocumentEntity.
 */
public interface DocumentEntityService {

    /**
     * Save a documentEntity.
     *
     * @param documentEntityDTO the entity to save
     * @return the persisted entity
     */
    DocumentEntityDTO save(DocumentEntityDTO documentEntityDTO);

    /**
     * Get all the documentEntities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DocumentEntityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" documentEntity.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DocumentEntityDTO> findOne(String id);

    /**
     * Delete the "id" documentEntity.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the documentEntity corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DocumentEntityDTO> search(String query, Pageable pageable);
}
