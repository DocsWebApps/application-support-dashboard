package com.docswebapps.appsuppdash.web.rest;

import com.docswebapps.appsuppdash.ApplicationSupportDashboardApp;
import com.docswebapps.appsuppdash.domain.App;
import com.docswebapps.appsuppdash.domain.Incident;
import com.docswebapps.appsuppdash.domain.enumeration.SystemStatus;
import com.docswebapps.appsuppdash.repository.AppRepository;
import com.docswebapps.appsuppdash.repository.IncidentRepository;
import com.docswebapps.appsuppdash.service.IncidentService;
import com.docswebapps.appsuppdash.service.dto.IncidentDTO;
import com.docswebapps.appsuppdash.service.mapper.IncidentMapper;
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
import com.docswebapps.appsuppdash.domain.enumeration.Severity;
import com.docswebapps.appsuppdash.domain.enumeration.IssueStatus;
/**
 * Test class for the IncidentResource REST controller.
 *
 * @see IncidentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationSupportDashboardApp.class)
public class IncidentResourceIntTest {
    private static final LocalDate DEFAULT_OPENED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OPENED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Severity DEFAULT_SEVERITY = Severity.P1;
    private static final Severity UPDATED_SEVERITY = Severity.P2;

    private static final IssueStatus DEFAULT_INCIDENT_STATUS = IssueStatus.OPEN;
    private static final IssueStatus UPDATED_INCIDENT_STATUS = IssueStatus.CLOSED;

    private static final LocalDate DEFAULT_CLOSED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CLOSED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private IncidentMapper incidentMapper;

    @Autowired
    private IncidentService incidentService;

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

    private MockMvc restIncidentMockMvc;

    private Incident incident;

    private void createApp() {
        final String DEFAULT_NAME = "AAAAAAAAAA";
        final Long DEFAULT_PROBLEM_COUNT = 1L;
        final SystemStatus DEFAULT_SYS_STATUS = SystemStatus.GREEN;
        final LocalDate DEFAULT_LAST_PROBLEM_DATE = LocalDate.ofEpochDay(0L);

        App app = new App()
            .name(DEFAULT_NAME)
            .problemCount(DEFAULT_PROBLEM_COUNT)
            .sysStatus(DEFAULT_SYS_STATUS)
            .lastProblemDate(DEFAULT_LAST_PROBLEM_DATE);

        appRepository.save(app);
    }

    @Before
    public void setup() {
        this.createApp();
        MockitoAnnotations.initMocks(this);
        final IncidentResource incidentResource = new IncidentResource(incidentService);
        this.restIncidentMockMvc = MockMvcBuilders.standaloneSetup(incidentResource)
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
    public static Incident createEntity(EntityManager em) {
        Incident incident = new Incident()
            .openedAt(DEFAULT_OPENED_AT)
            .description(DEFAULT_DESCRIPTION)
            .severity(DEFAULT_SEVERITY)
            .incidentStatus(DEFAULT_INCIDENT_STATUS)
            .closedAt(DEFAULT_CLOSED_AT);
        return incident;
    }

    @Before
    public void initTest() {
        incident = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncident() throws Exception {
        int databaseSizeBeforeCreate = incidentRepository.findAll().size();

        // Create the Incident
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);
        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isCreated());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeCreate + 1);
        Incident testIncident = incidentList.get(incidentList.size() - 1);
        assertThat(testIncident.getOpenedAt()).isEqualTo(DEFAULT_OPENED_AT);
        assertThat(testIncident.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIncident.getSeverity()).isEqualTo(DEFAULT_SEVERITY);
        assertThat(testIncident.getIncidentStatus()).isEqualTo(DEFAULT_INCIDENT_STATUS);
        assertThat(testIncident.getClosedAt()).isEqualTo(DEFAULT_CLOSED_AT);
    }

    @Test
    @Transactional
    public void createIncidentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incidentRepository.findAll().size();

        // Create the Incident with an existing ID
        incident.setId(1L);
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOpenedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setOpenedAt(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setDescription(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSeverityIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setSeverity(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncidentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setIncidentStatus(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAllIncidentCombos() throws Exception {
        this.getAllIncidents(IssueStatus.ALL, Severity.ALL);
        this.getAllIncidents(IssueStatus.ALL, Severity.P1);
        this.getAllIncidents(IssueStatus.ALL, Severity.P2);
        this.getAllIncidents(IssueStatus.ALL, Severity.P3);
        this.getAllIncidents(IssueStatus.ALL, Severity.P4);
        this.getAllIncidents(IssueStatus.OPEN, Severity.ALL);
        this.getAllIncidents(IssueStatus.OPEN, Severity.P1);
        this.getAllIncidents(IssueStatus.OPEN, Severity.P2);
        this.getAllIncidents(IssueStatus.OPEN, Severity.P3);
        this.getAllIncidents(IssueStatus.OPEN, Severity.P4);
        this.getAllIncidents(IssueStatus.CLOSED, Severity.ALL);
        this.getAllIncidents(IssueStatus.CLOSED, Severity.P1);
        this.getAllIncidents(IssueStatus.CLOSED, Severity.P2);
        this.getAllIncidents(IssueStatus.CLOSED, Severity.P3);
        this.getAllIncidents(IssueStatus.CLOSED, Severity.P4);
    }

    @Transactional
    public void getAllIncidents(IssueStatus status, Severity severity) throws Exception {
        // Initialize the database
        Incident testIncident = new Incident()
            .openedAt(DEFAULT_OPENED_AT)
            .description(DEFAULT_DESCRIPTION)
            .severity(severity)
            .incidentStatus(status)
            .closedAt(DEFAULT_CLOSED_AT);

        incidentRepository.saveAndFlush(testIncident);

        // Get all the incidentList
        restIncidentMockMvc.perform(get("/api/incidents/{status}/{severity}", status, severity))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testIncident.getId().intValue())))
            .andExpect(jsonPath("$.[*].openedAt").value(hasItem(DEFAULT_OPENED_AT.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(severity.toString())))
            .andExpect(jsonPath("$.[*].incidentStatus").value(hasItem(status.toString())))
            .andExpect(jsonPath("$.[*].closedAt").value(hasItem(DEFAULT_CLOSED_AT.toString())));

        // Clean Up DataBase
        incidentRepository.delete(testIncident);
    }

    @Test
    @Transactional
    public void getIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get the incident
        restIncidentMockMvc.perform(get("/api/incidents/{id}", incident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incident.getId().intValue()))
            .andExpect(jsonPath("$.openedAt").value(DEFAULT_OPENED_AT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.severity").value(DEFAULT_SEVERITY.toString()))
            .andExpect(jsonPath("$.incidentStatus").value(DEFAULT_INCIDENT_STATUS.toString()))
            .andExpect(jsonPath("$.closedAt").value(DEFAULT_CLOSED_AT.toString()));
    }

    @Test
    @Transactional
    public void getBannerStats() throws Exception {
        // Get the incident
        restIncidentMockMvc.perform(get("/api/incidents/stats"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.p3Count").value(0))
            .andExpect(jsonPath("$.p4Count").value(0))
            .andExpect(jsonPath("$.problemCount").value(0))
            .andExpect(jsonPath("$.riskCount").value(0));
    }

    @Test
    public void checkBannerIncidents() throws Exception {
        this.getP1P2BannerIncidents(Severity.P1);
        this.getP1P2BannerIncidents(Severity.P2);
        this.getP3P4BannerIncidents(Severity.P3);
        this.getP3P4BannerIncidents(Severity.P4);
    }

    @Transactional
    public void getP3P4BannerIncidents(Severity severity) throws Exception {
        // Initialize the database
        Incident testIncident = new Incident()
            .openedAt(DEFAULT_OPENED_AT)
            .description(DEFAULT_DESCRIPTION)
            .severity(severity)
            .incidentStatus(DEFAULT_INCIDENT_STATUS)
            .closedAt(DEFAULT_CLOSED_AT);

        incidentRepository.saveAndFlush(testIncident);

        // Get the incident
        restIncidentMockMvc.perform(get("/api/incidents/incident"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.description").value("No open incidents!!"));

        // Cleanup Database
        incidentRepository.delete(testIncident);
    }

    @Transactional
    public void getP1P2BannerIncidents(Severity severity) throws Exception {
        // Initialize the database
        Incident testIncident = new Incident()
            .openedAt(DEFAULT_OPENED_AT)
            .description(DEFAULT_DESCRIPTION)
            .severity(severity)
            .incidentStatus(DEFAULT_INCIDENT_STATUS)
            .closedAt(DEFAULT_CLOSED_AT);

        incidentRepository.saveAndFlush(testIncident);

        // Get the incident
        restIncidentMockMvc.perform(get("/api/incidents/incident"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(testIncident.getId().intValue()))
            .andExpect(jsonPath("$.openedAt").value(DEFAULT_OPENED_AT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.severity").value(severity.toString()))
            .andExpect(jsonPath("$.incidentStatus").value(DEFAULT_INCIDENT_STATUS.toString()))
            .andExpect(jsonPath("$.closedAt").value(DEFAULT_CLOSED_AT.toString()));

        // Cleanup Database
        incidentRepository.delete(testIncident);
    }

    @Test
    @Transactional
    public void getNonExistingIncident() throws Exception {
        // Get the incident
        restIncidentMockMvc.perform(get("/api/incidents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        int databaseSizeBeforeUpdate = incidentRepository.findAll().size();

        // Update the incident
        Incident updatedIncident = incidentRepository.findById(incident.getId()).get();
        // Disconnect from session so that the updates on updatedIncident are not directly saved in db
        em.detach(updatedIncident);
        updatedIncident
            .openedAt(UPDATED_OPENED_AT)
            .description(UPDATED_DESCRIPTION)
            .severity(UPDATED_SEVERITY)
            .incidentStatus(UPDATED_INCIDENT_STATUS)
            .closedAt(UPDATED_CLOSED_AT);
        IncidentDTO incidentDTO = incidentMapper.toDto(updatedIncident);

        restIncidentMockMvc.perform(put("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isOk());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeUpdate);
        Incident testIncident = incidentList.get(incidentList.size() - 1);
        assertThat(testIncident.getOpenedAt()).isEqualTo(UPDATED_OPENED_AT);
        assertThat(testIncident.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIncident.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testIncident.getIncidentStatus()).isEqualTo(UPDATED_INCIDENT_STATUS);
        assertThat(testIncident.getClosedAt()).isEqualTo(UPDATED_CLOSED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingIncident() throws Exception {
        int databaseSizeBeforeUpdate = incidentRepository.findAll().size();

        // Create the Incident
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncidentMockMvc.perform(put("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        int databaseSizeBeforeDelete = incidentRepository.findAll().size();

        // Delete the incident
        restIncidentMockMvc.perform(delete("/api/incidents/{id}/delete", incident.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void closeIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        int databaseSizeBeforeDelete = incidentRepository.findAll().size();

        // Delete the incident
        restIncidentMockMvc.perform(delete("/api/incidents/{id}/close", incident.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeDelete);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Incident.class);
        Incident incident1 = new Incident();
        incident1.setId(1L);
        Incident incident2 = new Incident();
        incident2.setId(incident1.getId());
        assertThat(incident1).isEqualTo(incident2);
        incident2.setId(2L);
        assertThat(incident1).isNotEqualTo(incident2);
        incident1.setId(null);
        assertThat(incident1).isNotEqualTo(incident2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidentDTO.class);
        IncidentDTO incidentDTO1 = new IncidentDTO();
        incidentDTO1.setId(1L);
        IncidentDTO incidentDTO2 = new IncidentDTO();
        assertThat(incidentDTO1).isNotEqualTo(incidentDTO2);
        incidentDTO2.setId(incidentDTO1.getId());
        assertThat(incidentDTO1).isEqualTo(incidentDTO2);
        incidentDTO2.setId(2L);
        assertThat(incidentDTO1).isNotEqualTo(incidentDTO2);
        incidentDTO1.setId(null);
        assertThat(incidentDTO1).isNotEqualTo(incidentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(incidentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(incidentMapper.fromId(null)).isNull();
    }
}
