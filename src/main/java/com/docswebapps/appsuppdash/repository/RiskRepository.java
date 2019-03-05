package com.docswebapps.appsuppdash.repository;

import com.docswebapps.appsuppdash.domain.Risk;
import com.docswebapps.appsuppdash.domain.enumeration.IssueStatus;
import com.docswebapps.appsuppdash.domain.enumeration.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Risk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskRepository extends JpaRepository<Risk, Long> {
    Long countByRiskStatus(IssueStatus riskStatus);
    Page<Risk> findByOrderByOpenedAtDesc(Pageable pageable);
    Page<Risk> findByRiskStatusOrderByOpenedAtDesc(Pageable page, IssueStatus riskStatus);
    Page<Risk> findByPriorityOrderByOpenedAtDesc(Pageable page, Priority priority);
    Page<Risk> findByRiskStatusAndPriorityOrderByOpenedAtDesc(Pageable page, IssueStatus riskStatus, Priority priority);
}
