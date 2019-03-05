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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing IncidentUpdates.
 */
@Service
@Transactional
public class IncidentUpdatesService {
    private final Logger log = LoggerFactory.getLogger(IncidentUpdatesService.class);
    private final IncidentUpdatesRepository incidentUpdatesRepository;
    private final IncidentUpdatesMapper incidentUpdatesMapper;

    public IncidentUpdatesService(IncidentUpdatesRepository incidentUpdatesRepository,
                                 IncidentUpdatesMapper incidentUpdatesMapper) {
        this.incidentUpdatesRepository = incidentUpdatesRepository;
        this.incidentUpdatesMapper = incidentUpdatesMapper;
    }

    /**
     * Save a incidentUpdate.
     *
     * @param incidentUpdatesDTO the entity to save
     * @return the persisted entity
     */
    public IncidentUpdatesDTO save(IncidentUpdatesDTO incidentUpdatesDTO) {
        log.debug("IncidentUpdatesService: Request to save IncidentUpdate : {}", incidentUpdatesDTO);
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
        log.debug("IncidentUpdatesService: Request to get all IncidentUpdates");
        return incidentUpdatesRepository.findAll(pageable)
            .map(incidentUpdatesMapper::toDto);
    }


    /**
     * Get one incidentUpdate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<IncidentUpdatesDTO> findOne(Long id) {
        log.debug("IncidentUpdatesService: Request to get IncidentUpdate : {}", id);
        return incidentUpdatesRepository.findById(id)
            .map(incidentUpdatesMapper::toDto);
    }

    /**
     * Delete the incidentUpdate by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("IncidentUpdatesService: Request to delete IncidentUpdate : {}", id);
        incidentUpdatesRepository.deleteById(id);
    }

    // My Custom Code
    /**
     * @param id of Incident
     * @return IncidentUpdatesDTO's
     */
    public List<IncidentUpdatesDTO> findIncidentUpdates(Long id) {
        log.debug("IncidentUpdatesService: Request to get all IncidentUpdates", id);
        return incidentUpdatesRepository.findIncidentUpdates(id).stream()
            .map(incidentUpdatesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
