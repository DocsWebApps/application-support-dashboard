package com.docswebapps.appsuppdash.web.rest;

import com.docswebapps.appsuppdash.ApplicationSupportDashboardApp;

import com.docswebapps.appsuppdash.domain.RiskUpdates;
import com.docswebapps.appsuppdash.domain.Risk;
import com.docswebapps.appsuppdash.repository.RiskUpdatesRepository;
import com.docswebapps.appsuppdash.service.RiskUpdatesService;
import com.docswebapps.appsuppdash.service.dto.RiskUpdatesDTO;
import com.docswebapps.appsuppdash.service.mapper.RiskUpdatesMapper;
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
 * Test class for the RiskUpdatesResource REST controller.
 *
 * @see RiskUpdatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationSupportDashboardApp.class)
public class RiskUpdatesResourceIntTest {

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_TEXT = "BBBBBBBBBB";

    @Autowired
    private RiskUpdatesRepository riskUpdatesRepository;

    @Autowired
    private RiskUpdatesMapper riskUpdatesMapper;

    @Autowired
    private RiskUpdatesService riskUpdatesService;

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

    private MockMvc restRiskUpdatesMockMvc;

    private RiskUpdates riskUpdates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RiskUpdatesResource riskUpdatesResource = new RiskUpdatesResource(riskUpdatesService);
        this.restRiskUpdatesMockMvc = MockMvcBuilders.standaloneSetup(riskUpdatesResource)
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
    public static RiskUpdates createEntity(EntityManager em) {
        RiskUpdates riskUpdates = new RiskUpdates()
            .updatedAt(DEFAULT_UPDATED_AT)
            .updateText(DEFAULT_UPDATE_TEXT);
        // Add required entity
        Risk risk = RiskResourceIntTest.createEntity(em);
        em.persist(risk);
        em.flush();
        riskUpdates.setRiskkUpdate(risk);
        return riskUpdates;
    }

    @Before
    public void initTest() {
        riskUpdates = createEntity(em);
    }

