package com.docswebapps.appsuppdash.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.docswebapps.appsuppdash.domain.enumeration.Severity;
import com.docswebapps.appsuppdash.domain.enumeration.IssueStatus;

/**
 * A DTO for the Incident entity.
 */
public class IncidentDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate openedAt;

    @NotNull
    private String description;

    @NotNull
    private Severity severity;

    @NotNull
    private IssueStatus incidentStatus;

    private LocalDate closedAt;


    private Long probRecId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(LocalDate openedAt) {
        this.openedAt = openedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public IssueStatus getIncidentStatus() {
        return incidentStatus;
    }

    public void setIncidentStatus(IssueStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    public LocalDate getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDate closedAt) {
        this.closedAt = closedAt;
    }

    public Long getProbRecId() {
        return probRecId;
    }

    public void setProbRecId(Long problemId) {
        this.probRecId = problemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncidentDTO incidentDTO = (IncidentDTO) o;
        if (incidentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncidentDTO{" +
            "id=" + getId() +
            ", openedAt='" + getOpenedAt() + "'" +
            ", description='" + getDescription() + "'" +
            ", severity='" + getSeverity() + "'" +
            ", incidentStatus='" + getIncidentStatus() + "'" +
            ", closedAt='" + getClosedAt() + "'" +
            ", probRec=" + getProbRecId() +
            "}";
    }
}
