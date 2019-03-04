package com.docswebapps.appsuppdash.service;

import com.docswebapps.appsuppdash.domain.Incident;
import com.docswebapps.appsuppdash.repository.IncidentRepository;
import com.docswebapps.appsuppdash.service.dto.IncidentDTO;
import com.docswebapps.appsuppdash.service.mapper.IncidentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Incident.
 */
@Service
@Transactional
public class IncidentService {

    private final Logger log = LoggerFactory.getLogger(IncidentService.class);

    private final IncidentRepository incidentRepository;

    private final IncidentMapper incidentMapper;

    public IncidentService(IncidentRepository incidentRepository, IncidentMapper incidentMapper) {
        this.incidentRepository = incidentRepository;
        this.incidentMapper = incidentMapper;
    }

    /**
     * Save a incident.
     *
     * @param incidentDTO the entity to save
     * @return the persisted entity
     */
    public IncidentDTO save(IncidentDTO incidentDTO) {
        log.debug("Request to save Incident : {}", incidentDTO);
        Incident incident = incidentMapper.toEntity(incidentDTO);
        incident = incidentRepository.save(incident);
        return incidentMapper.toDto(incident);
    }

    /**
     * Get all the incidents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IncidentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Incidents");
        return incidentRepository.findAll(pageable)
            .map(incidentMapper::toDto);
    }


    /**
     * Get one incident by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<IncidentDTO> findOne(Long id) {
        log.debug("Request to get Incident : {}", id);
        return incidentRepository.findById(id)
            .map(incidentMapper::toDto);
    }

    /**
     * Delete the incident by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Incident : {}", id);
        incidentRepository.deleteById(id);
    }
}
