package com.docswebapps.appsuppdash.repository;

import com.docswebapps.appsuppdash.domain.Incident;
import com.docswebapps.appsuppdash.domain.Problem;
import com.docswebapps.appsuppdash.domain.enumeration.IssueStatus;
import com.docswebapps.appsuppdash.domain.enumeration.Severity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data  repository for the Incident entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    Long countBySeverityAndIncidentStatus(Severity severity, IssueStatus incidentStatus);
    Page<Incident> findByOrderByIncidentStatusDescOpenedAtDesc(Pageable page);
    Page<Incident> findByIncidentStatusOrderByOpenedAtDesc(Pageable page, IssueStatus incidentStatus);
    Page<Incident> findBySeverityOrderByOpenedAtDesc(Pageable page, Severity severity);
    Page<Incident> findByIncidentStatusAndSeverityOrderByOpenedAtDesc(Pageable page, IssueStatus incidentStatus, Severity severity);
    Page<Incident> findByProbRecOrderByOpenedAtDesc(Pageable page, Problem problem);
    Incident findFirstBySeverityOrderByIdDesc(Severity severity);
    Incident findFirstBySeverityAndIncidentStatusOrderByIdDesc(Severity severity, IssueStatus incidentStatus);

    @Modifying
    @Transactional
    @Query(value = "update incident i set i.prob_rec_id=null where i.prob_rec_id= :problemID", nativeQuery = true)
    void updateProblems(@Param("problemID") Long problemID);
}
