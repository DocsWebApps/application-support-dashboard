package com.docswebapps.appsuppdash.service.mapper;

import com.docswebapps.appsuppdash.domain.*;
import com.docswebapps.appsuppdash.service.dto.AppDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity App and its DTO AppDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AppMapper extends EntityMapper<AppDTO, App> {



    default App fromId(Long id) {
        if (id == null) {
            return null;
        }
        App app = new App();
        app.setId(id);
        return app;
    }
}
