package com.docswebapps.appsuppdash.service.mapper;

import com.docswebapps.appsuppdash.domain.*;
import com.docswebapps.appsuppdash.service.dto.RiskUpdatesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RiskUpdates and its DTO RiskUpdatesDTO.
 */
@Mapper(componentModel = "spring", uses = {RiskMapper.class})
public interface RiskUpdatesMapper extends EntityMapper<RiskUpdatesDTO, RiskUpdates> {

    @Mapping(source = "riskkUpdate.id", target = "riskkUpdateId")
    RiskUpdatesDTO toDto(RiskUpdates riskUpdates);

    @Mapping(source = "riskkUpdateId", target = "riskkUpdate")
    RiskUpdates toEntity(RiskUpdatesDTO riskUpdatesDTO);

    default RiskUpdates fromId(Long id) {
        if (id == null) {
            return null;
        }
        RiskUpdates riskUpdates = new RiskUpdates();
        riskUpdates.setId(id);
        return riskUpdates;
    }
}
