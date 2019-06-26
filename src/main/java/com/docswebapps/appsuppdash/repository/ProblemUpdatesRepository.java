package com.docswebapps.appsuppdash.repository;

import com.docswebapps.appsuppdash.domain.ProblemUpdates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data  repository for the ProblemUpdates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProblemUpdatesRepository extends JpaRepository<ProblemUpdates, Long> {
    @Query(value = "select * from problem_updates p where p.prob_update_id = :problemID order by updated_at desc", nativeQuery = true)
    Page<ProblemUpdates> findProblemUpdates(@Param("problemID") Long problemID, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "delete from problem_updates where prob_update_id = :problemID", nativeQuery = true)
    void deleteProblemUpdates(@Param("problemID") Long problemID);
}
