package com.docswebapps.appsuppdash.repository;

import com.docswebapps.appsuppdash.domain.RiskUpdates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data  repository for the RiskUpdates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskUpdatesRepository extends JpaRepository<RiskUpdates, Long> {
    @Query(value = "select * from risk_updates r where r.riskk_update_id = :riskID order by updated_at desc, id desc", nativeQuery = true)
    Page<RiskUpdates> findRiskUpdates(@Param("riskID") Long riskID, Pageable page);

    @Modifying
    @Transactional
    @Query(value = "delete from risk_updates where riskk_update_id = :riskID", nativeQuery = true)
    void deleteRiskUpdates(@Param("riskID") Long riskID);
}
