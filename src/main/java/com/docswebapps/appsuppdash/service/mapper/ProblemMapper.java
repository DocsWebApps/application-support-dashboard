package com.docswebapps.appsuppdash.service.mapper;

import com.docswebapps.appsuppdash.domain.*;
import com.docswebapps.appsuppdash.service.dto.ProblemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Problem and its DTO ProblemDTO.
 */
@Mapper(componentModel = "spring", uses = {RiskMapper.class})
public interface ProblemMapper extends EntityMapper<ProblemDTO, Problem> {

    @Mapping(source = "riskRec.id", target = "riskRecId")
    ProblemDTO toDto(Problem problem);

    @Mapping(target = "incidents", ignore = true)
    @Mapping(target = "problemUpdates", ignore = true)
    @Mapping(source = "riskRecId", target = "riskRec")
    Problem toEntity(ProblemDTO problemDTO);

    default Problem fromId(Long id) {
        if (id == null) {
            return null;
        }
        Problem problem = new Problem();
        problem.setId(id);
        return problem;
    }
}
