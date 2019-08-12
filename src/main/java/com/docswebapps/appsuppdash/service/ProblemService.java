package com.docswebapps.appsuppdash.service;

import com.docswebapps.appsuppdash.domain.Problem;
import com.docswebapps.appsuppdash.domain.enumeration.IssueStatus;
import com.docswebapps.appsuppdash.domain.enumeration.Priority;
import com.docswebapps.appsuppdash.repository.IncidentRepository;
import com.docswebapps.appsuppdash.repository.ProblemRepository;
import com.docswebapps.appsuppdash.repository.ProblemUpdatesRepository;
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
    private final ProblemUpdatesRepository problemUpdatesRepository;
    private final IncidentRepository incidentRepository;
    private final ProblemMapper problemMapper;

    public ProblemService(ProblemRepository problemRepository,
                          ProblemUpdatesRepository problemUpdatesRepository,
                          IncidentRepository incidentRepository,
                          ProblemMapper problemMapper) {
        this.problemRepository = problemRepository;
        this.problemUpdatesRepository = problemUpdatesRepository;
        this.incidentRepository = incidentRepository;
        this.problemMapper = problemMapper;
    }

    /**
     * Save a problem.
     *
     * @param problemDTO the entity to save
     * @return the persisted entity
     */
    public ProblemDTO save(ProblemDTO problemDTO) {
        log.debug("ProblemService: ProblemService: Request to save Problem : {}", problemDTO);
        Problem problem = problemMapper.toEntity(problemDTO);
        problem = problemRepository.save(problem);
        return problemMapper.toDto(problem);
    }

    // My Custom Code
    /**
     * Get one problem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ProblemDTO findOne(Long id) {
      log.debug("ProblemService: Request to get Problem : {}", id);
      Optional<Problem> tryProblem = problemRepository.findById(id);
      if (tryProblem.isPresent()) {
        Problem problem = tryProblem.get();
        this.setIncidentCountForProblem(problem);
        return problemMapper.toDto(problem);
      } else {
        ProblemDTO problemDTO = new ProblemDTO();
        problemDTO.setTitle("No Problem Exists for Problem ID:" + id);
        return problemDTO;
      }
    }

    /**
     * Get all the problems.
     *
     * @param pageable the pagination information
     * @param status OPEN or CLOSED
     * @param priority ALL, HIGH, MEDIUM or LOW
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProblemDTO> findAll(Pageable pageable, IssueStatus status, Priority priority) {
        log.debug("ProblemService: Request to get all Problems: {} AND {}", status, priority);
        Page<Problem> problems;
        if (status == IssueStatus.ALL && priority == Priority.ALL) {
          problems = problemRepository.findByOrderByOpenedAtDesc(pageable);
        } else if (status != IssueStatus.ALL && priority != Priority.ALL) {
          problems = problemRepository.findByProbStatusAndPriorityOrderByOpenedAtDesc(pageable, status, priority);
        } else if (status == IssueStatus.ALL) {
          problems = problemRepository.findByPriorityOrderByOpenedAtDesc(pageable, priority);
        } else {
          problems = problemRepository.findByProbStatusOrderByOpenedAtDesc(pageable, status);
        }
        this.setIncidentCountForProblems(problems);
        return problems.map(problemMapper::toDto);
    }

    /**
     * Delete the problem by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("ProblemService: Request to delete Problem : {}", id);
        incidentRepository.updateProblems(id);
        problemUpdatesRepository.deleteProblemUpdates(id);
        problemRepository.deleteById(id);
    }

    // Private support methods for obtaining incident count for each problem
    private void setIncidentCountForProblems(Page<Problem> problems) {
      problems.forEach(this::setIncidentCountForProblem);
    }

    private void setIncidentCountForProblem(Problem problem) {
      problem.setIncidentCount(incidentRepository.countByProbRec(problem));
    }
}
