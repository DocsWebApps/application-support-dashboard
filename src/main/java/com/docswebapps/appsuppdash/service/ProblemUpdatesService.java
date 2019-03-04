package com.docswebapps.appsuppdash.service;

import com.docswebapps.appsuppdash.domain.ProblemUpdates;
import com.docswebapps.appsuppdash.repository.ProblemUpdatesRepository;
import com.docswebapps.appsuppdash.service.dto.ProblemUpdatesDTO;
import com.docswebapps.appsuppdash.service.mapper.ProblemUpdatesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ProblemUpdates.
 */
@Service
@Transactional
public class ProblemUpdatesService {

    private final Logger log = LoggerFactory.getLogger(ProblemUpdatesService.class);

    private final ProblemUpdatesRepository problemUpdatesRepository;

    private final ProblemUpdatesMapper problemUpdatesMapper;

    public ProblemUpdatesService(ProblemUpdatesRepository problemUpdatesRepository, ProblemUpdatesMapper problemUpdatesMapper) {
        this.problemUpdatesRepository = problemUpdatesRepository;
        this.problemUpdatesMapper = problemUpdatesMapper;
    }

    /**
     * Save a problemUpdates.
     *
     * @param problemUpdatesDTO the entity to save
     * @return the persisted entity
     */
    public ProblemUpdatesDTO save(ProblemUpdatesDTO problemUpdatesDTO) {
        log.debug("Request to save ProblemUpdates : {}", problemUpdatesDTO);
        ProblemUpdates problemUpdates = problemUpdatesMapper.toEntity(problemUpdatesDTO);
        problemUpdates = problemUpdatesRepository.save(problemUpdates);
        return problemUpdatesMapper.toDto(problemUpdates);
    }

    /**
     * Get all the problemUpdates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProblemUpdatesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProblemUpdates");
        return problemUpdatesRepository.findAll(pageable)
            .map(problemUpdatesMapper::toDto);
    }


    /**
     * Get one problemUpdates by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProblemUpdatesDTO> findOne(Long id) {
        log.debug("Request to get ProblemUpdates : {}", id);
        return problemUpdatesRepository.findById(id)
            .map(problemUpdatesMapper::toDto);
    }

    /**
     * Delete the problemUpdates by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProblemUpdates : {}", id);
        problemUpdatesRepository.deleteById(id);
    }
}
