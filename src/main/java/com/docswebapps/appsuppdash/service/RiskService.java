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
     * Get one risk by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RiskDTO> findOne(Long id) {
        log.debug("RiskService: Request to get Risk : {}", id);
        return riskRepository.findById(id)
            .map(riskMapper::toDto);
    }

    // My Custom Code
    /**
     * Get all the risks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RiskDTO> findAll(Pageable pageable, IssueStatus status, Priority priority) {
      log.debug("RiskService: Request to get all Risks: {} AND {}", status, priority);
      if (status == IssueStatus.ALL && priority == Priority.ALL) {
        return riskRepository.findByOrderByOpenedAtDesc(pageable)
          .map(riskMapper::toDto);
      } else if (status != IssueStatus.ALL && priority != Priority.ALL) {
        return riskRepository.findByRiskStatusAndPriorityOrderByOpenedAtDesc(pageable, status, priority)
          .map(riskMapper::toDto);
      } else if (status == IssueStatus.ALL) {
        return riskRepository.findByPriorityOrderByOpenedAtDesc(pageable, priority)
          .map(riskMapper::toDto);
      } else {
        return riskRepository.findByRiskStatusOrderByOpenedAtDesc(pageable, status)
          .map(riskMapper::toDto);
      }
    }

    /**
     * Delete the risk by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("RiskService: Request to delete Risk : {}", id);
        problemRepository.updateProblems(id);
        riskUpdatesRepository.deleteRiskUpdates(id);
        riskRepository.deleteById(id);
    }
}
