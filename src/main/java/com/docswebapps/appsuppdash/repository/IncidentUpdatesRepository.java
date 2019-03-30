package com.docswebapps.appsuppdash.repository;

import com.docswebapps.appsuppdash.domain.IncidentUpdates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data  repository for the IncidentUpdates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidentUpdatesRepository extends JpaRepository<IncidentUpdates, Long> {
    @Query(value = "select * from incident_updates i where i.in_update_id = :incidentID", nativeQuery = true)
    Page<IncidentUpdates> findIncidentUpdates(@Param("incidentID") Long incidentID, Pageable page);

    @Modifying
    @Transactional
    @Query(value = "delete from incident_updates where in_update_id = :incidentID", nativeQuery = true)
    void deleteIncidentUpdates(@Param("incidentID") Long incidentID);
}
