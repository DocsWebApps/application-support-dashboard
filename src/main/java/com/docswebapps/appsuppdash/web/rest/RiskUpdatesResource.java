package com.docswebapps.appsuppdash.web.rest;
import com.docswebapps.appsuppdash.service.RiskUpdatesService;
import com.docswebapps.appsuppdash.web.rest.errors.BadRequestAlertException;
import com.docswebapps.appsuppdash.web.rest.util.HeaderUtil;
import com.docswebapps.appsuppdash.web.rest.util.PaginationUtil;
import com.docswebapps.appsuppdash.service.dto.RiskUpdatesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RiskUpdates.
 */
@RestController
@RequestMapping("/api")
public class RiskUpdatesResource {

    private final Logger log = LoggerFactory.getLogger(RiskUpdatesResource.class);

    private static final String ENTITY_NAME = "riskUpdates";

    private final RiskUpdatesService riskUpdatesService;

    public RiskUpdatesResource(RiskUpdatesService riskUpdatesService) {
        this.riskUpdatesService = riskUpdatesService;
    }

    /**
     * POST  /risk-updates : Create a new riskUpdates.
     *
     * @param riskUpdatesDTO the riskUpdatesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new riskUpdatesDTO, or with status 400 (Bad Request) if the riskUpdates has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/risk-updates")
    public ResponseEntity<RiskUpdatesDTO> createRiskUpdates(@Valid @RequestBody RiskUpdatesDTO riskUpdatesDTO) throws URISyntaxException {
        log.debug("RiskUpdatesResource: REST request to save RiskUpdates : {}", riskUpdatesDTO);
        if (riskUpdatesDTO.getId() != null) {
            throw new BadRequestAlertException("RiskUpdatesResource: A new riskUpdates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiskUpdatesDTO result = riskUpdatesService.save(riskUpdatesDTO);
        return ResponseEntity.created(new URI("/api/risk-updates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /risk-updates : Updates an existing riskUpdates.
     *
     * @param riskUpdatesDTO the riskUpdatesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated riskUpdatesDTO,
     * or with status 400 (Bad Request) if the riskUpdatesDTO is not valid,
     * or with status 500 (Internal Server Error) if the riskUpdatesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/risk-updates")
    public ResponseEntity<RiskUpdatesDTO> updateRiskUpdates(@Valid @RequestBody RiskUpdatesDTO riskUpdatesDTO) throws URISyntaxException {
        log.debug("RiskUpdatesResource: REST request to update RiskUpdates : {}", riskUpdatesDTO);
        if (riskUpdatesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiskUpdatesDTO result = riskUpdatesService.save(riskUpdatesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, riskUpdatesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /risk-updates : get all the riskUpdates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of riskUpdates in body
     */
    @GetMapping("/risk-updates")
    public ResponseEntity<List<RiskUpdatesDTO>> getAllRiskUpdates(Pageable pageable) {
        log.debug("RiskUpdatesResource: REST request to get a page of RiskUpdates");
        Page<RiskUpdatesDTO> page = riskUpdatesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/risk-updates");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /risk-updates/:id : get the "id" riskUpdates.
     *
     * @param id the id of the riskUpdatesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the riskUpdatesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/risk-updates/{id}")
    public ResponseEntity<RiskUpdatesDTO> getRiskUpdates(@PathVariable Long id) {
        log.debug("RiskUpdatesResource: REST request to get RiskUpdates : {}", id);
        Optional<RiskUpdatesDTO> riskUpdatesDTO = riskUpdatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(riskUpdatesDTO);
    }

    /**
     * DELETE  /risk-updates/:id : delete the "id" riskUpdates.
     *
     * @param id the id of the riskUpdatesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/risk-updates/{id}")
    public ResponseEntity<Void> deleteRiskUpdates(@PathVariable Long id) {
        log.debug("RiskUpdatesResource: REST request to delete RiskUpdates : {}", id);
        riskUpdatesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
