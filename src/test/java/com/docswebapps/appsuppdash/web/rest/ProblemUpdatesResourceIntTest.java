package com.docswebapps.appsuppdash.web.rest;

import com.docswebapps.appsuppdash.ApplicationSupportDashboardApp;

import com.docswebapps.appsuppdash.domain.ProblemUpdates;
import com.docswebapps.appsuppdash.domain.Problem;
import com.docswebapps.appsuppdash.repository.ProblemUpdatesRepository;
import com.docswebapps.appsuppdash.service.ProblemUpdatesService;
import com.docswebapps.appsuppdash.service.dto.ProblemUpdatesDTO;
import com.docswebapps.appsuppdash.service.mapper.ProblemUpdatesMapper;
import com.docswebapps.appsuppdash.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.docswebapps.appsuppdash.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProblemUpdatesResource REST controller.
 *
 * @see ProblemUpdatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationSupportDashboardApp.class)
public class ProblemUpdatesResourceIntTest {

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_TEXT = "BBBBBBBBBB";

    @Autowired
    private ProblemUpdatesRepository problemUpdatesRepository;

    @Autowired
    private ProblemUpdatesMapper problemUpdatesMapper;

    @Autowired
    private ProblemUpdatesService problemUpdatesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProblemUpdatesMockMvc;

    private ProblemUpdates problemUpdates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProblemUpdatesResource problemUpdatesResource = new ProblemUpdatesResource(problemUpdatesService);
        this.restProblemUpdatesMockMvc = MockMvcBuilders.standaloneSetup(problemUpdatesResource)
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
    public static ProblemUpdates createEntity(EntityManager em) {
        ProblemUpdates problemUpdates = new ProblemUpdates()
            .updatedAt(DEFAULT_UPDATED_AT)
            .updateText(DEFAULT_UPDATE_TEXT);
        // Add required entity
        Problem problem = ProblemResourceIntTest.createEntity(em);
        em.persist(problem);
        em.flush();
        problemUpdates.setProbUpdate(problem);
        return problemUpdates;
    }

    @Before
    public void initTest() {
        problemUpdates = createEntity(em);
    }