    @Test
    @Transactional
    public void createRiskUpdates() throws Exception {
        int databaseSizeBeforeCreate = riskUpdatesRepository.findAll().size();

        // Create the RiskUpdates
        RiskUpdatesDTO riskUpdatesDTO = riskUpdatesMapper.toDto(riskUpdates);
        restRiskUpdatesMockMvc.perform(post("/api/risk-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskUpdatesDTO)))
            .andExpect(status().isCreated());

        // Validate the RiskUpdates in the database
        List<RiskUpdates> riskUpdatesList = riskUpdatesRepository.findAll();
        assertThat(riskUpdatesList).hasSize(databaseSizeBeforeCreate + 1);
        RiskUpdates testRiskUpdates = riskUpdatesList.get(riskUpdatesList.size() - 1);
        assertThat(testRiskUpdates.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testRiskUpdates.getUpdateText()).isEqualTo(DEFAULT_UPDATE_TEXT);
    }

    @Test
    @Transactional
    public void createRiskUpdatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = riskUpdatesRepository.findAll().size();

        // Create the RiskUpdates with an existing ID
        riskUpdates.setId(1L);
        RiskUpdatesDTO riskUpdatesDTO = riskUpdatesMapper.toDto(riskUpdates);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiskUpdatesMockMvc.perform(post("/api/risk-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskUpdatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RiskUpdates in the database
        List<RiskUpdates> riskUpdatesList = riskUpdatesRepository.findAll();
        assertThat(riskUpdatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = riskUpdatesRepository.findAll().size();
        // set the field null
        riskUpdates.setUpdatedAt(null);

        // Create the RiskUpdates, which fails.
        RiskUpdatesDTO riskUpdatesDTO = riskUpdatesMapper.toDto(riskUpdates);

        restRiskUpdatesMockMvc.perform(post("/api/risk-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskUpdatesDTO)))
            .andExpect(status().isBadRequest());

        List<RiskUpdates> riskUpdatesList = riskUpdatesRepository.findAll();
        assertThat(riskUpdatesList).hasSize(databaseSizeBeforeTest);
    }

//    @Test
//    @Transactional
//    public void getAllRiskUpdates() throws Exception {
//        // Initialize the database
//        riskUpdatesRepository.saveAndFlush(riskUpdates);
//
//        // Get all the riskUpdatesList
//        restRiskUpdatesMockMvc.perform(get("/api/risk-updates?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(riskUpdates.getId().intValue())))
//            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
//            .andExpect(jsonPath("$.[*].updateText").value(hasItem(DEFAULT_UPDATE_TEXT.toString())));
//    }
    
    @Test
    @Transactional
    public void getRiskUpdates() throws Exception {
        // Initialize the database
        riskUpdatesRepository.saveAndFlush(riskUpdates);

        // Get the riskUpdates
        restRiskUpdatesMockMvc.perform(get("/api/risk-updates/{id}", riskUpdates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(riskUpdates.getId().intValue()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.updateText").value(DEFAULT_UPDATE_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRiskUpdates() throws Exception {
        // Get the riskUpdates
        restRiskUpdatesMockMvc.perform(get("/api/risk-updates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRiskUpdates() throws Exception {
        // Initialize the database
        riskUpdatesRepository.saveAndFlush(riskUpdates);

        int databaseSizeBeforeUpdate = riskUpdatesRepository.findAll().size();

        // Update the riskUpdates
        RiskUpdates updatedRiskUpdates = riskUpdatesRepository.findById(riskUpdates.getId()).get();
        // Disconnect from session so that the updates on updatedRiskUpdates are not directly saved in db
        em.detach(updatedRiskUpdates);
        updatedRiskUpdates
            .updatedAt(UPDATED_UPDATED_AT)
            .updateText(UPDATED_UPDATE_TEXT);
        RiskUpdatesDTO riskUpdatesDTO = riskUpdatesMapper.toDto(updatedRiskUpdates);

        restRiskUpdatesMockMvc.perform(put("/api/risk-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskUpdatesDTO)))
            .andExpect(status().isOk());

        // Validate the RiskUpdates in the database
        List<RiskUpdates> riskUpdatesList = riskUpdatesRepository.findAll();
        assertThat(riskUpdatesList).hasSize(databaseSizeBeforeUpdate);
        RiskUpdates testRiskUpdates = riskUpdatesList.get(riskUpdatesList.size() - 1);
        assertThat(testRiskUpdates.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testRiskUpdates.getUpdateText()).isEqualTo(UPDATED_UPDATE_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingRiskUpdates() throws Exception {
        int databaseSizeBeforeUpdate = riskUpdatesRepository.findAll().size();

        // Create the RiskUpdates
        RiskUpdatesDTO riskUpdatesDTO = riskUpdatesMapper.toDto(riskUpdates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiskUpdatesMockMvc.perform(put("/api/risk-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskUpdatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RiskUpdates in the database
        List<RiskUpdates> riskUpdatesList = riskUpdatesRepository.findAll();
        assertThat(riskUpdatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRiskUpdates() throws Exception {
        // Initialize the database
        riskUpdatesRepository.saveAndFlush(riskUpdates);

        int databaseSizeBeforeDelete = riskUpdatesRepository.findAll().size();

        // Delete the riskUpdates
        restRiskUpdatesMockMvc.perform(delete("/api/risk-updates/{id}", riskUpdates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RiskUpdates> riskUpdatesList = riskUpdatesRepository.findAll();
        assertThat(riskUpdatesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskUpdates.class);
        RiskUpdates riskUpdates1 = new RiskUpdates();
        riskUpdates1.setId(1L);
        RiskUpdates riskUpdates2 = new RiskUpdates();
        riskUpdates2.setId(riskUpdates1.getId());
        assertThat(riskUpdates1).isEqualTo(riskUpdates2);
        riskUpdates2.setId(2L);
        assertThat(riskUpdates1).isNotEqualTo(riskUpdates2);
        riskUpdates1.setId(null);
        assertThat(riskUpdates1).isNotEqualTo(riskUpdates2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskUpdatesDTO.class);
        RiskUpdatesDTO riskUpdatesDTO1 = new RiskUpdatesDTO();
        riskUpdatesDTO1.setId(1L);
        RiskUpdatesDTO riskUpdatesDTO2 = new RiskUpdatesDTO();
        assertThat(riskUpdatesDTO1).isNotEqualTo(riskUpdatesDTO2);
        riskUpdatesDTO2.setId(riskUpdatesDTO1.getId());
        assertThat(riskUpdatesDTO1).isEqualTo(riskUpdatesDTO2);
        riskUpdatesDTO2.setId(2L);
        assertThat(riskUpdatesDTO1).isNotEqualTo(riskUpdatesDTO2);
        riskUpdatesDTO1.setId(null);
        assertThat(riskUpdatesDTO1).isNotEqualTo(riskUpdatesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(riskUpdatesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(riskUpdatesMapper.fromId(null)).isNull();
    }
}
