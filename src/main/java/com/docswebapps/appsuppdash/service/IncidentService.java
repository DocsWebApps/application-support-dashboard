package com.docswebapps.appsuppdash.service;

import com.docswebapps.appsuppdash.domain.BannerStats;
import com.docswebapps.appsuppdash.domain.Incident;
import com.docswebapps.appsuppdash.domain.enumeration.IssueStatus;
import com.docswebapps.appsuppdash.domain.enumeration.Severity;
import com.docswebapps.appsuppdash.domain.enumeration.SystemStatus;
import com.docswebapps.appsuppdash.repository.IncidentRepository;
import com.docswebapps.appsuppdash.repository.IncidentUpdatesRepository;
import com.docswebapps.appsuppdash.repository.ProblemRepository;
import com.docswebapps.appsuppdash.repository.RiskRepository;
import com.docswebapps.appsuppdash.service.dto.IncidentDTO;
import com.docswebapps.appsuppdash.service.mapper.IncidentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Service Implementation for managing Incident.
 */
@Service
@Transactional
public class IncidentService {

    private final Logger log = LoggerFactory.getLogger(IncidentService.class);
    private final IncidentRepository incidentRepository;
    private final ProblemRepository problemRepository;
    private final RiskRepository riskRepository;
    private final IncidentUpdatesRepository incidentUpdatesRepository;
    private final AppService appService;
    private final IncidentMapper incidentMapper;

    public IncidentService(IncidentRepository incidentRepository,
                           IncidentMapper incidentMapper,
                           AppService appService,
                           ProblemRepository problemRepository,
                           RiskRepository riskRepository,
                           IncidentUpdatesRepository incidentUpdatesRepository)
    {
        this.incidentRepository = incidentRepository;
        this.problemRepository = problemRepository;
        this.riskRepository = riskRepository;
        this.incidentUpdatesRepository = incidentUpdatesRepository;
        this.appService = appService;
        this.incidentMapper = incidentMapper;
    }

    /**
     * Save a incident.
     *
     * @param incidentDTO the entity to save
     * @return the persisted entity
     */
    public IncidentDTO save(IncidentDTO incidentDTO) {
        log.debug("IncidentService: Request to save Incident : {}", incidentDTO);
        Incident incident = incidentMapper.toEntity(incidentDTO);
        incident = incidentRepository.save(incident);
        this.setAppSysStatus();
        return incidentMapper.toDto(incident);
    }

    /**
     * Get one incident by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<IncidentDTO> findOne(Long id) {
        log.debug("IncidentService: Request to get Incident : {}", id);
        return incidentRepository.findById(id)
            .map(incidentMapper::toDto);
    }

    // My Custom Code
    /**
     * Get all the incidents.
     *
     * @param pageable the pagination information
     * @param status - All, OPEN, CLOSED
     * @param severity - ALL, P1, P2, P3, P4
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IncidentDTO> findAll(Pageable pageable, IssueStatus status, Severity severity) {
        log.debug("IncidentService: Request to get all Incidents: {} AND {}", status, severity);
        if (status == IssueStatus.ALL && severity == Severity.ALL) {
            return incidentRepository.findByOrderByIncidentStatusDescOpenedAtDesc(pageable)
                .map(incidentMapper::toDto);
        } else if (status != IssueStatus.ALL && severity != Severity.ALL) {
            return incidentRepository.findByIncidentStatusAndSeverityOrderByOpenedAtDesc(pageable, status, severity)
                .map(incidentMapper::toDto);
        } else if (status == IssueStatus.ALL) {
            return incidentRepository.findBySeverityOrderByOpenedAtDesc(pageable, severity)
                .map(incidentMapper::toDto);
        } else {
            return incidentRepository.findByIncidentStatusOrderByOpenedAtDesc(pageable, status)
                .map(incidentMapper::toDto);
        }
    }

    /**
     * Delete the incident by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("IncidentService: Request to delete Incident : {}", id);
        incidentUpdatesRepository.deleteIncidentUpdates(id);
        incidentRepository.deleteById(id);
        this.setAppSysStatus();
    }

    /**
     * Close the incident by id.
     *
     * @param id the id of the entity
     */
    public void close(Long id) {
        log.debug("IncidentService: Request to close Incident : {}", id);
        Optional<Incident> tryIncident = incidentRepository.findById(id);
        if (tryIncident.isPresent()) {
            Incident incident = tryIncident.get();
            Enum severity = incident.getSeverity();
            if (incident.getClosedAt() == null) {
                incident.setClosedAt(LocalDate.now());
            }
            incident.setIncidentStatus(IssueStatus.CLOSED);
            incidentRepository.save(incident);
            if (severity == Severity.P1 || severity == Severity.P2) {
                this.appService.updateApp(incident.getClosedAt());
            }
            this.setAppSysStatus();
        }
    }

