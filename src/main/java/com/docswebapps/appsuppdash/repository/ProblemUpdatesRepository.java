package com.docswebapps.appsuppdash.repository;

import com.docswebapps.appsuppdash.domain.ProblemUpdates;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


/**
 * Spring Data  repository for the ProblemUpdates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProblemUpdatesRepository extends JpaRepository<ProblemUpdates, Long> {
    @Query(value = "select * from problem_updates p where p.prob_update_id = :problemID", nativeQuery = true)
    List<ProblemUpdates> findProblemUpdates(@Param("problemID") Long problemID);

    @Modifying
    @Transactional
    @Query(value = "delete from problem_updates p where p.prob_update_id = :problemID", nativeQuery = true)
    void deleteProblemUpdates(@Param("problemID") Long problemID);
}
