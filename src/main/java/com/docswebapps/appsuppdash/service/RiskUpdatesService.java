package com.docswebapps.appsuppdash.service;

import com.docswebapps.appsuppdash.domain.RiskUpdates;
import com.docswebapps.appsuppdash.repository.RiskUpdatesRepository;
import com.docswebapps.appsuppdash.service.dto.RiskUpdatesDTO;
import com.docswebapps.appsuppdash.service.mapper.RiskUpdatesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RiskUpdates.
 */
@Service
@Transactional
public class RiskUpdatesService {

    private final Logger log = LoggerFactory.getLogger(RiskUpdatesService.class);

    private final RiskUpdatesRepository riskUpdatesRepository;

    private final RiskUpdatesMapper riskUpdatesMapper;

    public RiskUpdatesService(RiskUpdatesRepository riskUpdatesRepository, RiskUpdatesMapper riskUpdatesMapper) {
        this.riskUpdatesRepository = riskUpdatesRepository;
        this.riskUpdatesMapper = riskUpdatesMapper;
    }

    /**
     * Save a riskUpdates.
     *
     * @param riskUpdatesDTO the entity to save
     * @return the persisted entity
     */
    public RiskUpdatesDTO save(RiskUpdatesDTO riskUpdatesDTO) {
        log.debug("Request to save RiskUpdates : {}", riskUpdatesDTO);
        RiskUpdates riskUpdates = riskUpdatesMapper.toEntity(riskUpdatesDTO);
        riskUpdates = riskUpdatesRepository.save(riskUpdates);
        return riskUpdatesMapper.toDto(riskUpdates);
    }

    /**
     * Get all the riskUpdates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RiskUpdatesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RiskUpdates");
        return riskUpdatesRepository.findAll(pageable)
            .map(riskUpdatesMapper::toDto);
    }


    /**
     * Get one riskUpdates by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RiskUpdatesDTO> findOne(Long id) {
        log.debug("Request to get RiskUpdates : {}", id);
        return riskUpdatesRepository.findById(id)
            .map(riskUpdatesMapper::toDto);
    }

    /**
     * Delete the riskUpdates by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RiskUpdates : {}", id);
        riskUpdatesRepository.deleteById(id);
    }
}
