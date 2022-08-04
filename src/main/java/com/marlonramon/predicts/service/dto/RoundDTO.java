package com.marlonramon.predicts.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.marlonramon.predicts.domain.Round} entity.
 */
public class RoundDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer number;

    @NotNull
    private Instant startDate;

    private ChampionshipDTO championship;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public ChampionshipDTO getChampionship() {
        return championship;
    }

    public void setChampionship(ChampionshipDTO championship) {
        this.championship = championship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoundDTO)) {
            return false;
        }

        RoundDTO roundDTO = (RoundDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, roundDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoundDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", startDate='" + getStartDate() + "'" +
            ", championship=" + getChampionship() +
            "}";
    }
}
