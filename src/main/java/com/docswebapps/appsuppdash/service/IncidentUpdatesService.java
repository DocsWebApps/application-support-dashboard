package com.docswebapps.appsuppdash.service;

import com.docswebapps.appsuppdash.domain.IncidentUpdates;
import com.docswebapps.appsuppdash.repository.IncidentUpdatesRepository;
import com.docswebapps.appsuppdash.service.dto.IncidentUpdatesDTO;
import com.docswebapps.appsuppdash.service.mapper.IncidentUpdatesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing IncidentUpdates.
 */
@Service
@Transactional
public class IncidentUpdatesService {

    private final Logger log = LoggerFactory.getLogger(IncidentUpdatesService.class);

    private final IncidentUpdatesRepository incidentUpdatesRepository;

    private final IncidentUpdatesMapper incidentUpdatesMapper;

    public IncidentUpdatesService(IncidentUpdatesRepository incidentUpdatesRepository, IncidentUpdatesMapper incidentUpdatesMapper) {
        this.incidentUpdatesRepository = incidentUpdatesRepository;
        this.incidentUpdatesMapper = incidentUpdatesMapper;
    }

    /**
     * Save a incidentUpdates.
     *
     * @param incidentUpdatesDTO the entity to save
     * @return the persisted entity
     */
    public IncidentUpdatesDTO save(IncidentUpdatesDTO incidentUpdatesDTO) {
        log.debug("Request to save IncidentUpdates : {}", incidentUpdatesDTO);
        IncidentUpdates incidentUpdates = incidentUpdatesMapper.toEntity(incidentUpdatesDTO);
        incidentUpdates = incidentUpdatesRepository.save(incidentUpdates);
        return incidentUpdatesMapper.toDto(incidentUpdates);
    }

    /**
     * Get all the incidentUpdates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IncidentUpdatesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IncidentUpdates");
        return incidentUpdatesRepository.findAll(pageable)
            .map(incidentUpdatesMapper::toDto);
    }


    /**
     * Get one incidentUpdates by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<IncidentUpdatesDTO> findOne(Long id) {
        log.debug("Request to get IncidentUpdates : {}", id);
        return incidentUpdatesRepository.findById(id)
            .map(incidentUpdatesMapper::toDto);
    }

    /**
     * Delete the incidentUpdates by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IncidentUpdates : {}", id);
        incidentUpdatesRepository.deleteById(id);
    }
}
