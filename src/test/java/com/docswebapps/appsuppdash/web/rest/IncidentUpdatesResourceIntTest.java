package com.docswebapps.appsuppdash.web.rest;

import com.docswebapps.appsuppdash.ApplicationSupportDashboardApp;

import com.docswebapps.appsuppdash.domain.IncidentUpdates;
import com.docswebapps.appsuppdash.domain.Incident;
import com.docswebapps.appsuppdash.repository.IncidentUpdatesRepository;
import com.docswebapps.appsuppdash.service.IncidentUpdatesService;
import com.docswebapps.appsuppdash.service.dto.IncidentUpdatesDTO;
import com.docswebapps.appsuppdash.service.mapper.IncidentUpdatesMapper;
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
 * Test class for the IncidentUpdatesResource REST controller.
 *
 * @see IncidentUpdatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationSupportDashboardApp.class)
public class IncidentUpdatesResourceIntTest {

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_TEXT = "BBBBBBBBBB";

    @Autowired
    private IncidentUpdatesRepository incidentUpdatesRepository;

    @Autowired
    private IncidentUpdatesMapper incidentUpdatesMapper;

    @Autowired
    private IncidentUpdatesService incidentUpdatesService;

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

    private MockMvc restIncidentUpdatesMockMvc;

    private IncidentUpdates incidentUpdates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncidentUpdatesResource incidentUpdatesResource = new IncidentUpdatesResource(incidentUpdatesService);
        this.restIncidentUpdatesMockMvc = MockMvcBuilders.standaloneSetup(incidentUpdatesResource)
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
    public static IncidentUpdates createEntity(EntityManager em) {
        IncidentUpdates incidentUpdates = new IncidentUpdates()
            .updatedAt(DEFAULT_UPDATED_AT)
            .updateText(DEFAULT_UPDATE_TEXT);
        // Add required entity
        Incident incident = IncidentResourceIntTest.createEntity(em);
        em.persist(incident);
        em.flush();
        incidentUpdates.setInUpdate(incident);
        return incidentUpdates;
    }

