package com.docswebapps.appsuppdash.web.rest;

import com.docswebapps.appsuppdash.ApplicationSupportDashboardApp;

import com.docswebapps.appsuppdash.domain.Problem;
import com.docswebapps.appsuppdash.repository.ProblemRepository;
import com.docswebapps.appsuppdash.service.ProblemService;
import com.docswebapps.appsuppdash.service.dto.ProblemDTO;
import com.docswebapps.appsuppdash.service.mapper.ProblemMapper;
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
 * Test class for the ProblemResource REST controller.
 *
 * @see ProblemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationSupportDashboardApp.class)
public class ProblemResourceIntTest {

    private static final LocalDate DEFAULT_OPENED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OPENED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_STATEMENT = "AAAAAAAAAA";
    private static final String UPDATED_STATEMENT = "BBBBBBBBBB";

    private static final IssueStatus DEFAULT_PROB_STATUS = IssueStatus.OPEN;
    private static final IssueStatus UPDATED_PROB_STATUS = IssueStatus.CLOSED;

    private static final Priority DEFAULT_PRIORITY = Priority.LOW;
    private static final Priority UPDATED_PRIORITY = Priority.MEDIUM;

    private static final LocalDate DEFAULT_CLOSED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CLOSED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private ProblemService problemService;

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

    private MockMvc restProblemMockMvc;

    private Problem problem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProblemResource problemResource = new ProblemResource(problemService);
        this.restProblemMockMvc = MockMvcBuilders.standaloneSetup(problemResource)
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
    public static Problem createEntity(EntityManager em) {
        Problem problem = new Problem()
            .openedAt(DEFAULT_OPENED_AT)
            .title(DEFAULT_TITLE)
            .statement(DEFAULT_STATEMENT)
            .probStatus(DEFAULT_PROB_STATUS)
            .priority(DEFAULT_PRIORITY)
            .closedAt(DEFAULT_CLOSED_AT);
        return problem;
    }

    @Before
    public void initTest() {
        problem = createEntity(em);
    }

