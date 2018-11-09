package ch.sebooom.web.rest;

import ch.sebooom.SebHipsterApp;

import ch.sebooom.domain.TestEntity;
import ch.sebooom.repository.TestEntityRepository;
import ch.sebooom.service.TestEntityService;
import ch.sebooom.service.dto.TestEntityDTO;
import ch.sebooom.service.mapper.TestEntityMapper;
import ch.sebooom.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static ch.sebooom.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TestEntityResource REST controller.
 *
 * @see TestEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SebHipsterApp.class)
public class TestEntityResourceIntTest {

    private static final String DEFAULT_TEST = "AAAAAAAAAA";
    private static final String UPDATED_TEST = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private TestEntityRepository testEntityRepository;

    @Autowired
    private TestEntityMapper testEntityMapper;

    @Autowired
    private TestEntityService testEntityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTestEntityMockMvc;

    private TestEntity testEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TestEntityResource testEntityResource = new TestEntityResource(testEntityService);
        this.restTestEntityMockMvc = MockMvcBuilders.standaloneSetup(testEntityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestEntity createEntity(EntityManager em) {
        TestEntity testEntity = new TestEntity()
            .test(DEFAULT_TEST)
            .desc(DEFAULT_DESC);
        return testEntity;
    }

    @Before
    public void initTest() {
        testEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createTestEntity() throws Exception {
        int databaseSizeBeforeCreate = testEntityRepository.findAll().size();

        // Create the TestEntity
        TestEntityDTO testEntityDTO = testEntityMapper.toDto(testEntity);
        restTestEntityMockMvc.perform(post("/api/test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testEntityDTO)))
            .andExpect(status().isCreated());

        // Validate the TestEntity in the database
        List<TestEntity> testEntityList = testEntityRepository.findAll();
        assertThat(testEntityList).hasSize(databaseSizeBeforeCreate + 1);
        TestEntity testTestEntity = testEntityList.get(testEntityList.size() - 1);
        assertThat(testTestEntity.getTest()).isEqualTo(DEFAULT_TEST);
        assertThat(testTestEntity.getDesc()).isEqualTo(DEFAULT_DESC);
    }

    @Test
    @Transactional
    public void createTestEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = testEntityRepository.findAll().size();

        // Create the TestEntity with an existing ID
        testEntity.setId(1L);
        TestEntityDTO testEntityDTO = testEntityMapper.toDto(testEntity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestEntityMockMvc.perform(post("/api/test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TestEntity in the database
        List<TestEntity> testEntityList = testEntityRepository.findAll();
        assertThat(testEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTestIsRequired() throws Exception {
        int databaseSizeBeforeTest = testEntityRepository.findAll().size();
        // set the field null
        testEntity.setTest(null);

        // Create the TestEntity, which fails.
        TestEntityDTO testEntityDTO = testEntityMapper.toDto(testEntity);

        restTestEntityMockMvc.perform(post("/api/test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testEntityDTO)))
            .andExpect(status().isBadRequest());

        List<TestEntity> testEntityList = testEntityRepository.findAll();
        assertThat(testEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = testEntityRepository.findAll().size();
        // set the field null
        testEntity.setDesc(null);

        // Create the TestEntity, which fails.
        TestEntityDTO testEntityDTO = testEntityMapper.toDto(testEntity);

        restTestEntityMockMvc.perform(post("/api/test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testEntityDTO)))
            .andExpect(status().isBadRequest());

        List<TestEntity> testEntityList = testEntityRepository.findAll();
        assertThat(testEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTestEntities() throws Exception {
        // Initialize the database
        testEntityRepository.saveAndFlush(testEntity);

        // Get all the testEntityList
        restTestEntityMockMvc.perform(get("/api/test-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].test").value(hasItem(DEFAULT_TEST.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())));
    }

    @Test
    @Transactional
    public void getTestEntity() throws Exception {
        // Initialize the database
        testEntityRepository.saveAndFlush(testEntity);

        // Get the testEntity
        restTestEntityMockMvc.perform(get("/api/test-entities/{id}", testEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(testEntity.getId().intValue()))
            .andExpect(jsonPath("$.test").value(DEFAULT_TEST.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTestEntity() throws Exception {
        // Get the testEntity
        restTestEntityMockMvc.perform(get("/api/test-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTestEntity() throws Exception {
        // Initialize the database
        testEntityRepository.saveAndFlush(testEntity);
        int databaseSizeBeforeUpdate = testEntityRepository.findAll().size();

        // Update the testEntity
        TestEntity updatedTestEntity = testEntityRepository.findOne(testEntity.getId());
        // Disconnect from session so that the updates on updatedTestEntity are not directly saved in db
        em.detach(updatedTestEntity);
        updatedTestEntity
            .test(UPDATED_TEST)
            .desc(UPDATED_DESC);
        TestEntityDTO testEntityDTO = testEntityMapper.toDto(updatedTestEntity);

        restTestEntityMockMvc.perform(put("/api/test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testEntityDTO)))
            .andExpect(status().isOk());

        // Validate the TestEntity in the database
        List<TestEntity> testEntityList = testEntityRepository.findAll();
        assertThat(testEntityList).hasSize(databaseSizeBeforeUpdate);
        TestEntity testTestEntity = testEntityList.get(testEntityList.size() - 1);
        assertThat(testTestEntity.getTest()).isEqualTo(UPDATED_TEST);
        assertThat(testTestEntity.getDesc()).isEqualTo(UPDATED_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingTestEntity() throws Exception {
        int databaseSizeBeforeUpdate = testEntityRepository.findAll().size();

        // Create the TestEntity
        TestEntityDTO testEntityDTO = testEntityMapper.toDto(testEntity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTestEntityMockMvc.perform(put("/api/test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testEntityDTO)))
            .andExpect(status().isCreated());

        // Validate the TestEntity in the database
        List<TestEntity> testEntityList = testEntityRepository.findAll();
        assertThat(testEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTestEntity() throws Exception {
        // Initialize the database
        testEntityRepository.saveAndFlush(testEntity);
        int databaseSizeBeforeDelete = testEntityRepository.findAll().size();

        // Get the testEntity
        restTestEntityMockMvc.perform(delete("/api/test-entities/{id}", testEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TestEntity> testEntityList = testEntityRepository.findAll();
        assertThat(testEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestEntity.class);
        TestEntity testEntity1 = new TestEntity();
        testEntity1.setId(1L);
        TestEntity testEntity2 = new TestEntity();
        testEntity2.setId(testEntity1.getId());
        assertThat(testEntity1).isEqualTo(testEntity2);
        testEntity2.setId(2L);
        assertThat(testEntity1).isNotEqualTo(testEntity2);
        testEntity1.setId(null);
        assertThat(testEntity1).isNotEqualTo(testEntity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestEntityDTO.class);
        TestEntityDTO testEntityDTO1 = new TestEntityDTO();
        testEntityDTO1.setId(1L);
        TestEntityDTO testEntityDTO2 = new TestEntityDTO();
        assertThat(testEntityDTO1).isNotEqualTo(testEntityDTO2);
        testEntityDTO2.setId(testEntityDTO1.getId());
        assertThat(testEntityDTO1).isEqualTo(testEntityDTO2);
        testEntityDTO2.setId(2L);
        assertThat(testEntityDTO1).isNotEqualTo(testEntityDTO2);
        testEntityDTO1.setId(null);
        assertThat(testEntityDTO1).isNotEqualTo(testEntityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(testEntityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(testEntityMapper.fromId(null)).isNull();
    }
}
