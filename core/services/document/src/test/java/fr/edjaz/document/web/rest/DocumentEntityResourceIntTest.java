package fr.edjaz.document.web.rest;

import fr.edjaz.document.DocumentApp;

import fr.edjaz.document.config.SecurityBeanOverrideConfiguration;

import fr.edjaz.document.domain.DocumentEntity;
import fr.edjaz.document.repository.DocumentEntityRepository;
import fr.edjaz.document.repository.search.DocumentEntitySearchRepository;
import fr.edjaz.document.service.DocumentEntityService;
import fr.edjaz.document.service.dto.DocumentEntityDTO;
import fr.edjaz.document.service.mapper.DocumentEntityMapper;
import fr.edjaz.document.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.util.Collections;
import java.util.List;


import static fr.edjaz.document.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DocumentEntityResource REST controller.
 *
 * @see DocumentEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, DocumentApp.class})
public class DocumentEntityResourceIntTest {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_RESUME = "AAAAAAAAAA";
    private static final String UPDATED_RESUME = "BBBBBBBBBB";

    @Autowired
    private DocumentEntityRepository documentEntityRepository;

    @Autowired
    private DocumentEntityMapper documentEntityMapper;

    @Autowired
    private DocumentEntityService documentEntityService;

    /**
     * This repository is mocked in the fr.edjaz.document.repository.search test package.
     *
     * @see fr.edjaz.document.repository.search.DocumentEntitySearchRepositoryMockConfiguration
     */
    @Autowired
    private DocumentEntitySearchRepository mockDocumentEntitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restDocumentEntityMockMvc;

