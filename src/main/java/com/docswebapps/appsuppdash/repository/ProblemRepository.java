package com.docswebapps.appsuppdash.repository;

import com.docswebapps.appsuppdash.domain.Problem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Problem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

}
