package com.docswebapps.appsuppdash.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the RiskUpdates entity.
 */
public class RiskUpdatesDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate updatedAt;

    
    @Lob
    private String updateText;


    private Long riskkUpdateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdateText() {
        return updateText;
    }

    public void setUpdateText(String updateText) {
        this.updateText = updateText;
    }

    public Long getRiskkUpdateId() {
        return riskkUpdateId;
    }

    public void setRiskkUpdateId(Long riskId) {
        this.riskkUpdateId = riskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RiskUpdatesDTO riskUpdatesDTO = (RiskUpdatesDTO) o;
        if (riskUpdatesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), riskUpdatesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RiskUpdatesDTO{" +
            "id=" + getId() +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", updateText='" + getUpdateText() + "'" +
            ", riskkUpdate=" + getRiskkUpdateId() +
            "}";
    }
}
