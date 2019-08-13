package com.docswebapps.appsuppdash.web.rest;
import com.docswebapps.appsuppdash.domain.Problem;
import com.docswebapps.appsuppdash.domain.enumeration.IssueStatus;
import com.docswebapps.appsuppdash.domain.enumeration.Priority;
import com.docswebapps.appsuppdash.service.ProblemService;
import com.docswebapps.appsuppdash.web.rest.errors.BadRequestAlertException;
import com.docswebapps.appsuppdash.web.rest.util.HeaderUtil;
import com.docswebapps.appsuppdash.web.rest.util.PaginationUtil;
import com.docswebapps.appsuppdash.service.dto.ProblemDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Problem.
 */
@RestController
@RequestMapping("/api")
public class ProblemResource {
    private final Logger log = LoggerFactory.getLogger(ProblemResource.class);

    private static final String ENTITY_NAME = "problem";

    private final ProblemService problemService;

    public ProblemResource(ProblemService problemService) {
        this.problemService = problemService;
    }

    /**
     * POST  /problems : Create a new problem.
     *
     * @param problemDTO the problemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new problemDTO, or with status 400 (Bad Request) if the problem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/problems")
    public ResponseEntity<ProblemDTO> createProblem(@Valid @RequestBody ProblemDTO problemDTO) throws URISyntaxException {
        log.debug("ProblemResource: REST request to save Problem : {}", problemDTO);
        if (problemDTO.getId() != null) {
            throw new BadRequestAlertException("ProblemResource: A new problem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProblemDTO result = problemService.save(problemDTO);
        return ResponseEntity.created(new URI("/api/problems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /problems : Updates an existing problem.
     *
     * @param problemDTO the problemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated problemDTO,
     * or with status 400 (Bad Request) if the problemDTO is not valid,
     * or with status 500 (Internal Server Error) if the problemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/problems")
    public ResponseEntity<ProblemDTO> updateProblem(@Valid @RequestBody ProblemDTO problemDTO) throws URISyntaxException {
        log.debug("ProblemResource: REST request to update Problem : {}", problemDTO);
        if (problemDTO.getId() == null) {
            throw new BadRequestAlertException("ProblemResource: Invalid id", ENTITY_NAME, "idnull");
        }
        ProblemDTO result = problemService.save(problemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, problemDTO.getId().toString()))
            .body(result);
    }

    // My Custom Code
    /**
     * GET /problems/risk/{id}
     *
     * @param id - the risk id
     * @param pageable the pagination information
     * @return a list of Problems related to the risk
     */
    @GetMapping("/problems/risk/{id}")
    public ResponseEntity<List<ProblemDTO>> getRelatedProblems(Pageable pageable, @PathVariable Long id) {
      log.debug("Problem Resource: REST request to get all related problems for risk id: {}", id);
      Page<ProblemDTO> page = problemService.getRelatedProblems(pageable, id);
      HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/problems/risk/{id}");
      return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /problems/{status}/{priority} : get all the problems.
     *
     * @param pageable the pagination information
     * @param status  OPEN or CLOSED
     * @param priority ALL, HIGH, MEDIUM or LOW
     * @return the ResponseEntity with status 200 (OK) and the list of problems in body
     */
    @GetMapping("/problems/{status}/{priority}")
    public ResponseEntity<List<ProblemDTO>> getAllProblems(Pageable pageable,
                                                           @PathVariable IssueStatus status,
                                                           @PathVariable Priority priority) {
        log.debug("ProblemResource: REST request to get a page of Problems: {} AND {}", status, priority);
        Page<ProblemDTO> page = problemService.findAll(pageable, status, priority);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/problems");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /problems/:id : get the "id" problem.
     *
     * @param id the id of the problemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the problemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/problems/{id}")
    public ResponseEntity<ProblemDTO> getProblem(@PathVariable Long id) {
        log.debug("ProblemResource: REST request to get Problem : {}", id);
        ProblemDTO problemDTO = problemService.findOne(id);
        return ResponseEntity.ok().body(problemDTO);
    }

    /**
     * DELETE  /problems/:id : delete the "id" problem.
     *
     * @param id the id of the problemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/problems/{id}")
    public ResponseEntity<Void> deleteProblem(@PathVariable Long id) {
        log.debug("ProblemResource: REST request to delete Problem : {}", id);
        problemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
