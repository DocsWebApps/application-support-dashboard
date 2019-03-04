package com.docswebapps.appsuppdash.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.docswebapps.appsuppdash.domain.enumeration.SystemStatus;

/**
 * A DTO for the App entity.
 */
public class AppDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Min(value = 0L)
    private Long problemCount;

    @NotNull
    private SystemStatus sysStatus;

    private LocalDate lastProblemDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProblemCount() {
        return problemCount;
    }

    public void setProblemCount(Long problemCount) {
        this.problemCount = problemCount;
    }

    public SystemStatus getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(SystemStatus sysStatus) {
        this.sysStatus = sysStatus;
    }

    public LocalDate getLastProblemDate() {
        return lastProblemDate;
    }

    public void setLastProblemDate(LocalDate lastProblemDate) {
        this.lastProblemDate = lastProblemDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppDTO appDTO = (AppDTO) o;
        if (appDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", problemCount=" + getProblemCount() +
            ", sysStatus='" + getSysStatus() + "'" +
            ", lastProblemDate='" + getLastProblemDate() + "'" +
            "}";
    }
}
