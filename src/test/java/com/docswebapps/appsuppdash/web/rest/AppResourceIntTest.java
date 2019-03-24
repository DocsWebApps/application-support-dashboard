package com.docswebapps.appsuppdash.web.rest;

import com.docswebapps.appsuppdash.ApplicationSupportDashboardApp;

import com.docswebapps.appsuppdash.domain.App;
import com.docswebapps.appsuppdash.repository.AppRepository;
import com.docswebapps.appsuppdash.service.AppService;
import com.docswebapps.appsuppdash.service.dto.AppDTO;
import com.docswebapps.appsuppdash.service.mapper.AppMapper;
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
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.docswebapps.appsuppdash.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.docswebapps.appsuppdash.domain.enumeration.SystemStatus;
/**
 * Test class for the AppResource REST controller.
 *
 * @see AppResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationSupportDashboardApp.class)
public class AppResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PROBLEM_COUNT = 0L;
    private static final Long UPDATED_PROBLEM_COUNT = 1L;

    private static final SystemStatus DEFAULT_SYS_STATUS = SystemStatus.GREEN;
    private static final SystemStatus UPDATED_SYS_STATUS = SystemStatus.AMBER;

    private static final LocalDate DEFAULT_LAST_PROBLEM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_PROBLEM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String SYS_MESSAGE = "System is OK: No Major Incidents Reported";
    private static final String SYS_MESSAGE_COLOR = "green";
    private static final String MSG_DETAIL = ChronoUnit.DAYS.between(DEFAULT_LAST_PROBLEM_DATE, LocalDate.now()) + " Days Since the Last P1/P2";
    private static final String MSG_COLOR = "black";
    private static final String PROB_COUNT ="0 Previous P1/P2's Recorded";

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private AppService appService;

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

    private MockMvc restAppMockMvc;

    private App app;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppResource appResource = new AppResource(appService);
        this.restAppMockMvc = MockMvcBuilders.standaloneSetup(appResource)
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
    public static App createEntity(EntityManager em) {
        App app = new App()
            .name(DEFAULT_NAME)
            .problemCount(DEFAULT_PROBLEM_COUNT)
            .sysStatus(DEFAULT_SYS_STATUS)
            .lastProblemDate(DEFAULT_LAST_PROBLEM_DATE);
        return app;
    }

    @Before
    public void initTest() {
        app = createEntity(em);
    }

    @Test
    @Transactional
    public void createApp() throws Exception {
        int databaseSizeBeforeCreate = appRepository.findAll().size();

        // Create the App
        AppDTO appDTO = appMapper.toDto(app);
        restAppMockMvc.perform(post("/api/apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appDTO)))
            .andExpect(status().isCreated());

        // Validate the App in the database
        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeCreate + 1);
        App testApp = appList.get(appList.size() - 1);
        assertThat(testApp.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApp.getProblemCount()).isEqualTo(DEFAULT_PROBLEM_COUNT);
        assertThat(testApp.getSysStatus()).isEqualTo(DEFAULT_SYS_STATUS);
        assertThat(testApp.getLastProblemDate()).isEqualTo(DEFAULT_LAST_PROBLEM_DATE);
    }

    @Test
    @Transactional
    public void createAppWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appRepository.findAll().size();

        // Create the App with an existing ID
        app.setId(1L);
        AppDTO appDTO = appMapper.toDto(app);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppMockMvc.perform(post("/api/apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appDTO)))
            .andExpect(status().isBadRequest());

        // Validate the App in the database
        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRepository.findAll().size();
        // set the field null
        app.setName(null);

        // Create the App, which fails.
        AppDTO appDTO = appMapper.toDto(app);

        restAppMockMvc.perform(post("/api/apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appDTO)))
            .andExpect(status().isBadRequest());

        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProblemCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRepository.findAll().size();
        // set the field null
        app.setProblemCount(null);

        // Create the App, which fails.
        AppDTO appDTO = appMapper.toDto(app);

        restAppMockMvc.perform(post("/api/apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appDTO)))
            .andExpect(status().isBadRequest());

        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSysStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRepository.findAll().size();
        // set the field null
        app.setSysStatus(null);

        // Create the App, which fails.
        AppDTO appDTO = appMapper.toDto(app);

        restAppMockMvc.perform(post("/api/apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appDTO)))
            .andExpect(status().isBadRequest());

        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApps() throws Exception {
        // Initialize the database
        appRepository.saveAndFlush(app);

        // Get all the appList
        restAppMockMvc.perform(get("/api/apps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(app.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].problemCount").value(hasItem(DEFAULT_PROBLEM_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].sysStatus").value(hasItem(DEFAULT_SYS_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastProblemDate").value(hasItem(DEFAULT_LAST_PROBLEM_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getApp() throws Exception {
        // Initialize the database
        appRepository.saveAndFlush(app);

        // Get the app
        restAppMockMvc.perform(get("/api/apps/{id}", app.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(app.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.problemCount").value(DEFAULT_PROBLEM_COUNT.intValue()))
            .andExpect(jsonPath("$.sysStatus").value(DEFAULT_SYS_STATUS.toString()))
            .andExpect(jsonPath("$.lastProblemDate").value(DEFAULT_LAST_PROBLEM_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApp() throws Exception {
        // Get the app
        restAppMockMvc.perform(get("/api/apps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApp() throws Exception {
        // Initialize the database
        appRepository.saveAndFlush(app);

        int databaseSizeBeforeUpdate = appRepository.findAll().size();

        // Update the app
        App updatedApp = appRepository.findById(app.getId()).get();
        // Disconnect from session so that the updates on updatedApp are not directly saved in db
        em.detach(updatedApp);
        updatedApp
            .name(UPDATED_NAME)
            .problemCount(UPDATED_PROBLEM_COUNT)
            .sysStatus(UPDATED_SYS_STATUS)
            .lastProblemDate(UPDATED_LAST_PROBLEM_DATE);
        AppDTO appDTO = appMapper.toDto(updatedApp);

        restAppMockMvc.perform(put("/api/apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appDTO)))
            .andExpect(status().isOk());

        // Validate the App in the database
        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeUpdate);
        App testApp = appList.get(appList.size() - 1);
        assertThat(testApp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApp.getProblemCount()).isEqualTo(UPDATED_PROBLEM_COUNT);
        assertThat(testApp.getSysStatus()).isEqualTo(UPDATED_SYS_STATUS);
        assertThat(testApp.getLastProblemDate()).isEqualTo(UPDATED_LAST_PROBLEM_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingApp() throws Exception {
        int databaseSizeBeforeUpdate = appRepository.findAll().size();

        // Create the App
        AppDTO appDTO = appMapper.toDto(app);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppMockMvc.perform(put("/api/apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appDTO)))
            .andExpect(status().isBadRequest());

        // Validate the App in the database
        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApp() throws Exception {
        // Initialize the database
        appRepository.saveAndFlush(app);

        int databaseSizeBeforeDelete = appRepository.findAll().size();

        // Delete the app
        restAppMockMvc.perform(delete("/api/apps/{id}", app.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void checkGetAppName() throws Exception {
      // Initialize the database
      appRepository.saveAndFlush(app);

      // Get the app
      restAppMockMvc.perform(get("/api/apps/name"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void checkGetAppStatus() throws Exception {
      // Initialize the database
      appRepository.saveAndFlush(app);

      // Get the app
      restAppMockMvc.perform(get("/api/apps/status"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.sysMessage").value(SYS_MESSAGE))
        .andExpect(jsonPath("$.sysMessageColor").value(SYS_MESSAGE_COLOR))
        .andExpect(jsonPath("$.msgDetail").value(MSG_DETAIL))
        .andExpect(jsonPath("$.msgColor").value(MSG_COLOR))
        .andExpect(jsonPath("$.appStatus").value(DEFAULT_SYS_STATUS.toString()))
        .andExpect(jsonPath("$.probCount").value(PROB_COUNT));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(App.class);
        App app1 = new App();
        app1.setId(1L);
        App app2 = new App();
        app2.setId(app1.getId());
        assertThat(app1).isEqualTo(app2);
        app2.setId(2L);
        assertThat(app1).isNotEqualTo(app2);
        app1.setId(null);
        assertThat(app1).isNotEqualTo(app2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppDTO.class);
        AppDTO appDTO1 = new AppDTO();
        appDTO1.setId(1L);
        AppDTO appDTO2 = new AppDTO();
        assertThat(appDTO1).isNotEqualTo(appDTO2);
        appDTO2.setId(appDTO1.getId());
        assertThat(appDTO1).isEqualTo(appDTO2);
        appDTO2.setId(2L);
        assertThat(appDTO1).isNotEqualTo(appDTO2);
        appDTO1.setId(null);
        assertThat(appDTO1).isNotEqualTo(appDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(appMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(appMapper.fromId(null)).isNull();
    }
}