    @Test
    @Transactional
    public void createProblem() throws Exception {
        int databaseSizeBeforeCreate = problemRepository.findAll().size();

        // Create the Problem
        ProblemDTO problemDTO = problemMapper.toDto(problem);
        restProblemMockMvc.perform(post("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemDTO)))
            .andExpect(status().isCreated());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeCreate + 1);
        Problem testProblem = problemList.get(problemList.size() - 1);
        assertThat(testProblem.getOpenedAt()).isEqualTo(DEFAULT_OPENED_AT);
        assertThat(testProblem.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProblem.getStatement()).isEqualTo(DEFAULT_STATEMENT);
        assertThat(testProblem.getProbStatus()).isEqualTo(DEFAULT_PROB_STATUS);
        assertThat(testProblem.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testProblem.getClosedAt()).isEqualTo(DEFAULT_CLOSED_AT);
    }

    @Test
    @Transactional
    public void createProblemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = problemRepository.findAll().size();

        // Create the Problem with an existing ID
        problem.setId(1L);
        ProblemDTO problemDTO = problemMapper.toDto(problem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProblemMockMvc.perform(post("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOpenedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = problemRepository.findAll().size();
        // set the field null
        problem.setOpenedAt(null);

        // Create the Problem, which fails.
        ProblemDTO problemDTO = problemMapper.toDto(problem);

        restProblemMockMvc.perform(post("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemDTO)))
            .andExpect(status().isBadRequest());

        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = problemRepository.findAll().size();
        // set the field null
        problem.setTitle(null);

        // Create the Problem, which fails.
        ProblemDTO problemDTO = problemMapper.toDto(problem);

        restProblemMockMvc.perform(post("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemDTO)))
            .andExpect(status().isBadRequest());

        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProbStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = problemRepository.findAll().size();
        // set the field null
        problem.setProbStatus(null);

        // Create the Problem, which fails.
        ProblemDTO problemDTO = problemMapper.toDto(problem);

        restProblemMockMvc.perform(post("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemDTO)))
            .andExpect(status().isBadRequest());

        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = problemRepository.findAll().size();
        // set the field null
        problem.setPriority(null);

        // Create the Problem, which fails.
        ProblemDTO problemDTO = problemMapper.toDto(problem);

        restProblemMockMvc.perform(post("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemDTO)))
            .andExpect(status().isBadRequest());

        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAllProblemCombos() throws Exception {
          this.getAllProblems(IssueStatus.ALL, Priority.ALL);
          this.getAllProblems(IssueStatus.ALL, Priority.HIGH);
          this.getAllProblems(IssueStatus.ALL, Priority.MEDIUM);
          this.getAllProblems(IssueStatus.ALL, Priority.LOW);
          this.getAllProblems(IssueStatus.OPEN, Priority.ALL);
          this.getAllProblems(IssueStatus.OPEN, Priority.HIGH);
          this.getAllProblems(IssueStatus.OPEN, Priority.MEDIUM);
          this.getAllProblems(IssueStatus.OPEN, Priority.LOW);
          this.getAllProblems(IssueStatus.CLOSED, Priority.ALL);
          this.getAllProblems(IssueStatus.CLOSED, Priority.HIGH);
          this.getAllProblems(IssueStatus.CLOSED, Priority.MEDIUM);
          this.getAllProblems(IssueStatus.CLOSED, Priority.LOW);
    }

    @Transactional
    public void getAllProblems(IssueStatus status, Priority priority) throws Exception {
        // Initialize the database
        Problem testProblem = new Problem()
            .openedAt(DEFAULT_OPENED_AT)
            .title(DEFAULT_TITLE)
            .statement(DEFAULT_STATEMENT)
            .probStatus(status)
            .priority(priority)
            .closedAt(DEFAULT_CLOSED_AT);

        problemRepository.saveAndFlush(testProblem);

        // Get all the incidentList
        restProblemMockMvc.perform(get("/api/problems/{status}/{severity}", status, priority))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testProblem.getId().intValue())))
            .andExpect(jsonPath("$.[*].openedAt").value(hasItem(DEFAULT_OPENED_AT.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].statement").value(hasItem(DEFAULT_STATEMENT.toString())))
            .andExpect(jsonPath("$.[*].probStatus").value(hasItem(status.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(priority.toString())))
            .andExpect(jsonPath("$.[*].closedAt").value(hasItem(DEFAULT_CLOSED_AT.toString())));

        // Clean Up DataBase
        problemRepository.delete(testProblem);
    }

    
    @Test
    @Transactional
    public void getProblem() throws Exception {
        // Initialize the database
        problemRepository.saveAndFlush(problem);

        // Get the problem
        restProblemMockMvc.perform(get("/api/problems/{id}", problem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(problem.getId().intValue()))
            .andExpect(jsonPath("$.openedAt").value(DEFAULT_OPENED_AT.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.statement").value(DEFAULT_STATEMENT.toString()))
            .andExpect(jsonPath("$.probStatus").value(DEFAULT_PROB_STATUS.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.closedAt").value(DEFAULT_CLOSED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProblem() throws Exception {
        // Get the problem
        restProblemMockMvc.perform(get("/api/problems/{id}", Long.MAX_VALUE))
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void updateProblem() throws Exception {
        // Initialize the database
        problemRepository.saveAndFlush(problem);

        int databaseSizeBeforeUpdate = problemRepository.findAll().size();

        // Update the problem
        Problem updatedProblem = problemRepository.findById(problem.getId()).get();
        // Disconnect from session so that the updates on updatedProblem are not directly saved in db
        em.detach(updatedProblem);
        updatedProblem
            .openedAt(UPDATED_OPENED_AT)
            .title(UPDATED_TITLE)
            .statement(UPDATED_STATEMENT)
            .probStatus(UPDATED_PROB_STATUS)
            .priority(UPDATED_PRIORITY)
            .closedAt(UPDATED_CLOSED_AT);
        ProblemDTO problemDTO = problemMapper.toDto(updatedProblem);

        restProblemMockMvc.perform(put("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemDTO)))
            .andExpect(status().isOk());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeUpdate);
        Problem testProblem = problemList.get(problemList.size() - 1);
        assertThat(testProblem.getOpenedAt()).isEqualTo(UPDATED_OPENED_AT);
        assertThat(testProblem.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProblem.getStatement()).isEqualTo(UPDATED_STATEMENT);
        assertThat(testProblem.getProbStatus()).isEqualTo(UPDATED_PROB_STATUS);
        assertThat(testProblem.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testProblem.getClosedAt()).isEqualTo(UPDATED_CLOSED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingProblem() throws Exception {
        int databaseSizeBeforeUpdate = problemRepository.findAll().size();

        // Create the Problem
        ProblemDTO problemDTO = problemMapper.toDto(problem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProblemMockMvc.perform(put("/api/problems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(problemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProblem() throws Exception {
        // Initialize the database
        problemRepository.saveAndFlush(problem);

        int databaseSizeBeforeDelete = problemRepository.findAll().size();

        // Delete the problem
        restProblemMockMvc.perform(delete("/api/problems/{id}", problem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Problem.class);
        Problem problem1 = new Problem();
        problem1.setId(1L);
        Problem problem2 = new Problem();
        problem2.setId(problem1.getId());
        assertThat(problem1).isEqualTo(problem2);
        problem2.setId(2L);
        assertThat(problem1).isNotEqualTo(problem2);
        problem1.setId(null);
        assertThat(problem1).isNotEqualTo(problem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProblemDTO.class);
        ProblemDTO problemDTO1 = new ProblemDTO();
        problemDTO1.setId(1L);
        ProblemDTO problemDTO2 = new ProblemDTO();
        assertThat(problemDTO1).isNotEqualTo(problemDTO2);
        problemDTO2.setId(problemDTO1.getId());
        assertThat(problemDTO1).isEqualTo(problemDTO2);
        problemDTO2.setId(2L);
        assertThat(problemDTO1).isNotEqualTo(problemDTO2);
        problemDTO1.setId(null);
        assertThat(problemDTO1).isNotEqualTo(problemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(problemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(problemMapper.fromId(null)).isNull();
    }
}
