package com.docswebapps.appsuppdash.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A Problem.
 */
@Entity
@Table(name = "problem")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Problem implements Serializable {

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
    @Column(name = "statement", nullable = false)
    private String statement;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "prob_status", nullable = false)
    private IssueStatus probStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @Column(name = "closed_at")
    private LocalDate closedAt;

    @Transient
    private Long incidentCount;

    @OneToMany(mappedBy = "probRec")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Incident> incidents = new HashSet<>();
    @OneToMany(mappedBy = "probUpdate")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProblemUpdates> problemUpdates = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("problems")
    private Risk riskRec;

    public Long getIncidentCount() {
      return this.incidentCount;
    }

    public void setIncidentCount(Long incidentCount) {
      this.incidentCount = incidentCount;
    }

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

    public Problem openedAt(LocalDate openedAt) {
        this.openedAt = openedAt;
        return this;
    }

    public void setOpenedAt(LocalDate openedAt) {
        this.openedAt = openedAt;
    }

    public String getTitle() {
        return title;
    }

    public Problem title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatement() {
        return statement;
    }

    public Problem statement(String statement) {
        this.statement = statement;
        return this;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public IssueStatus getProbStatus() {
        return probStatus;
    }

    public Problem probStatus(IssueStatus probStatus) {
        this.probStatus = probStatus;
        return this;
    }

    public void setProbStatus(IssueStatus probStatus) {
        this.probStatus = probStatus;
    }

    public Priority getPriority() {
        return priority;
    }

    public Problem priority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getClosedAt() {
        return closedAt;
    }

    public Problem closedAt(LocalDate closedAt) {
        this.closedAt = closedAt;
        return this;
    }

    public void setClosedAt(LocalDate closedAt) {
        this.closedAt = closedAt;
    }

    public Set<Incident> getIncidents() {
        return incidents;
    }

    public Problem incidents(Set<Incident> incidents) {
        this.incidents = incidents;
        return this;
    }

    public Problem addIncident(Incident incident) {
        this.incidents.add(incident);
        incident.setProbRec(this);
        return this;
    }

    public Problem removeIncident(Incident incident) {
        this.incidents.remove(incident);
        incident.setProbRec(null);
        return this;
    }

    public void setIncidents(Set<Incident> incidents) {
        this.incidents = incidents;
    }

    public Set<ProblemUpdates> getProblemUpdates() {
        return problemUpdates;
    }

    public Problem problemUpdates(Set<ProblemUpdates> problemUpdates) {
        this.problemUpdates = problemUpdates;
        return this;
    }

    public Problem addProblemUpdates(ProblemUpdates problemUpdates) {
        this.problemUpdates.add(problemUpdates);
        problemUpdates.setProbUpdate(this);
        return this;
    }

    public Problem removeProblemUpdates(ProblemUpdates problemUpdates) {
        this.problemUpdates.remove(problemUpdates);
        problemUpdates.setProbUpdate(null);
        return this;
    }

    public void setProblemUpdates(Set<ProblemUpdates> problemUpdates) {
        this.problemUpdates = problemUpdates;
    }

    public Risk getRiskRec() {
        return riskRec;
    }

    public Problem riskRec(Risk risk) {
        this.riskRec = risk;
        return this;
    }

    public void setRiskRec(Risk risk) {
        this.riskRec = risk;
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
        Problem problem = (Problem) o;
        if (problem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), problem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Problem{" +
            "id=" + getId() +
            ", openedAt='" + getOpenedAt() + "'" +
            ", title='" + getTitle() + "'" +
            ", statement='" + getStatement() + "'" +
            ", probStatus='" + getProbStatus() + "'" +
            ", priority='" + getPriority() + "'" +
            ", closedAt='" + getClosedAt() + "'" +
            "}";
    }
}
