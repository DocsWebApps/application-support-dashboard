package com.docswebapps.appsuppdash.web.rest;
import com.docswebapps.appsuppdash.service.ProblemUpdatesService;
import com.docswebapps.appsuppdash.web.rest.errors.BadRequestAlertException;
import com.docswebapps.appsuppdash.web.rest.util.HeaderUtil;
import com.docswebapps.appsuppdash.web.rest.util.PaginationUtil;
import com.docswebapps.appsuppdash.service.dto.ProblemUpdatesDTO;
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
 * REST controller for managing ProblemUpdates.
 */
@RestController
@RequestMapping("/api")
public class ProblemUpdatesResource {

    private final Logger log = LoggerFactory.getLogger(ProblemUpdatesResource.class);

    private static final String ENTITY_NAME = "problemUpdates";

    private final ProblemUpdatesService problemUpdatesService;

    public ProblemUpdatesResource(ProblemUpdatesService problemUpdatesService) {
        this.problemUpdatesService = problemUpdatesService;
    }

    /**
     * POST  /problem-updates : Create a new problemUpdates.
     *
     * @param problemUpdatesDTO the problemUpdatesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new problemUpdatesDTO, or with status 400 (Bad Request) if the problemUpdates has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/problem-updates")
    public ResponseEntity<ProblemUpdatesDTO> createProblemUpdates(@Valid @RequestBody ProblemUpdatesDTO problemUpdatesDTO) throws URISyntaxException {
        log.debug("ProblemUpdatesResource: REST request to save ProblemUpdates : {}", problemUpdatesDTO);
        if (problemUpdatesDTO.getId() != null) {
            throw new BadRequestAlertException("ProblemUpdatesResource: A new problemUpdates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProblemUpdatesDTO result = problemUpdatesService.save(problemUpdatesDTO);
        return ResponseEntity.created(new URI("/api/problem-updates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /problem-updates : Updates an existing problemUpdates.
     *
     * @param problemUpdatesDTO the problemUpdatesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated problemUpdatesDTO,
     * or with status 400 (Bad Request) if the problemUpdatesDTO is not valid,
     * or with status 500 (Internal Server Error) if the problemUpdatesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/problem-updates")
    public ResponseEntity<ProblemUpdatesDTO> updateProblemUpdates(@Valid @RequestBody ProblemUpdatesDTO problemUpdatesDTO) throws URISyntaxException {
        log.debug("ProblemUpdatesResource: REST request to update ProblemUpdates : {}", problemUpdatesDTO);
        if (problemUpdatesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProblemUpdatesDTO result = problemUpdatesService.save(problemUpdatesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, problemUpdatesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /problem-updates : get all the problemUpdates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of problemUpdates in body
     */
    @GetMapping("/problem-updates")
    public ResponseEntity<List<ProblemUpdatesDTO>> getAllProblemUpdates(Pageable pageable) {
        log.debug("ProblemUpdatesResource: REST request to get a page of ProblemUpdates");
        Page<ProblemUpdatesDTO> page = problemUpdatesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/problem-updates");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /problem-updates/:id : get the "id" problemUpdates.
     *
     * @param id the id of the problemUpdatesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the problemUpdatesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/problem-updates/{id}")
    public ResponseEntity<ProblemUpdatesDTO> getProblemUpdates(@PathVariable Long id) {
        log.debug("ProblemUpdatesResource: REST request to get ProblemUpdates : {}", id);
        Optional<ProblemUpdatesDTO> problemUpdatesDTO = problemUpdatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(problemUpdatesDTO);
    }

    /**
     * DELETE  /problem-updates/:id : delete the "id" problemUpdates.
     *
     * @param id the id of the problemUpdatesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/problem-updates/{id}")
    public ResponseEntity<Void> deleteProblemUpdates(@PathVariable Long id) {
        log.debug("ProblemUpdatesResource: REST request to delete ProblemUpdates : {}", id);
        problemUpdatesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
