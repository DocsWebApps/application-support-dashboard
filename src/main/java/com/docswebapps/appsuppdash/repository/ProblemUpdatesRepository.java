package com.docswebapps.appsuppdash.repository;

import com.docswebapps.appsuppdash.domain.ProblemUpdates;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProblemUpdates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProblemUpdatesRepository extends JpaRepository<ProblemUpdates, Long> {

}
