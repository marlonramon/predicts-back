package com.marlonramon.predicts.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.marlonramon.predicts.domain.Championship} entity.
 */
public class ChampionshipDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private SweepstakeDTO sweepstake;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public SweepstakeDTO getSweepstake() {
        return sweepstake;
    }

    public void setSweepstake(SweepstakeDTO sweepstake) {
        this.sweepstake = sweepstake;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChampionshipDTO)) {
            return false;
        }

        ChampionshipDTO championshipDTO = (ChampionshipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, championshipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChampionshipDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", sweepstake=" + getSweepstake() +
            "}";
    }
}
