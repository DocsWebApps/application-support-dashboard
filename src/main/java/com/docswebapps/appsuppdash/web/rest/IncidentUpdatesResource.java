package com.docswebapps.appsuppdash.web.rest;
import com.docswebapps.appsuppdash.service.IncidentUpdatesService;
import com.docswebapps.appsuppdash.web.rest.errors.BadRequestAlertException;
import com.docswebapps.appsuppdash.web.rest.util.HeaderUtil;
import com.docswebapps.appsuppdash.web.rest.util.PaginationUtil;
import com.docswebapps.appsuppdash.service.dto.IncidentUpdatesDTO;
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
 * REST controller for managing IncidentUpdates.
 */
@RestController
@RequestMapping("/api")
public class IncidentUpdatesResource {

    private final Logger log = LoggerFactory.getLogger(IncidentUpdatesResource.class);

    private static final String ENTITY_NAME = "incidentUpdates";

    private final IncidentUpdatesService incidentUpdatesService;

    public IncidentUpdatesResource(IncidentUpdatesService incidentUpdatesService) {
        this.incidentUpdatesService = incidentUpdatesService;
    }

    /**
     * POST  /incident-updates : Create a new incidentUpdates.
     *
     * @param incidentUpdatesDTO the incidentUpdatesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new incidentUpdatesDTO, or with status 400 (Bad Request) if the incidentUpdates has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/incident-updates")
    public ResponseEntity<IncidentUpdatesDTO> createIncidentUpdates(@Valid @RequestBody IncidentUpdatesDTO incidentUpdatesDTO) throws URISyntaxException {
        log.debug("REST request to save IncidentUpdates : {}", incidentUpdatesDTO);
        if (incidentUpdatesDTO.getId() != null) {
            throw new BadRequestAlertException("A new incidentUpdates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncidentUpdatesDTO result = incidentUpdatesService.save(incidentUpdatesDTO);
        return ResponseEntity.created(new URI("/api/incident-updates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /incident-updates : Updates an existing incidentUpdates.
     *
     * @param incidentUpdatesDTO the incidentUpdatesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated incidentUpdatesDTO,
     * or with status 400 (Bad Request) if the incidentUpdatesDTO is not valid,
     * or with status 500 (Internal Server Error) if the incidentUpdatesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/incident-updates")
    public ResponseEntity<IncidentUpdatesDTO> updateIncidentUpdates(@Valid @RequestBody IncidentUpdatesDTO incidentUpdatesDTO) throws URISyntaxException {
        log.debug("REST request to update IncidentUpdates : {}", incidentUpdatesDTO);
        if (incidentUpdatesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IncidentUpdatesDTO result = incidentUpdatesService.save(incidentUpdatesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, incidentUpdatesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /incident-updates : get all the incidentUpdates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of incidentUpdates in body
     */
    @GetMapping("/incident-updates")
    public ResponseEntity<List<IncidentUpdatesDTO>> getAllIncidentUpdates(Pageable pageable) {
        log.debug("REST request to get a page of IncidentUpdates");
        Page<IncidentUpdatesDTO> page = incidentUpdatesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/incident-updates");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /incident-updates/:id : get the "id" incidentUpdates.
     *
     * @param id the id of the incidentUpdatesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the incidentUpdatesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/incident-updates/{id}")
    public ResponseEntity<IncidentUpdatesDTO> getIncidentUpdates(@PathVariable Long id) {
        log.debug("REST request to get IncidentUpdates : {}", id);
        Optional<IncidentUpdatesDTO> incidentUpdatesDTO = incidentUpdatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incidentUpdatesDTO);
    }

    /**
     * DELETE  /incident-updates/:id : delete the "id" incidentUpdates.
     *
     * @param id the id of the incidentUpdatesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/incident-updates/{id}")
    public ResponseEntity<Void> deleteIncidentUpdates(@PathVariable Long id) {
        log.debug("REST request to delete IncidentUpdates : {}", id);
        incidentUpdatesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
