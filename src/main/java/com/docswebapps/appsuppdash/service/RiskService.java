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

    // My Custom Code
  /**
   * Get one risk by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
    @Transactional(readOnly = true)
    public RiskDTO findOne(Long id) {
      log.debug("RiskService: Request to get Risk : {}", id);
      Optional<Risk> tryRisk = riskRepository.findById(id);
      if (tryRisk.isPresent()) {
        Risk risk = tryRisk.get();
        this.setProblemCountForRisk(risk);
        return riskMapper.toDto(risk);
      } else {
        RiskDTO riskDTO = new RiskDTO();
        riskDTO.setTitle("No Risk Exists for Risk ID:" + id);
        return riskDTO;
      }
    }

    /**
     * Get all the risks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RiskDTO> findAll(Pageable pageable, IssueStatus status, Priority priority) {
      log.debug("RiskService: Request to get all Risks: {} AND {}", status, priority);
      Page<Risk> risks;
      if (status == IssueStatus.ALL && priority == Priority.ALL) {
        risks = riskRepository.findByOrderByOpenedAtDesc(pageable);
      } else if (status != IssueStatus.ALL && priority != Priority.ALL) {
        risks = riskRepository.findByRiskStatusAndPriorityOrderByOpenedAtDesc(pageable, status, priority);
      } else if (status == IssueStatus.ALL) {
        risks = riskRepository.findByPriorityOrderByOpenedAtDesc(pageable, priority);
      } else {
        risks = riskRepository.findByRiskStatusOrderByOpenedAtDesc(pageable, status);
      }
      this.setProblemCountForRisks(risks);
      return risks.map(riskMapper::toDto);
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

    // Private support methods for obtaining problem count for each risk
    private void setProblemCountForRisks(Page<Risk> risks) {
      risks.forEach(this::setProblemCountForRisk);
    }

    private void setProblemCountForRisk(Risk risk) {
      risk.setProblemCount(problemRepository.countByRiskRec(risk));
    }
}
