package com.docswebapps.appsuppdash.repository;

import com.docswebapps.appsuppdash.domain.RiskUpdates;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RiskUpdates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskUpdatesRepository extends JpaRepository<RiskUpdates, Long> {

}
