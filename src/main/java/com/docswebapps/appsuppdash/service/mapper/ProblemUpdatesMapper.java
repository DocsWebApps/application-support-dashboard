package com.docswebapps.appsuppdash.service.mapper;

import com.docswebapps.appsuppdash.domain.*;
import com.docswebapps.appsuppdash.service.dto.ProblemUpdatesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProblemUpdates and its DTO ProblemUpdatesDTO.
 */
@Mapper(componentModel = "spring", uses = {ProblemMapper.class})
public interface ProblemUpdatesMapper extends EntityMapper<ProblemUpdatesDTO, ProblemUpdates> {

    @Mapping(source = "probUpdate.id", target = "probUpdateId")
    ProblemUpdatesDTO toDto(ProblemUpdates problemUpdates);

    @Mapping(source = "probUpdateId", target = "probUpdate")
    ProblemUpdates toEntity(ProblemUpdatesDTO problemUpdatesDTO);

    default ProblemUpdates fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProblemUpdates problemUpdates = new ProblemUpdates();
        problemUpdates.setId(id);
        return problemUpdates;
    }
}
