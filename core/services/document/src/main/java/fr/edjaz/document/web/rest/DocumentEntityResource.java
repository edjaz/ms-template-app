package fr.edjaz.document.web.rest;
import fr.edjaz.document.service.DocumentEntityService;
import fr.edjaz.document.web.rest.errors.BadRequestAlertException;
import fr.edjaz.document.web.rest.util.HeaderUtil;
import fr.edjaz.document.web.rest.util.PaginationUtil;
import fr.edjaz.document.service.dto.DocumentEntityDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DocumentEntity.
 */
@RestController
@RequestMapping("/api")
public class DocumentEntityResource {

    private final Logger log = LoggerFactory.getLogger(DocumentEntityResource.class);

    private static final String ENTITY_NAME = "documentDocumentEntity";

    private final DocumentEntityService documentEntityService;

    public DocumentEntityResource(DocumentEntityService documentEntityService) {
        this.documentEntityService = documentEntityService;
    }

    /**
     * POST  /document-entities : Create a new documentEntity.
     *
     * @param documentEntityDTO the documentEntityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentEntityDTO, or with status 400 (Bad Request) if the documentEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/document-entities")
    public ResponseEntity<DocumentEntityDTO> createDocumentEntity(@Valid @RequestBody DocumentEntityDTO documentEntityDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentEntity : {}", documentEntityDTO);
        if (documentEntityDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentEntityDTO result = documentEntityService.save(documentEntityDTO);
        return ResponseEntity.created(new URI("/api/document-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /document-entities : Updates an existing documentEntity.
     *
     * @param documentEntityDTO the documentEntityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentEntityDTO,
     * or with status 400 (Bad Request) if the documentEntityDTO is not valid,
     * or with status 500 (Internal Server Error) if the documentEntityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/document-entities")
    public ResponseEntity<DocumentEntityDTO> updateDocumentEntity(@Valid @RequestBody DocumentEntityDTO documentEntityDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentEntity : {}", documentEntityDTO);
        if (documentEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentEntityDTO result = documentEntityService.save(documentEntityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /document-entities : get all the documentEntities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of documentEntities in body
     */
    @GetMapping("/document-entities")
    public ResponseEntity<List<DocumentEntityDTO>> getAllDocumentEntities(Pageable pageable) {
        log.debug("REST request to get a page of DocumentEntities");
        Page<DocumentEntityDTO> page = documentEntityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/document-entities");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /document-entities/:id : get the "id" documentEntity.
     *
     * @param id the id of the documentEntityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documentEntityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/document-entities/{id}")
    public ResponseEntity<DocumentEntityDTO> getDocumentEntity(@PathVariable String id) {
        log.debug("REST request to get DocumentEntity : {}", id);
        Optional<DocumentEntityDTO> documentEntityDTO = documentEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentEntityDTO);
    }

    /**
     * DELETE  /document-entities/:id : delete the "id" documentEntity.
     *
     * @param id the id of the documentEntityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/document-entities/{id}")
    public ResponseEntity<Void> deleteDocumentEntity(@PathVariable String id) {
        log.debug("REST request to delete DocumentEntity : {}", id);
        documentEntityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/document-entities?query=:query : search for the documentEntity corresponding
     * to the query.
     *
     * @param query the query of the documentEntity search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/document-entities")
    public ResponseEntity<List<DocumentEntityDTO>> searchDocumentEntities(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DocumentEntities for query {}", query);
        Page<DocumentEntityDTO> page = documentEntityService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/document-entities");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
