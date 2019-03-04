package com.docswebapps.appsuppdash.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.docswebapps.appsuppdash.domain.enumeration.SystemStatus;

/**
 * A App.
 */
@Entity
@Table(name = "app")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class App implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Min(value = 0L)
    @Column(name = "problem_count", nullable = false)
    private Long problemCount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sys_status", nullable = false)
    private SystemStatus sysStatus;

    @Column(name = "last_problem_date")
    private LocalDate lastProblemDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public App name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProblemCount() {
        return problemCount;
    }

    public App problemCount(Long problemCount) {
        this.problemCount = problemCount;
        return this;
    }

    public void setProblemCount(Long problemCount) {
        this.problemCount = problemCount;
    }

    public SystemStatus getSysStatus() {
        return sysStatus;
    }

    public App sysStatus(SystemStatus sysStatus) {
        this.sysStatus = sysStatus;
        return this;
    }

    public void setSysStatus(SystemStatus sysStatus) {
        this.sysStatus = sysStatus;
    }

    public LocalDate getLastProblemDate() {
        return lastProblemDate;
    }

    public App lastProblemDate(LocalDate lastProblemDate) {
        this.lastProblemDate = lastProblemDate;
        return this;
    }

    public void setLastProblemDate(LocalDate lastProblemDate) {
        this.lastProblemDate = lastProblemDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        App app = (App) o;
        if (app.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), app.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "App{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", problemCount=" + getProblemCount() +
            ", sysStatus='" + getSysStatus() + "'" +
            ", lastProblemDate='" + getLastProblemDate() + "'" +
            "}";
    }
}
