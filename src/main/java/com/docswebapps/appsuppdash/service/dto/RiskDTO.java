package com.docswebapps.appsuppdash.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.docswebapps.appsuppdash.domain.enumeration.IssueStatus;
import com.docswebapps.appsuppdash.domain.enumeration.Priority;

/**
 * A DTO for the Risk entity.
 */
public class RiskDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate openedAt;

    @NotNull
    private String title;


    @Lob
    private String description;

    @Lob
    private String mitigation;

    @NotNull
    private IssueStatus riskStatus;

    @NotNull
    private Priority priority;

    private LocalDate closedAt;

    private Long problemCount;

    public Long getProblemCount() {
      return this.problemCount;
    }

    public void setProblemCount(Long problemCount) {
      this.problemCount = problemCount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMitigation() {
        return mitigation;
    }

    public void setMitigation(String mitigation) {
        this.mitigation = mitigation;
    }

    public IssueStatus getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(IssueStatus riskStatus) {
        this.riskStatus = riskStatus;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RiskDTO riskDTO = (RiskDTO) o;
        if (riskDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), riskDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RiskDTO{" +
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
