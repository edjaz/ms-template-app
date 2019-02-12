package fr.edjaz.document.service.impl;

import fr.edjaz.document.service.DocumentEntityService;
import fr.edjaz.document.domain.DocumentEntity;
import fr.edjaz.document.repository.DocumentEntityRepository;
import fr.edjaz.document.repository.search.DocumentEntitySearchRepository;
import fr.edjaz.document.service.dto.DocumentEntityDTO;
import fr.edjaz.document.service.mapper.DocumentEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DocumentEntity.
 */
@Service
public class DocumentEntityServiceImpl implements DocumentEntityService {

    private final Logger log = LoggerFactory.getLogger(DocumentEntityServiceImpl.class);

    private final DocumentEntityRepository documentEntityRepository;

    private final DocumentEntityMapper documentEntityMapper;

    private final DocumentEntitySearchRepository documentEntitySearchRepository;

    public DocumentEntityServiceImpl(DocumentEntityRepository documentEntityRepository, DocumentEntityMapper documentEntityMapper, DocumentEntitySearchRepository documentEntitySearchRepository) {
        this.documentEntityRepository = documentEntityRepository;
        this.documentEntityMapper = documentEntityMapper;
        this.documentEntitySearchRepository = documentEntitySearchRepository;
    }

    /**
     * Save a documentEntity.
     *
     * @param documentEntityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DocumentEntityDTO save(DocumentEntityDTO documentEntityDTO) {
        log.debug("Request to save DocumentEntity : {}", documentEntityDTO);
        DocumentEntity documentEntity = documentEntityMapper.toEntity(documentEntityDTO);
        documentEntity = documentEntityRepository.save(documentEntity);
        DocumentEntityDTO result = documentEntityMapper.toDto(documentEntity);
        documentEntitySearchRepository.save(documentEntity);
        return result;
    }

    /**
     * Get all the documentEntities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<DocumentEntityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentEntities");
        return documentEntityRepository.findAll(pageable)
            .map(documentEntityMapper::toDto);
    }


    /**
     * Get one documentEntity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<DocumentEntityDTO> findOne(String id) {
        log.debug("Request to get DocumentEntity : {}", id);
        return documentEntityRepository.findById(id)
            .map(documentEntityMapper::toDto);
    }

    /**
     * Delete the documentEntity by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete DocumentEntity : {}", id);        documentEntityRepository.deleteById(id);
        documentEntitySearchRepository.deleteById(id);
    }

    /**
     * Search for the documentEntity corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<DocumentEntityDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DocumentEntities for query {}", query);
        return documentEntitySearchRepository.search(queryStringQuery(query), pageable)
            .map(documentEntityMapper::toDto);
    }
}
