package com.docswebapps.appsuppdash.service;

import com.docswebapps.appsuppdash.domain.Problem;
import com.docswebapps.appsuppdash.repository.ProblemRepository;
import com.docswebapps.appsuppdash.service.dto.ProblemDTO;
import com.docswebapps.appsuppdash.service.mapper.ProblemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Problem.
 */
@Service
@Transactional
public class ProblemService {

    private final Logger log = LoggerFactory.getLogger(ProblemService.class);

    private final ProblemRepository problemRepository;

    private final ProblemMapper problemMapper;

    public ProblemService(ProblemRepository problemRepository, ProblemMapper problemMapper) {
        this.problemRepository = problemRepository;
        this.problemMapper = problemMapper;
    }

    /**
     * Save a problem.
     *
     * @param problemDTO the entity to save
     * @return the persisted entity
     */
    public ProblemDTO save(ProblemDTO problemDTO) {
        log.debug("Request to save Problem : {}", problemDTO);
        Problem problem = problemMapper.toEntity(problemDTO);
        problem = problemRepository.save(problem);
        return problemMapper.toDto(problem);
    }

    /**
     * Get all the problems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProblemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Problems");
        return problemRepository.findAll(pageable)
            .map(problemMapper::toDto);
    }


    /**
     * Get one problem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProblemDTO> findOne(Long id) {
        log.debug("Request to get Problem : {}", id);
        return problemRepository.findById(id)
            .map(problemMapper::toDto);
    }

    /**
     * Delete the problem by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Problem : {}", id);
        problemRepository.deleteById(id);
    }
}
