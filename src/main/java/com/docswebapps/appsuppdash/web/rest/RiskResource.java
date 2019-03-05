package com.docswebapps.appsuppdash.web.rest;
import com.docswebapps.appsuppdash.service.RiskService;
import com.docswebapps.appsuppdash.web.rest.errors.BadRequestAlertException;
import com.docswebapps.appsuppdash.web.rest.util.HeaderUtil;
import com.docswebapps.appsuppdash.web.rest.util.PaginationUtil;
import com.docswebapps.appsuppdash.service.dto.RiskDTO;
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
 * REST controller for managing Risk.
 */
@RestController
@RequestMapping("/api")
public class RiskResource {

    private final Logger log = LoggerFactory.getLogger(RiskResource.class);

    private static final String ENTITY_NAME = "risk";

    private final RiskService riskService;

    public RiskResource(RiskService riskService) {
        this.riskService = riskService;
    }

    /**
     * POST  /risks : Create a new risk.
     *
     * @param riskDTO the riskDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new riskDTO, or with status 400 (Bad Request) if the risk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/risks")
    public ResponseEntity<RiskDTO> createRisk(@Valid @RequestBody RiskDTO riskDTO) throws URISyntaxException {
        log.debug("RiskResource: REST request to save Risk : {}", riskDTO);
        if (riskDTO.getId() != null) {
            throw new BadRequestAlertException("RiskResource: A new risk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiskDTO result = riskService.save(riskDTO);
        return ResponseEntity.created(new URI("/api/risks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /risks : Updates an existing risk.
     *
     * @param riskDTO the riskDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated riskDTO,
     * or with status 400 (Bad Request) if the riskDTO is not valid,
     * or with status 500 (Internal Server Error) if the riskDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/risks")
    public ResponseEntity<RiskDTO> updateRisk(@Valid @RequestBody RiskDTO riskDTO) throws URISyntaxException {
        log.debug("RiskResource: REST request to update Risk : {}", riskDTO);
        if (riskDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiskDTO result = riskService.save(riskDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, riskDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /risks : get all the risks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of risks in body
     */
    @GetMapping("/risks")
    public ResponseEntity<List<RiskDTO>> getAllRisks(Pageable pageable) {
        log.debug("RiskResource: REST request to get a page of Risks");
        Page<RiskDTO> page = riskService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/risks");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /risks/:id : get the "id" risk.
     *
     * @param id the id of the riskDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the riskDTO, or with status 404 (Not Found)
     */
    @GetMapping("/risks/{id}")
    public ResponseEntity<RiskDTO> getRisk(@PathVariable Long id) {
        log.debug("RiskResource: REST request to get Risk : {}", id);
        Optional<RiskDTO> riskDTO = riskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(riskDTO);
    }

    /**
     * DELETE  /risks/:id : delete the "id" risk.
     *
     * @param id the id of the riskDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/risks/{id}")
    public ResponseEntity<Void> deleteRisk(@PathVariable Long id) {
        log.debug("RiskResource: REST request to delete Risk : {}", id);
        riskService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
