package com.docswebapps.appsuppdash.repository;

import com.docswebapps.appsuppdash.domain.IncidentUpdates;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IncidentUpdates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidentUpdatesRepository extends JpaRepository<IncidentUpdates, Long> {

}
