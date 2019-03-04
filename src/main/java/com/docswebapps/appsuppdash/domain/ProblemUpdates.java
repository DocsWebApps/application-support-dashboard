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
 * A ProblemUpdates.
 */
@Entity
@Table(name = "problem_updates")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProblemUpdates implements Serializable {

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
    @JsonIgnoreProperties("problemUpdates")
    private Problem probUpdate;

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

    public ProblemUpdates updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdateText() {
        return updateText;
    }

    public ProblemUpdates updateText(String updateText) {
        this.updateText = updateText;
        return this;
    }

    public void setUpdateText(String updateText) {
        this.updateText = updateText;
    }

    public Problem getProbUpdate() {
        return probUpdate;
    }

    public ProblemUpdates probUpdate(Problem problem) {
        this.probUpdate = problem;
        return this;
    }

    public void setProbUpdate(Problem problem) {
        this.probUpdate = problem;
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
        ProblemUpdates problemUpdates = (ProblemUpdates) o;
        if (problemUpdates.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), problemUpdates.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProblemUpdates{" +
            "id=" + getId() +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", updateText='" + getUpdateText() + "'" +
            "}";
    }
}
