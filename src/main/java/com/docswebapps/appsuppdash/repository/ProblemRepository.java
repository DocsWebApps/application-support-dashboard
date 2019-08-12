package com.docswebapps.appsuppdash.repository;

import com.docswebapps.appsuppdash.domain.Problem;
import com.docswebapps.appsuppdash.domain.Risk;
import com.docswebapps.appsuppdash.domain.enumeration.IssueStatus;
import com.docswebapps.appsuppdash.domain.enumeration.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/**
 * Spring Data  repository for the Problem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    Long countByProbStatus(IssueStatus probStatus);
    Long countByRiskRec(Risk risk);
    Page<Problem> findByOrderByOpenedAtDesc(Pageable page);
    Page<Problem> findByProbStatusOrderByOpenedAtDesc(Pageable page, IssueStatus probStatus);
    Page<Problem> findByPriorityOrderByOpenedAtDesc(Pageable page, Priority priority);
    Page<Problem> findByRiskRecOrderByOpenedAtDesc(Pageable page, Risk risk);
    Page<Problem> findByProbStatusAndPriorityOrderByOpenedAtDesc(Pageable page, IssueStatus probStatus, Priority priority);

    @Modifying
    @Transactional
    @Query(value = "update problem p set p.risk_rec_id=null where p.risk_rec_id =  :riskID", nativeQuery = true)
    void updateProblems(@Param("riskID") Long riskID);
}
