package com.docswebapps.appsuppdash.web.rest;

import com.docswebapps.appsuppdash.ApplicationSupportDashboardApp;

import com.docswebapps.appsuppdash.domain.Risk;
import com.docswebapps.appsuppdash.repository.RiskRepository;
import com.docswebapps.appsuppdash.service.RiskService;
import com.docswebapps.appsuppdash.service.dto.RiskDTO;
import com.docswebapps.appsuppdash.service.mapper.RiskMapper;
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

import com.docswebapps.appsuppdash.domain.enumeration.IssueStatus;
import com.docswebapps.appsuppdash.domain.enumeration.Priority;
/**
 * Test class for the RiskResource REST controller.
 *
 * @see RiskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationSupportDashboardApp.class)
public class RiskResourceIntTest {

    private static final LocalDate DEFAULT_OPENED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OPENED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MITIGATION = "AAAAAAAAAA";
    private static final String UPDATED_MITIGATION = "BBBBBBBBBB";

    private static final IssueStatus DEFAULT_RISK_STATUS = IssueStatus.OPEN;
    private static final IssueStatus UPDATED_RISK_STATUS = IssueStatus.CLOSED;

    private static final Priority DEFAULT_PRIORITY = Priority.LOW;
    private static final Priority UPDATED_PRIORITY = Priority.MEDIUM;

    private static final LocalDate DEFAULT_CLOSED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CLOSED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RiskRepository riskRepository;

    @Autowired
    private RiskMapper riskMapper;

    @Autowired
    private RiskService riskService;

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

    private MockMvc restRiskMockMvc;

    private Risk risk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RiskResource riskResource = new RiskResource(riskService);
        this.restRiskMockMvc = MockMvcBuilders.standaloneSetup(riskResource)
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
    public static Risk createEntity(EntityManager em) {
        Risk risk = new Risk()
            .openedAt(DEFAULT_OPENED_AT)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .mitigation(DEFAULT_MITIGATION)
            .riskStatus(DEFAULT_RISK_STATUS)
            .priority(DEFAULT_PRIORITY)
            .closedAt(DEFAULT_CLOSED_AT);
        return risk;
    }

    @Before
    public void initTest() {
        risk = createEntity(em);
    }

    @Test
    @Transactional
    public void createRisk() throws Exception {
        int databaseSizeBeforeCreate = riskRepository.findAll().size();

        // Create the Risk
        RiskDTO riskDTO = riskMapper.toDto(risk);
        restRiskMockMvc.perform(post("/api/risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskDTO)))
            .andExpect(status().isCreated());

        // Validate the Risk in the database
        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeCreate + 1);
        Risk testRisk = riskList.get(riskList.size() - 1);
        assertThat(testRisk.getOpenedAt()).isEqualTo(DEFAULT_OPENED_AT);
        assertThat(testRisk.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testRisk.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRisk.getMitigation()).isEqualTo(DEFAULT_MITIGATION);
        assertThat(testRisk.getRiskStatus()).isEqualTo(DEFAULT_RISK_STATUS);
        assertThat(testRisk.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testRisk.getClosedAt()).isEqualTo(DEFAULT_CLOSED_AT);
    }

    @Test
    @Transactional
    public void createRiskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = riskRepository.findAll().size();

        // Create the Risk with an existing ID
        risk.setId(1L);
        RiskDTO riskDTO = riskMapper.toDto(risk);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiskMockMvc.perform(post("/api/risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Risk in the database
        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOpenedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = riskRepository.findAll().size();
        // set the field null
        risk.setOpenedAt(null);

        // Create the Risk, which fails.
        RiskDTO riskDTO = riskMapper.toDto(risk);

        restRiskMockMvc.perform(post("/api/risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskDTO)))
            .andExpect(status().isBadRequest());

        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = riskRepository.findAll().size();
        // set the field null
        risk.setTitle(null);

        // Create the Risk, which fails.
        RiskDTO riskDTO = riskMapper.toDto(risk);

        restRiskMockMvc.perform(post("/api/risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskDTO)))
            .andExpect(status().isBadRequest());

        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRiskStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = riskRepository.findAll().size();
        // set the field null
        risk.setRiskStatus(null);

        // Create the Risk, which fails.
        RiskDTO riskDTO = riskMapper.toDto(risk);

        restRiskMockMvc.perform(post("/api/risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskDTO)))
            .andExpect(status().isBadRequest());

        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = riskRepository.findAll().size();
        // set the field null
        risk.setPriority(null);

        // Create the Risk, which fails.
        RiskDTO riskDTO = riskMapper.toDto(risk);

        restRiskMockMvc.perform(post("/api/risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskDTO)))
            .andExpect(status().isBadRequest());

        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRisks() throws Exception {
        // Initialize the database
        riskRepository.saveAndFlush(risk);

        // Get all the riskList
        restRiskMockMvc.perform(get("/api/risks/ALL/ALL?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(risk.getId().intValue())))
            .andExpect(jsonPath("$.[*].openedAt").value(hasItem(DEFAULT_OPENED_AT.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].mitigation").value(hasItem(DEFAULT_MITIGATION.toString())))
            .andExpect(jsonPath("$.[*].riskStatus").value(hasItem(DEFAULT_RISK_STATUS.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].closedAt").value(hasItem(DEFAULT_CLOSED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getRisk() throws Exception {
        // Initialize the database
        riskRepository.saveAndFlush(risk);

        // Get the risk
        restRiskMockMvc.perform(get("/api/risks/{id}", risk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(risk.getId().intValue()))
            .andExpect(jsonPath("$.openedAt").value(DEFAULT_OPENED_AT.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.mitigation").value(DEFAULT_MITIGATION.toString()))
            .andExpect(jsonPath("$.riskStatus").value(DEFAULT_RISK_STATUS.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.closedAt").value(DEFAULT_CLOSED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRisk() throws Exception {
        // Get the risk
        restRiskMockMvc.perform(get("/api/risks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRisk() throws Exception {
        // Initialize the database
        riskRepository.saveAndFlush(risk);

        int databaseSizeBeforeUpdate = riskRepository.findAll().size();

        // Update the risk
        Risk updatedRisk = riskRepository.findById(risk.getId()).get();
        // Disconnect from session so that the updates on updatedRisk are not directly saved in db
        em.detach(updatedRisk);
        updatedRisk
            .openedAt(UPDATED_OPENED_AT)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .mitigation(UPDATED_MITIGATION)
            .riskStatus(UPDATED_RISK_STATUS)
            .priority(UPDATED_PRIORITY)
            .closedAt(UPDATED_CLOSED_AT);
        RiskDTO riskDTO = riskMapper.toDto(updatedRisk);

        restRiskMockMvc.perform(put("/api/risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskDTO)))
            .andExpect(status().isOk());

        // Validate the Risk in the database
        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeUpdate);
        Risk testRisk = riskList.get(riskList.size() - 1);
        assertThat(testRisk.getOpenedAt()).isEqualTo(UPDATED_OPENED_AT);
        assertThat(testRisk.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRisk.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRisk.getMitigation()).isEqualTo(UPDATED_MITIGATION);
        assertThat(testRisk.getRiskStatus()).isEqualTo(UPDATED_RISK_STATUS);
        assertThat(testRisk.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testRisk.getClosedAt()).isEqualTo(UPDATED_CLOSED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingRisk() throws Exception {
        int databaseSizeBeforeUpdate = riskRepository.findAll().size();

        // Create the Risk
        RiskDTO riskDTO = riskMapper.toDto(risk);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiskMockMvc.perform(put("/api/risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Risk in the database
        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRisk() throws Exception {
        // Initialize the database
        riskRepository.saveAndFlush(risk);

        int databaseSizeBeforeDelete = riskRepository.findAll().size();

        // Delete the risk
        restRiskMockMvc.perform(delete("/api/risks/{id}", risk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Risk.class);
        Risk risk1 = new Risk();
        risk1.setId(1L);
        Risk risk2 = new Risk();
        risk2.setId(risk1.getId());
        assertThat(risk1).isEqualTo(risk2);
        risk2.setId(2L);
        assertThat(risk1).isNotEqualTo(risk2);
        risk1.setId(null);
        assertThat(risk1).isNotEqualTo(risk2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskDTO.class);
        RiskDTO riskDTO1 = new RiskDTO();
        riskDTO1.setId(1L);
        RiskDTO riskDTO2 = new RiskDTO();
        assertThat(riskDTO1).isNotEqualTo(riskDTO2);
        riskDTO2.setId(riskDTO1.getId());
        assertThat(riskDTO1).isEqualTo(riskDTO2);
        riskDTO2.setId(2L);
        assertThat(riskDTO1).isNotEqualTo(riskDTO2);
        riskDTO1.setId(null);
        assertThat(riskDTO1).isNotEqualTo(riskDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(riskMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(riskMapper.fromId(null)).isNull();
    }
}
