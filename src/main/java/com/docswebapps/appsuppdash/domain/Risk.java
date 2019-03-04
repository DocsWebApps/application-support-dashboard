package com.docswebapps.appsuppdash.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.docswebapps.appsuppdash.domain.enumeration.IssueStatus;

import com.docswebapps.appsuppdash.domain.enumeration.Priority;

/**
 * A Risk.
 */
@Entity
@Table(name = "risk")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Risk implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "opened_at", nullable = false)
    private LocalDate openedAt;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name = "mitigation")
    private String mitigation;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "risk_status", nullable = false)
    private IssueStatus riskStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @Column(name = "closed_at")
    private LocalDate closedAt;

    @OneToMany(mappedBy = "riskRec")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Problem> problems = new HashSet<>();
    @OneToMany(mappedBy = "riskkUpdate")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RiskUpdates> riskUpdates = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOpenedAt() {
        return openedAt;
    }

    public Risk openedAt(LocalDate openedAt) {
        this.openedAt = openedAt;
        return this;
    }

    public void setOpenedAt(LocalDate openedAt) {
        this.openedAt = openedAt;
    }

    public String getTitle() {
        return title;
    }

    public Risk title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Risk description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMitigation() {
        return mitigation;
    }

    public Risk mitigation(String mitigation) {
        this.mitigation = mitigation;
        return this;
    }

    public void setMitigation(String mitigation) {
        this.mitigation = mitigation;
    }

    public IssueStatus getRiskStatus() {
        return riskStatus;
    }

    public Risk riskStatus(IssueStatus riskStatus) {
        this.riskStatus = riskStatus;
        return this;
    }

    public void setRiskStatus(IssueStatus riskStatus) {
        this.riskStatus = riskStatus;
    }

    public Priority getPriority() {
        return priority;
    }

    public Risk priority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getClosedAt() {
        return closedAt;
    }

    public Risk closedAt(LocalDate closedAt) {
        this.closedAt = closedAt;
        return this;
    }

    public void setClosedAt(LocalDate closedAt) {
        this.closedAt = closedAt;
    }

    public Set<Problem> getProblems() {
        return problems;
    }

    public Risk problems(Set<Problem> problems) {
        this.problems = problems;
        return this;
    }

    public Risk addProblem(Problem problem) {
        this.problems.add(problem);
        problem.setRiskRec(this);
        return this;
    }

    public Risk removeProblem(Problem problem) {
        this.problems.remove(problem);
        problem.setRiskRec(null);
        return this;
    }

    public void setProblems(Set<Problem> problems) {
        this.problems = problems;
    }

    public Set<RiskUpdates> getRiskUpdates() {
        return riskUpdates;
    }

    public Risk riskUpdates(Set<RiskUpdates> riskUpdates) {
        this.riskUpdates = riskUpdates;
        return this;
    }

    public Risk addRiskUpdates(RiskUpdates riskUpdates) {
        this.riskUpdates.add(riskUpdates);
        riskUpdates.setRiskkUpdate(this);
        return this;
    }

    public Risk removeRiskUpdates(RiskUpdates riskUpdates) {
        this.riskUpdates.remove(riskUpdates);
        riskUpdates.setRiskkUpdate(null);
        return this;
    }

    public void setRiskUpdates(Set<RiskUpdates> riskUpdates) {
        this.riskUpdates = riskUpdates;
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
        Risk risk = (Risk) o;
        if (risk.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), risk.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Risk{" +
            "id=" + getId() +
            ", openedAt='" + getOpenedAt() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", mitigation='" + getMitigation() + "'" +
            ", riskStatus='" + getRiskStatus() + "'" +
            ", priority='" + getPriority() + "'" +
            ", closedAt='" + getClosedAt() + "'" +
            "}";
    }
}