    @Before
    public void initTest() {
        incidentUpdates = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncidentUpdates() throws Exception {
        int databaseSizeBeforeCreate = incidentUpdatesRepository.findAll().size();

        // Create the IncidentUpdates
        IncidentUpdatesDTO incidentUpdatesDTO = incidentUpdatesMapper.toDto(incidentUpdates);
        restIncidentUpdatesMockMvc.perform(post("/api/incident-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentUpdatesDTO)))
            .andExpect(status().isCreated());

        // Validate the IncidentUpdates in the database
        List<IncidentUpdates> incidentUpdatesList = incidentUpdatesRepository.findAll();
        assertThat(incidentUpdatesList).hasSize(databaseSizeBeforeCreate + 1);
        IncidentUpdates testIncidentUpdates = incidentUpdatesList.get(incidentUpdatesList.size() - 1);
        assertThat(testIncidentUpdates.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testIncidentUpdates.getUpdateText()).isEqualTo(DEFAULT_UPDATE_TEXT);
    }

    @Test
    @Transactional
    public void createIncidentUpdatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incidentUpdatesRepository.findAll().size();

        // Create the IncidentUpdates with an existing ID
        incidentUpdates.setId(1L);
        IncidentUpdatesDTO incidentUpdatesDTO = incidentUpdatesMapper.toDto(incidentUpdates);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidentUpdatesMockMvc.perform(post("/api/incident-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentUpdatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncidentUpdates in the database
        List<IncidentUpdates> incidentUpdatesList = incidentUpdatesRepository.findAll();
        assertThat(incidentUpdatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentUpdatesRepository.findAll().size();
        // set the field null
        incidentUpdates.setUpdatedAt(null);

        // Create the IncidentUpdates, which fails.
        IncidentUpdatesDTO incidentUpdatesDTO = incidentUpdatesMapper.toDto(incidentUpdates);

        restIncidentUpdatesMockMvc.perform(post("/api/incident-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentUpdatesDTO)))
            .andExpect(status().isBadRequest());

        List<IncidentUpdates> incidentUpdatesList = incidentUpdatesRepository.findAll();
        assertThat(incidentUpdatesList).hasSize(databaseSizeBeforeTest);
    }

//    @Test
//    @Transactional
//    public void getAllIncidentUpdates() throws Exception {
//        // Initialize the database
//        incidentUpdatesRepository.saveAndFlush(incidentUpdates);
//
//        // Get all the incidentUpdatesList
//        restIncidentUpdatesMockMvc.perform(get("/api/incident-updates?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(incidentUpdates.getId().intValue())))
//            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
//            .andExpect(jsonPath("$.[*].updateText").value(hasItem(DEFAULT_UPDATE_TEXT.toString())));
//    }
    
    @Test
    @Transactional
    public void getIncidentUpdates() throws Exception {
        // Initialize the database
        incidentUpdatesRepository.saveAndFlush(incidentUpdates);

        // Get the incidentUpdates
        restIncidentUpdatesMockMvc.perform(get("/api/incident-updates/{id}", incidentUpdates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incidentUpdates.getId().intValue()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.updateText").value(DEFAULT_UPDATE_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIncidentUpdates() throws Exception {
        // Get the incidentUpdates
        restIncidentUpdatesMockMvc.perform(get("/api/incident-updates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncidentUpdates() throws Exception {
        // Initialize the database
        incidentUpdatesRepository.saveAndFlush(incidentUpdates);

        int databaseSizeBeforeUpdate = incidentUpdatesRepository.findAll().size();

        // Update the incidentUpdates
        IncidentUpdates updatedIncidentUpdates = incidentUpdatesRepository.findById(incidentUpdates.getId()).get();
        // Disconnect from session so that the updates on updatedIncidentUpdates are not directly saved in db
        em.detach(updatedIncidentUpdates);
        updatedIncidentUpdates
            .updatedAt(UPDATED_UPDATED_AT)
            .updateText(UPDATED_UPDATE_TEXT);
        IncidentUpdatesDTO incidentUpdatesDTO = incidentUpdatesMapper.toDto(updatedIncidentUpdates);

        restIncidentUpdatesMockMvc.perform(put("/api/incident-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentUpdatesDTO)))
            .andExpect(status().isOk());

        // Validate the IncidentUpdates in the database
        List<IncidentUpdates> incidentUpdatesList = incidentUpdatesRepository.findAll();
        assertThat(incidentUpdatesList).hasSize(databaseSizeBeforeUpdate);
        IncidentUpdates testIncidentUpdates = incidentUpdatesList.get(incidentUpdatesList.size() - 1);
        assertThat(testIncidentUpdates.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testIncidentUpdates.getUpdateText()).isEqualTo(UPDATED_UPDATE_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingIncidentUpdates() throws Exception {
        int databaseSizeBeforeUpdate = incidentUpdatesRepository.findAll().size();

        // Create the IncidentUpdates
        IncidentUpdatesDTO incidentUpdatesDTO = incidentUpdatesMapper.toDto(incidentUpdates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncidentUpdatesMockMvc.perform(put("/api/incident-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentUpdatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncidentUpdates in the database
        List<IncidentUpdates> incidentUpdatesList = incidentUpdatesRepository.findAll();
        assertThat(incidentUpdatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncidentUpdates() throws Exception {
        // Initialize the database
        incidentUpdatesRepository.saveAndFlush(incidentUpdates);

        int databaseSizeBeforeDelete = incidentUpdatesRepository.findAll().size();

        // Delete the incidentUpdates
        restIncidentUpdatesMockMvc.perform(delete("/api/incident-updates/{id}", incidentUpdates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IncidentUpdates> incidentUpdatesList = incidentUpdatesRepository.findAll();
        assertThat(incidentUpdatesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidentUpdates.class);
        IncidentUpdates incidentUpdates1 = new IncidentUpdates();
        incidentUpdates1.setId(1L);
        IncidentUpdates incidentUpdates2 = new IncidentUpdates();
        incidentUpdates2.setId(incidentUpdates1.getId());
        assertThat(incidentUpdates1).isEqualTo(incidentUpdates2);
        incidentUpdates2.setId(2L);
        assertThat(incidentUpdates1).isNotEqualTo(incidentUpdates2);
        incidentUpdates1.setId(null);
        assertThat(incidentUpdates1).isNotEqualTo(incidentUpdates2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidentUpdatesDTO.class);
        IncidentUpdatesDTO incidentUpdatesDTO1 = new IncidentUpdatesDTO();
        incidentUpdatesDTO1.setId(1L);
        IncidentUpdatesDTO incidentUpdatesDTO2 = new IncidentUpdatesDTO();
        assertThat(incidentUpdatesDTO1).isNotEqualTo(incidentUpdatesDTO2);
        incidentUpdatesDTO2.setId(incidentUpdatesDTO1.getId());
        assertThat(incidentUpdatesDTO1).isEqualTo(incidentUpdatesDTO2);
        incidentUpdatesDTO2.setId(2L);
        assertThat(incidentUpdatesDTO1).isNotEqualTo(incidentUpdatesDTO2);
        incidentUpdatesDTO1.setId(null);
        assertThat(incidentUpdatesDTO1).isNotEqualTo(incidentUpdatesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(incidentUpdatesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(incidentUpdatesMapper.fromId(null)).isNull();
    }
}
