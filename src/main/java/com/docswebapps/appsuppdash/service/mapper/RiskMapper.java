package com.docswebapps.appsuppdash.service.mapper;

import com.docswebapps.appsuppdash.domain.*;
import com.docswebapps.appsuppdash.service.dto.RiskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Risk and its DTO RiskDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RiskMapper extends EntityMapper<RiskDTO, Risk> {


    @Mapping(target = "problems", ignore = true)
    @Mapping(target = "riskUpdates", ignore = true)
    Risk toEntity(RiskDTO riskDTO);

    default Risk fromId(Long id) {
        if (id == null) {
            return null;
        }
        Risk risk = new Risk();
        risk.setId(id);
        return risk;
    }
}
