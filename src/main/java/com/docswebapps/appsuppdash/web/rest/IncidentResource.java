package com.docswebapps.appsuppdash.web.rest;
import com.docswebapps.appsuppdash.domain.BannerStats;
import com.docswebapps.appsuppdash.domain.enumeration.IssueStatus;
import com.docswebapps.appsuppdash.domain.enumeration.Severity;
import com.docswebapps.appsuppdash.service.IncidentService;
import com.docswebapps.appsuppdash.web.rest.errors.BadRequestAlertException;
import com.docswebapps.appsuppdash.web.rest.util.HeaderUtil;
import com.docswebapps.appsuppdash.web.rest.util.PaginationUtil;
import com.docswebapps.appsuppdash.service.dto.IncidentDTO;
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
 * REST controller for managing Incident.
 */
@RestController
@RequestMapping("/api")
public class IncidentResource {
    private final Logger log = LoggerFactory.getLogger(IncidentResource.class);

    private static final String ENTITY_NAME = "incident";

    private final IncidentService incidentService;

    public IncidentResource(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    /**
     * POST  /incidents : Create a new incident.
     *
     * @param incidentDTO the incidentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new incidentDTO, or with status 400 (Bad Request) if the incident has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/incidents")
    public ResponseEntity<IncidentDTO> createIncident(@Valid @RequestBody IncidentDTO incidentDTO) throws URISyntaxException {
        log.debug("Incident Resource: REST request to save Incident : {}", incidentDTO);
        if (incidentDTO.getId() != null) {
            throw new BadRequestAlertException("Incident Resource: A new incident cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncidentDTO result = incidentService.save(incidentDTO);
        return ResponseEntity.created(new URI("/api/incidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /incidents : Updates an existing incident.
     *
     * @param incidentDTO the incidentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated incidentDTO,
     * or with status 400 (Bad Request) if the incidentDTO is not valid,
     * or with status 500 (Internal Server Error) if the incidentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/incidents")
    public ResponseEntity<IncidentDTO> updateIncident(@Valid @RequestBody IncidentDTO incidentDTO) throws URISyntaxException {
        log.debug("Incident Resource: REST request to update Incident : {}", incidentDTO);
        if (incidentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IncidentDTO result = incidentService.save(incidentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, incidentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /incidents/:id : get the "id" incident.
     *
     * @param id the id of the incidentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the incidentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/incidents/{id}")
    public ResponseEntity<IncidentDTO> getIncident(@PathVariable Long id) {
        log.debug("Incident Resource: REST request to get Incident : {}", id);
        Optional<IncidentDTO> incidentDTO = incidentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incidentDTO);
    }

    // My Custom Code

    /**
     * GET  /incidents/{status}/{severity : get all the incidents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of incidents in body
     */
    @GetMapping("/incidents/{status}/{severity}")
    public ResponseEntity<List<IncidentDTO>> getAllIncidents(Pageable pageable,
                                                             @PathVariable IssueStatus status,
                                                             @PathVariable Severity severity) {
        log.debug("Incident Resource: REST request to get a page of Incidents");
        Page<IncidentDTO> page = incidentService.findAll(pageable, status, severity);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/incidents");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * DELETE  /incidents/:id : delete the "id" incident.
     *
     * @param id the id of the incidentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/incidents/{id}/delete")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        log.debug("Incident Resource: REST request to delete Incident : {}", id);
        incidentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * CLOSE /incidents/:id/close : close the "id" incident.
     *
     * @param id the id of the incidentDTO to close
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/incidents/{id}/close")
    public ResponseEntity<Void> closeIncident(@PathVariable Long id) {
        log.debug("Incident Resource: REST request to close Incident : {}", id);
        incidentService.close(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET Incident for banner display
     */
    @GetMapping("/incidents/incident")
    public ResponseEntity<IncidentDTO> getBannerIncident() {
        log.debug("Incident Resource: REST request to get the incident to display on the banner section");
        IncidentDTO incidentDTO = incidentService.getBannerIncident();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(incidentDTO));
    }

    /**
     * GET Incident/Problem stats for banner display
     */
    @GetMapping("/incidents/stats")
    public BannerStats getBannerStats() {
        log.debug("Incident Resource: REST request to get the incident/problem stats to display on the banner section");
        return incidentService.getBannerStats();
    }
}