    /**
     * GET Banner Incident - List the highest priority incident
     */
    @Transactional(readOnly = true)
    public IncidentDTO getBannerIncident() {
        log.debug("IncidentService: Get Incident Stats for the Front Page");
        Long p1Count=this.incidentRepository.countBySeverityAndIncidentStatus(Severity.P1, IssueStatus.OPEN);
        Long p2Count=this.incidentRepository.countBySeverityAndIncidentStatus(Severity.P2, IssueStatus.OPEN);
        Incident incident = new Incident();

        if (p1Count > 0) {
            incident = incidentRepository.findFirstBySeverityOrderByIdDesc(Severity.P1);
        } else if (p2Count > 0) {
            incident = incidentRepository.findFirstBySeverityOrderByIdDesc(Severity.P2);
        } else {
            incident.setDescription("No open incidents!!");
        }
        return incidentMapper.toDto(incident);
    }

    /**
     * GET Banner Stats - Open P3,P4 and defect count
     */
    @Transactional(readOnly = true)
    public BannerStats getBannerStats() {
        log.debug("IncidentService: Get P3/P4 and Open Risk/Problem Counts for the Front Page");
        BannerStats bannerStats = new BannerStats();
        bannerStats.setP3Count(this.incidentRepository.countBySeverityAndIncidentStatus(Severity.P3, IssueStatus.OPEN));
        bannerStats.setP4Count(this.incidentRepository.countBySeverityAndIncidentStatus(Severity.P4, IssueStatus.OPEN));
        bannerStats.setProblemCount(problemRepository.countByProbStatus(IssueStatus.OPEN));
        bannerStats.setRiskCount(riskRepository.countByRiskStatus(IssueStatus.OPEN));
        return bannerStats;
    }

//    /**
//     * Check for Open P1's or P2's
//     */
//    @Transactional(readOnly = true)
//    public boolean checkOpenP1P2Incidents(IncidentDTO incidentDTO) {
//        log.debug("IncidentService: Check for an Open P1 or P2");
//        Long incidentCount = incidentRepository.countBySeverityAndIncidentStatus(incidentDTO.getSeverity(), IssueStatus.OPEN);
//        if (incidentCount == 0) {
//            return false;
//        } else if (incidentDTO.getId() == null) {
//            return true;
//        }
//        else {
//            Incident incident = incidentRepository.findFirstBySeverityAndIncidentStatusOrderByIdDesc(incidentDTO.getSeverity(), IssueStatus.OPEN);
//            return !incidentDTO.getId().equals(incident.getId());
//        }
//    }

    /**
     * SET App Status
     */
    private void setAppSysStatus() {
        log.debug("IncidentService: Refresh Application Status");
        Long p1_count = incidentRepository.countBySeverityAndIncidentStatus(Severity.P1, IssueStatus.OPEN);
        Long p2_count = incidentRepository.countBySeverityAndIncidentStatus(Severity.P2, IssueStatus.OPEN);

        if (p1_count > 0) {
            appService.setAppSysStatus(SystemStatus.RED);
        } else if (p2_count > 0) {
            appService.setAppSysStatus(SystemStatus.AMBER);
        } else {
            appService.setAppSysStatus(SystemStatus.GREEN);
        }
    }
}