    private DocumentEntity documentEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentEntityResource documentEntityResource = new DocumentEntityResource(documentEntityService);
        this.restDocumentEntityMockMvc = MockMvcBuilders.standaloneSetup(documentEntityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentEntity createEntity() {
        DocumentEntity documentEntity = new DocumentEntity()
            .text(DEFAULT_TEXT)
            .resume(DEFAULT_RESUME);
        return documentEntity;
    }

    @Before
    public void initTest() {
        documentEntityRepository.deleteAll();
        documentEntity = createEntity();
    }

    @Test
    public void createDocumentEntity() throws Exception {
        int databaseSizeBeforeCreate = documentEntityRepository.findAll().size();

        // Create the DocumentEntity
        DocumentEntityDTO documentEntityDTO = documentEntityMapper.toDto(documentEntity);
        restDocumentEntityMockMvc.perform(post("/api/document-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentEntityDTO)))
            .andExpect(status().isCreated());

        // Validate the DocumentEntity in the database
        List<DocumentEntity> documentEntityList = documentEntityRepository.findAll();
        assertThat(documentEntityList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentEntity testDocumentEntity = documentEntityList.get(documentEntityList.size() - 1);
        assertThat(testDocumentEntity.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testDocumentEntity.getResume()).isEqualTo(DEFAULT_RESUME);

        // Validate the DocumentEntity in Elasticsearch
        verify(mockDocumentEntitySearchRepository, times(1)).save(testDocumentEntity);
    }

    @Test
    public void createDocumentEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentEntityRepository.findAll().size();

        // Create the DocumentEntity with an existing ID
        documentEntity.setId("existing_id");
        DocumentEntityDTO documentEntityDTO = documentEntityMapper.toDto(documentEntity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentEntityMockMvc.perform(post("/api/document-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentEntity in the database
        List<DocumentEntity> documentEntityList = documentEntityRepository.findAll();
        assertThat(documentEntityList).hasSize(databaseSizeBeforeCreate);

        // Validate the DocumentEntity in Elasticsearch
        verify(mockDocumentEntitySearchRepository, times(0)).save(documentEntity);
    }

    @Test
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentEntityRepository.findAll().size();
        // set the field null
        documentEntity.setText(null);

        // Create the DocumentEntity, which fails.
        DocumentEntityDTO documentEntityDTO = documentEntityMapper.toDto(documentEntity);

        restDocumentEntityMockMvc.perform(post("/api/document-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentEntityDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentEntity> documentEntityList = documentEntityRepository.findAll();
        assertThat(documentEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkResumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentEntityRepository.findAll().size();
        // set the field null
        documentEntity.setResume(null);

        // Create the DocumentEntity, which fails.
        DocumentEntityDTO documentEntityDTO = documentEntityMapper.toDto(documentEntity);

        restDocumentEntityMockMvc.perform(post("/api/document-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentEntityDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentEntity> documentEntityList = documentEntityRepository.findAll();
        assertThat(documentEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDocumentEntities() throws Exception {
        // Initialize the database
        documentEntityRepository.save(documentEntity);

        // Get all the documentEntityList
        restDocumentEntityMockMvc.perform(get("/api/document-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentEntity.getId())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].resume").value(hasItem(DEFAULT_RESUME.toString())));
    }
    
    @Test
    public void getDocumentEntity() throws Exception {
        // Initialize the database
        documentEntityRepository.save(documentEntity);

        // Get the documentEntity
        restDocumentEntityMockMvc.perform(get("/api/document-entities/{id}", documentEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentEntity.getId()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.resume").value(DEFAULT_RESUME.toString()));
    }

    @Test
    public void getNonExistingDocumentEntity() throws Exception {
        // Get the documentEntity
        restDocumentEntityMockMvc.perform(get("/api/document-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDocumentEntity() throws Exception {
        // Initialize the database
        documentEntityRepository.save(documentEntity);

        int databaseSizeBeforeUpdate = documentEntityRepository.findAll().size();

        // Update the documentEntity
        DocumentEntity updatedDocumentEntity = documentEntityRepository.findById(documentEntity.getId()).get();
        updatedDocumentEntity
            .text(UPDATED_TEXT)
            .resume(UPDATED_RESUME);
        DocumentEntityDTO documentEntityDTO = documentEntityMapper.toDto(updatedDocumentEntity);

        restDocumentEntityMockMvc.perform(put("/api/document-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentEntityDTO)))
            .andExpect(status().isOk());

        // Validate the DocumentEntity in the database
        List<DocumentEntity> documentEntityList = documentEntityRepository.findAll();
        assertThat(documentEntityList).hasSize(databaseSizeBeforeUpdate);
        DocumentEntity testDocumentEntity = documentEntityList.get(documentEntityList.size() - 1);
        assertThat(testDocumentEntity.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testDocumentEntity.getResume()).isEqualTo(UPDATED_RESUME);

        // Validate the DocumentEntity in Elasticsearch
        verify(mockDocumentEntitySearchRepository, times(1)).save(testDocumentEntity);
    }

    @Test
    public void updateNonExistingDocumentEntity() throws Exception {
        int databaseSizeBeforeUpdate = documentEntityRepository.findAll().size();

        // Create the DocumentEntity
        DocumentEntityDTO documentEntityDTO = documentEntityMapper.toDto(documentEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentEntityMockMvc.perform(put("/api/document-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentEntity in the database
        List<DocumentEntity> documentEntityList = documentEntityRepository.findAll();
        assertThat(documentEntityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DocumentEntity in Elasticsearch
        verify(mockDocumentEntitySearchRepository, times(0)).save(documentEntity);
    }

    @Test
    public void deleteDocumentEntity() throws Exception {
        // Initialize the database
        documentEntityRepository.save(documentEntity);

        int databaseSizeBeforeDelete = documentEntityRepository.findAll().size();

        // Delete the documentEntity
        restDocumentEntityMockMvc.perform(delete("/api/document-entities/{id}", documentEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DocumentEntity> documentEntityList = documentEntityRepository.findAll();
        assertThat(documentEntityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DocumentEntity in Elasticsearch
        verify(mockDocumentEntitySearchRepository, times(1)).deleteById(documentEntity.getId());
    }

    @Test
    public void searchDocumentEntity() throws Exception {
        // Initialize the database
        documentEntityRepository.save(documentEntity);
        when(mockDocumentEntitySearchRepository.search(queryStringQuery("id:" + documentEntity.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(documentEntity), PageRequest.of(0, 1), 1));
        // Search the documentEntity
        restDocumentEntityMockMvc.perform(get("/api/_search/document-entities?query=id:" + documentEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentEntity.getId())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].resume").value(hasItem(DEFAULT_RESUME)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentEntity.class);
        DocumentEntity documentEntity1 = new DocumentEntity();
        documentEntity1.setId("id1");
        DocumentEntity documentEntity2 = new DocumentEntity();
        documentEntity2.setId(documentEntity1.getId());
        assertThat(documentEntity1).isEqualTo(documentEntity2);
        documentEntity2.setId("id2");
        assertThat(documentEntity1).isNotEqualTo(documentEntity2);
        documentEntity1.setId(null);
        assertThat(documentEntity1).isNotEqualTo(documentEntity2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentEntityDTO.class);
        DocumentEntityDTO documentEntityDTO1 = new DocumentEntityDTO();
        documentEntityDTO1.setId("id1");
        DocumentEntityDTO documentEntityDTO2 = new DocumentEntityDTO();
        assertThat(documentEntityDTO1).isNotEqualTo(documentEntityDTO2);
        documentEntityDTO2.setId(documentEntityDTO1.getId());
        assertThat(documentEntityDTO1).isEqualTo(documentEntityDTO2);
        documentEntityDTO2.setId("id2");
        assertThat(documentEntityDTO1).isNotEqualTo(documentEntityDTO2);
        documentEntityDTO1.setId(null);
        assertThat(documentEntityDTO1).isNotEqualTo(documentEntityDTO2);
    }
}
