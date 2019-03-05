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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProblemUpdates.
 */
@Service
@Transactional
public class ProblemUpdatesService {

    private final Logger log = LoggerFactory.getLogger(ProblemUpdatesService.class);
    private final ProblemUpdatesRepository problemUpdatesRepository;
    private final ProblemUpdatesMapper problemUpdatesMapper;

    public ProblemUpdatesService(ProblemUpdatesRepository problemUpdatesRepository,
                                 ProblemUpdatesMapper problemUpdatesMapper) {
        this.problemUpdatesRepository = problemUpdatesRepository;
        this.problemUpdatesMapper = problemUpdatesMapper;
    }

    /**
     * Save a problemUpdate.
     *
     * @param problemUpdatesDTO the entity to save
     * @return the persisted entity
     */
    public ProblemUpdatesDTO save(ProblemUpdatesDTO problemUpdatesDTO) {
        log.debug("ProblemUpdatesService: Request to save ProblemUpdate : {}", problemUpdatesDTO);
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
     * Get one problemUpdate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProblemUpdatesDTO> findOne(Long id) {
        log.debug("ProblemUpdatesService: Request to get ProblemUpdate : {}", id);
        return problemUpdatesRepository.findById(id)
            .map(problemUpdatesMapper::toDto);
    }

    /**
     * Delete the problemUpdate by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("ProblemUpdatesService: Request to delete ProblemUpdate : {}", id);
        problemUpdatesRepository.deleteById(id);
    }

    // My Custom Code
    /**
     * Get all the problemUpdates for a particular problem
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ProblemUpdatesDTO> findProblemUpdates(Long id) {
        log.debug("ProblemUpdatesService: Request to get all ProblemUpdates", id);
        return problemUpdatesRepository.findProblemUpdates(id).stream()
            .map(problemUpdatesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
