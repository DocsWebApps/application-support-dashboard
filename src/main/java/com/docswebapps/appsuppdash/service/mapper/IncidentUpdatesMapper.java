package com.docswebapps.appsuppdash.service.mapper;

import com.docswebapps.appsuppdash.domain.*;
import com.docswebapps.appsuppdash.service.dto.IncidentUpdatesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity IncidentUpdates and its DTO IncidentUpdatesDTO.
 */
@Mapper(componentModel = "spring", uses = {IncidentMapper.class})
public interface IncidentUpdatesMapper extends EntityMapper<IncidentUpdatesDTO, IncidentUpdates> {

    @Mapping(source = "inUpdate.id", target = "inUpdateId")
    IncidentUpdatesDTO toDto(IncidentUpdates incidentUpdates);

    @Mapping(source = "inUpdateId", target = "inUpdate")
    IncidentUpdates toEntity(IncidentUpdatesDTO incidentUpdatesDTO);

    default IncidentUpdates fromId(Long id) {
        if (id == null) {
            return null;
        }
        IncidentUpdates incidentUpdates = new IncidentUpdates();
        incidentUpdates.setId(id);
        return incidentUpdates;
    }
}
