package com.docswebapps.appsuppdash.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the IncidentUpdates entity.
 */
public class IncidentUpdatesDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate updatedAt;

    
    @Lob
    private String updateText;


    private Long inUpdateId;

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

    public Long getInUpdateId() {
        return inUpdateId;
    }

    public void setInUpdateId(Long incidentId) {
        this.inUpdateId = incidentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncidentUpdatesDTO incidentUpdatesDTO = (IncidentUpdatesDTO) o;
        if (incidentUpdatesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidentUpdatesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncidentUpdatesDTO{" +
            "id=" + getId() +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", updateText='" + getUpdateText() + "'" +
            ", inUpdate=" + getInUpdateId() +
            "}";
    }
}
