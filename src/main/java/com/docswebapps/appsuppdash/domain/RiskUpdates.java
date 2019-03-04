package com.docswebapps.appsuppdash.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A RiskUpdates.
 */
@Entity
@Table(name = "risk_updates")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RiskUpdates implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

    
    @Lob
    @Column(name = "update_text", nullable = false)
    private String updateText;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("riskUpdates")
    private Risk riskkUpdate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public RiskUpdates updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdateText() {
        return updateText;
    }

    public RiskUpdates updateText(String updateText) {
        this.updateText = updateText;
        return this;
    }

    public void setUpdateText(String updateText) {
        this.updateText = updateText;
    }

    public Risk getRiskkUpdate() {
        return riskkUpdate;
    }

    public RiskUpdates riskkUpdate(Risk risk) {
        this.riskkUpdate = risk;
        return this;
    }

    public void setRiskkUpdate(Risk risk) {
        this.riskkUpdate = risk;
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
        RiskUpdates riskUpdates = (RiskUpdates) o;
        if (riskUpdates.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), riskUpdates.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RiskUpdates{" +
            "id=" + getId() +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", updateText='" + getUpdateText() + "'" +
            "}";
    }
}
