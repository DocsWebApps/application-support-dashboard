package com.docswebapps.appsuppdash.service;

import com.docswebapps.appsuppdash.domain.App;
import com.docswebapps.appsuppdash.domain.AppName;
import com.docswebapps.appsuppdash.domain.AppStatus;
import com.docswebapps.appsuppdash.domain.enumeration.SystemStatus;
import com.docswebapps.appsuppdash.repository.AppRepository;
import com.docswebapps.appsuppdash.service.dto.AppDTO;
import com.docswebapps.appsuppdash.service.mapper.AppMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing App.
 */
@Service
@Transactional
public class AppService {
    private final Logger log = LoggerFactory.getLogger(AppService.class);

    private final AppRepository appRepository;

    private final AppMapper appMapper;

    public AppService(AppRepository appRepository, AppMapper appMapper) {
        this.appRepository = appRepository;
        this.appMapper = appMapper;
    }

    /**
     * Save a app.
     *
     * @param appDTO the entity to save
     * @return the persisted entity
     */
    public AppDTO save(AppDTO appDTO) {
        log.debug("AppService: Request to save App : {}", appDTO);
        App app = appMapper.toEntity(appDTO);
        app = appRepository.save(app);
        return appMapper.toDto(app);
    }

    /**
     * Get all the apps.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AppDTO> findAll() {
        log.debug("AppService: Request to get all Apps");
        return appRepository.findAll().stream()
            .map(appMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one app by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AppDTO> findOne(Long id) {
        log.debug("AppService: Request to get App : {}", id);
        return appRepository.findById(id)
            .map(appMapper::toDto);
    }

    /**
     * Delete the app by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("AppService: Request to delete App : {}", id);
        appRepository.deleteById(id);
    }

    // My Custom Code
    /**
     * GET Application Status
     */
    public AppStatus getAppStats() {
        log.debug("AppService: Get Current Application Statistics");
        AppStatus appStat = new AppStatus();
        App app = appRepository.findFirstByOrderByIdDesc();
        Long probCount=app.getProblemCount();
        String appStatus = app.getSysStatus().toString();
        Long daysSinceLastIncident=app.getLastProblemDate() != null ? ChronoUnit.DAYS.between(app.getLastProblemDate(), LocalDate.now()) : -1;
        appStat.setAppStatus(appStatus);
        appStat.setProbCount(probCount == 1 ? "1 Previous P1/P2" : probCount + " Previous P1/P2's Recorded");
        appStat.setMsgColor(daysSinceLastIncident == 0 ? "red" : "black");

        if (appStatus.equals("GREEN")) {
            appStat.setSysMessage("System is OK: No Major Incidents Reported");
            appStat.setSysMessageColor("green");
        } else if (appStatus.equals("AMBER")) {
            appStat.setSysMessage("P2 Incident In Progress Now");
            appStat.setSysMessageColor("#f0d234");
        } else {
            appStat.setSysMessage("P1 Incident In Progress Now");
            appStat.setSysMessageColor("red");
        }

        if (daysSinceLastIncident == 0) {
            appStat.setMsgDetail("A P1/P2 Incident Occurred Earlier Today");
        } else if (daysSinceLastIncident == 1) {
            appStat.setMsgDetail(daysSinceLastIncident.toString() + " Day Since the Last P1/P2");
        } else if (daysSinceLastIncident > 1) {
            appStat.setMsgDetail(daysSinceLastIncident.toString() + " Days Since the Last P1/P2");
        } else {
            appStat.setMsgDetail("No P1/P2's Recorded Yet");
        }
        return appStat;
    }

    /**
     * UPDATE Application Status
     */
    public void setAppSysStatus(SystemStatus status) {
        log.debug("AppService: Set Application Status : {}", status);
        App app = appRepository.findFirstByOrderByIdDesc();
        app.setSysStatus(status);
        appRepository.save(app);
    }

    /**
     * UPDATE Application LastProblemDate and ProblemCount
     */
    public void updateApp(LocalDate dt) {
        log.debug("AppService: Update ProblemCount and LastProblemDate : {}", dt);
        App app = appRepository.findFirstByOrderByIdDesc();
        Long problemCount=app.getProblemCount();
        app.setProblemCount(++problemCount);
        app.setLastProblemDate(dt);
        appRepository.save(app);
    }

    /**
     * GET Application name
     */
    public AppName getAppName() {
        log.debug("AppService: Get Application Name");
        App app = appRepository.findFirstByOrderByIdDesc();
        AppName appName = new AppName();
        appName.setName(app.getName());
        return appName;
    }
}