    @Test
    @Transactional
    public void createProblemUpdates() throws Exception {
        int databaseSizeBeforeCreate = problemUpdatesRepository.findAll().size();

        // Create the ProblemUpdates
        ProblemUpdatesDTO problemUpdatesDTO = problemUpdatesMapper.toDto(problemUpdates);
        restProblemUpdatesMockMvc.perform(post("/api/problem-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemUpdatesDTO)))
            .andExpect(status().isCreated());

        // Validate the ProblemUpdates in the database
        List<ProblemUpdates> problemUpdatesList = problemUpdatesRepository.findAll();
        assertThat(problemUpdatesList).hasSize(databaseSizeBeforeCreate + 1);
        ProblemUpdates testProblemUpdates = problemUpdatesList.get(problemUpdatesList.size() - 1);
        assertThat(testProblemUpdates.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testProblemUpdates.getUpdateText()).isEqualTo(DEFAULT_UPDATE_TEXT);
    }

    @Test
    @Transactional
    public void createProblemUpdatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = problemUpdatesRepository.findAll().size();

        // Create the ProblemUpdates with an existing ID
        problemUpdates.setId(1L);
        ProblemUpdatesDTO problemUpdatesDTO = problemUpdatesMapper.toDto(problemUpdates);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProblemUpdatesMockMvc.perform(post("/api/problem-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemUpdatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProblemUpdates in the database
        List<ProblemUpdates> problemUpdatesList = problemUpdatesRepository.findAll();
        assertThat(problemUpdatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = problemUpdatesRepository.findAll().size();
        // set the field null
        problemUpdates.setUpdatedAt(null);

        // Create the ProblemUpdates, which fails.
        ProblemUpdatesDTO problemUpdatesDTO = problemUpdatesMapper.toDto(problemUpdates);

        restProblemUpdatesMockMvc.perform(post("/api/problem-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemUpdatesDTO)))
            .andExpect(status().isBadRequest());

        List<ProblemUpdates> problemUpdatesList = problemUpdatesRepository.findAll();
        assertThat(problemUpdatesList).hasSize(databaseSizeBeforeTest);
    }

//    @Test
//    @Transactional
//    public void getAllProblemUpdates() throws Exception {
//        // Initialize the database
//        problemUpdatesRepository.saveAndFlush(problemUpdates);
//
//        // Get all the problemUpdatesList
//        restProblemUpdatesMockMvc.perform(get("/api/problem-updates?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(problemUpdates.getId().intValue())))
//            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
//            .andExpect(jsonPath("$.[*].updateText").value(hasItem(DEFAULT_UPDATE_TEXT.toString())));
//    }
    
    @Test
    @Transactional
    public void getProblemUpdates() throws Exception {
        // Initialize the database
        problemUpdatesRepository.saveAndFlush(problemUpdates);

        // Get the problemUpdates
        restProblemUpdatesMockMvc.perform(get("/api/problem-updates/{id}", problemUpdates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(problemUpdates.getId().intValue()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.updateText").value(DEFAULT_UPDATE_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProblemUpdates() throws Exception {
        // Get the problemUpdates
        restProblemUpdatesMockMvc.perform(get("/api/problem-updates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProblemUpdates() throws Exception {
        // Initialize the database
        problemUpdatesRepository.saveAndFlush(problemUpdates);

        int databaseSizeBeforeUpdate = problemUpdatesRepository.findAll().size();

        // Update the problemUpdates
        ProblemUpdates updatedProblemUpdates = problemUpdatesRepository.findById(problemUpdates.getId()).get();
        // Disconnect from session so that the updates on updatedProblemUpdates are not directly saved in db
        em.detach(updatedProblemUpdates);
        updatedProblemUpdates
            .updatedAt(UPDATED_UPDATED_AT)
            .updateText(UPDATED_UPDATE_TEXT);
        ProblemUpdatesDTO problemUpdatesDTO = problemUpdatesMapper.toDto(updatedProblemUpdates);

        restProblemUpdatesMockMvc.perform(put("/api/problem-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemUpdatesDTO)))
            .andExpect(status().isOk());

        // Validate the ProblemUpdates in the database
        List<ProblemUpdates> problemUpdatesList = problemUpdatesRepository.findAll();
        assertThat(problemUpdatesList).hasSize(databaseSizeBeforeUpdate);
        ProblemUpdates testProblemUpdates = problemUpdatesList.get(problemUpdatesList.size() - 1);
        assertThat(testProblemUpdates.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testProblemUpdates.getUpdateText()).isEqualTo(UPDATED_UPDATE_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingProblemUpdates() throws Exception {
        int databaseSizeBeforeUpdate = problemUpdatesRepository.findAll().size();

        // Create the ProblemUpdates
        ProblemUpdatesDTO problemUpdatesDTO = problemUpdatesMapper.toDto(problemUpdates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProblemUpdatesMockMvc.perform(put("/api/problem-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemUpdatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProblemUpdates in the database
        List<ProblemUpdates> problemUpdatesList = problemUpdatesRepository.findAll();
        assertThat(problemUpdatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProblemUpdates() throws Exception {
        // Initialize the database
        problemUpdatesRepository.saveAndFlush(problemUpdates);

        int databaseSizeBeforeDelete = problemUpdatesRepository.findAll().size();

        // Delete the problemUpdates
        restProblemUpdatesMockMvc.perform(delete("/api/problem-updates/{id}", problemUpdates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProblemUpdates> problemUpdatesList = problemUpdatesRepository.findAll();
        assertThat(problemUpdatesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProblemUpdates.class);
        ProblemUpdates problemUpdates1 = new ProblemUpdates();
        problemUpdates1.setId(1L);
        ProblemUpdates problemUpdates2 = new ProblemUpdates();
        problemUpdates2.setId(problemUpdates1.getId());
        assertThat(problemUpdates1).isEqualTo(problemUpdates2);
        problemUpdates2.setId(2L);
        assertThat(problemUpdates1).isNotEqualTo(problemUpdates2);
        problemUpdates1.setId(null);
        assertThat(problemUpdates1).isNotEqualTo(problemUpdates2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProblemUpdatesDTO.class);
        ProblemUpdatesDTO problemUpdatesDTO1 = new ProblemUpdatesDTO();
        problemUpdatesDTO1.setId(1L);
        ProblemUpdatesDTO problemUpdatesDTO2 = new ProblemUpdatesDTO();
        assertThat(problemUpdatesDTO1).isNotEqualTo(problemUpdatesDTO2);
        problemUpdatesDTO2.setId(problemUpdatesDTO1.getId());
        assertThat(problemUpdatesDTO1).isEqualTo(problemUpdatesDTO2);
        problemUpdatesDTO2.setId(2L);
        assertThat(problemUpdatesDTO1).isNotEqualTo(problemUpdatesDTO2);
        problemUpdatesDTO1.setId(null);
        assertThat(problemUpdatesDTO1).isNotEqualTo(problemUpdatesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(problemUpdatesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(problemUpdatesMapper.fromId(null)).isNull();
    }
}
