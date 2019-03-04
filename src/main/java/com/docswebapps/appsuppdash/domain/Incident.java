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

import com.docswebapps.appsuppdash.domain.enumeration.Severity;

import com.docswebapps.appsuppdash.domain.enumeration.IssueStatus;

/**
 * A Incident.
 */
@Entity
@Table(name = "incident")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "opened_at", nullable = false)
    private LocalDate openedAt;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false)
    private Severity severity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "incident_status", nullable = false)
    private IssueStatus incidentStatus;

    @Column(name = "closed_at")
    private LocalDate closedAt;

    @OneToMany(mappedBy = "inUpdate")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IncidentUpdates> incidentUpdates = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("incidents")
    private Problem probRec;

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

    public Incident openedAt(LocalDate openedAt) {
        this.openedAt = openedAt;
        return this;
    }

    public void setOpenedAt(LocalDate openedAt) {
        this.openedAt = openedAt;
    }

    public String getDescription() {
        return description;
    }

    public Incident description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Severity getSeverity() {
        return severity;
    }

    public Incident severity(Severity severity) {
        this.severity = severity;
        return this;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public IssueStatus getIncidentStatus() {
        return incidentStatus;
    }

    public Incident incidentStatus(IssueStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
        return this;
    }

    public void setIncidentStatus(IssueStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    public LocalDate getClosedAt() {
        return closedAt;
    }

    public Incident closedAt(LocalDate closedAt) {
        this.closedAt = closedAt;
        return this;
    }

    public void setClosedAt(LocalDate closedAt) {
        this.closedAt = closedAt;
    }

    public Set<IncidentUpdates> getIncidentUpdates() {
        return incidentUpdates;
    }

    public Incident incidentUpdates(Set<IncidentUpdates> incidentUpdates) {
        this.incidentUpdates = incidentUpdates;
        return this;
    }

    public Incident addIncidentUpdates(IncidentUpdates incidentUpdates) {
        this.incidentUpdates.add(incidentUpdates);
        incidentUpdates.setInUpdate(this);
        return this;
    }

    public Incident removeIncidentUpdates(IncidentUpdates incidentUpdates) {
        this.incidentUpdates.remove(incidentUpdates);
        incidentUpdates.setInUpdate(null);
        return this;
    }

    public void setIncidentUpdates(Set<IncidentUpdates> incidentUpdates) {
        this.incidentUpdates = incidentUpdates;
    }

    public Problem getProbRec() {
        return probRec;
    }

    public Incident probRec(Problem problem) {
        this.probRec = problem;
        return this;
    }

    public void setProbRec(Problem problem) {
        this.probRec = problem;
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
        Incident incident = (Incident) o;
        if (incident.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incident.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Incident{" +
            "id=" + getId() +
            ", openedAt='" + getOpenedAt() + "'" +
            ", description='" + getDescription() + "'" +
            ", severity='" + getSeverity() + "'" +
            ", incidentStatus='" + getIncidentStatus() + "'" +
            ", closedAt='" + getClosedAt() + "'" +
            "}";
    }
}
