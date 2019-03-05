package com.docswebapps.appsuppdash.service;

import com.docswebapps.appsuppdash.domain.Risk;
import com.docswebapps.appsuppdash.domain.enumeration.IssueStatus;
import com.docswebapps.appsuppdash.domain.enumeration.Priority;
import com.docswebapps.appsuppdash.repository.ProblemRepository;
import com.docswebapps.appsuppdash.repository.RiskRepository;
import com.docswebapps.appsuppdash.repository.RiskUpdatesRepository;
import com.docswebapps.appsuppdash.service.dto.RiskDTO;
import com.docswebapps.appsuppdash.service.mapper.RiskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Risk.
 */
@Service
@Transactional
public class RiskService {
    private final Logger log = LoggerFactory.getLogger(RiskService.class);
    private final RiskRepository riskRepository;
    private final RiskUpdatesRepository riskUpdatesRepository;
    private final ProblemRepository problemRepository;
    private final RiskMapper riskMapper;

    public RiskService(RiskRepository riskRepository,
                       RiskUpdatesRepository riskUpdatesRepository,
                       ProblemRepository problemRepository,
                       RiskMapper riskMapper) {
        this.riskRepository = riskRepository;
        this.riskUpdatesRepository = riskUpdatesRepository;
        this.problemRepository = problemRepository;
        this.riskMapper = riskMapper;
    }

    /**
     * Save a risk.
     *
     * @param riskDTO the entity to save
     * @return the persisted entity
     */
    public RiskDTO save(RiskDTO riskDTO) {
        log.debug("RiskService: Request to save Risk : {}", riskDTO);
        Risk risk = riskMapper.toEntity(riskDTO);
        risk = riskRepository.save(risk);
        return riskMapper.toDto(risk);
    }

    /**
     * Get all the risks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RiskDTO> findAll(Pageable pageable) {
        log.debug("RiskService: Request to get all Risks");
        return riskRepository.findByOrderByOpenedAtDesc(pageable)
            .map(riskMapper::toDto);
    }


    /**
     * Get one risk by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RiskDTO> findOne(Long id) {
        log.debug("Request to get Risk : {}", id);
        return riskRepository.findById(id)
            .map(riskMapper::toDto);
    }

    /**
     * Delete the risk by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Risk : {}", id);
        problemRepository.updateProblems(id);
        riskUpdatesRepository.deleteRiskUpdates(id);
        riskRepository.deleteById(id);
    }

    // My Custom Code - ***** DEPRECATE CODE BELOW USING GET ALL ***
    /**
     * Find with Status and Priority
     * @param status: Open or Closed
     * @param priority: High, Medium or Low
     * @return Page of RiskDTOs
     */
    @Transactional(readOnly = true)
    public Page<RiskDTO> findWithStatusAndPriority(Pageable pageable, IssueStatus status, Priority priority) {
        log.debug("RiskService: Find All Risks with Status: {} and Priority: {} ", status, priority);
        return riskRepository.findByRiskStatusAndPriorityOrderByOpenedAtDesc(pageable, status, priority)
            .map(riskMapper::toDto);
    }

    /**
     * Find with Status
     * @param status: Open or Closed
     * @return Page of RiskDTOs
     */
    public Page<RiskDTO> findWithStatus(Pageable pageable, IssueStatus status) {
        log.debug("RiskService: Find All Risks with Status: {}", status);
        return riskRepository.findByRiskStatusOrderByOpenedAtDesc(pageable, status)
            .map(riskMapper::toDto);
    }

    /**
     * Find with Priority
     * @param priority: High, Medium, Low
     * @return Page of RiskDTOs
     */
    public Page<RiskDTO> findWithPriority(Pageable pageable, Priority priority) {
        log.debug("RiskService: Find All Risks with Priority: {}",priority);
        return riskRepository.findByPriorityOrderByOpenedAtDesc(pageable, priority)
            .map(riskMapper::toDto);
    }
}
