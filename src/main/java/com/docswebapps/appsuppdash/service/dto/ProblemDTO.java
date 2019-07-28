package com.docswebapps.appsuppdash.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.docswebapps.appsuppdash.domain.enumeration.IssueStatus;
import com.docswebapps.appsuppdash.domain.enumeration.Priority;

/**
 * A DTO for the Problem entity.
 */
public class ProblemDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate openedAt;

    @NotNull
    private String title;

    
    @Lob
    private String statement;

    @NotNull
    private IssueStatus probStatus;

    @NotNull
    private Priority priority;

    private LocalDate closedAt;

    private Long riskRecId;

    private Long incidentCount;

    public Long getIncidentCount() {
      return this.incidentCount;
    }

    public void setIncidentCount(Long incidentCount) {
      this.incidentCount = incidentCount;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public IssueStatus getProbStatus() {
        return probStatus;
    }

    public void setProbStatus(IssueStatus probStatus) {
        this.probStatus = probStatus;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDate closedAt) {
        this.closedAt = closedAt;
    }

    public Long getRiskRecId() {
        return riskRecId;
    }

    public void setRiskRecId(Long riskId) {
        this.riskRecId = riskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProblemDTO problemDTO = (ProblemDTO) o;
        if (problemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), problemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProblemDTO{" +
            "id=" + getId() +
            ", openedAt='" + getOpenedAt() + "'" +
            ", title='" + getTitle() + "'" +
            ", statement='" + getStatement() + "'" +
            ", probStatus='" + getProbStatus() + "'" +
            ", priority='" + getPriority() + "'" +
            ", closedAt='" + getClosedAt() + "'" +
            ", riskRec=" + getRiskRecId() +
            "}";
    }
}
