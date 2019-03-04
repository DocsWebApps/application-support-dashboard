package com.docswebapps.appsuppdash.service.mapper;

import com.docswebapps.appsuppdash.domain.*;
import com.docswebapps.appsuppdash.service.dto.IncidentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Incident and its DTO IncidentDTO.
 */
@Mapper(componentModel = "spring", uses = {ProblemMapper.class})
public interface IncidentMapper extends EntityMapper<IncidentDTO, Incident> {

    @Mapping(source = "probRec.id", target = "probRecId")
    IncidentDTO toDto(Incident incident);

    @Mapping(target = "incidentUpdates", ignore = true)
    @Mapping(source = "probRecId", target = "probRec")
    Incident toEntity(IncidentDTO incidentDTO);

    default Incident fromId(Long id) {
        if (id == null) {
            return null;
        }
        Incident incident = new Incident();
        incident.setId(id);
        return incident;
    }
}
