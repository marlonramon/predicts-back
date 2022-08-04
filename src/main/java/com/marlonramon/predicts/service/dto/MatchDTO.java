package com.marlonramon.predicts.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.marlonramon.predicts.domain.Match} entity.
 */
public class MatchDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant matchDate;

    private Integer homeScore;

    private Integer visitantScore;

    private RoundDTO round;

    private TeamDTO homeTeam;

    private TeamDTO visitantTeam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Instant matchDate) {
        this.matchDate = matchDate;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getVisitantScore() {
        return visitantScore;
    }

    public void setVisitantScore(Integer visitantScore) {
        this.visitantScore = visitantScore;
    }

    public RoundDTO getRound() {
        return round;
    }

    public void setRound(RoundDTO round) {
        this.round = round;
    }

    public TeamDTO getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(TeamDTO homeTeam) {
        this.homeTeam = homeTeam;
    }

    public TeamDTO getVisitantTeam() {
        return visitantTeam;
    }

    public void setVisitantTeam(TeamDTO visitantTeam) {
        this.visitantTeam = visitantTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchDTO)) {
            return false;
        }

        MatchDTO matchDTO = (MatchDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchDTO{" +
            "id=" + getId() +
            ", matchDate='" + getMatchDate() + "'" +
            ", homeScore=" + getHomeScore() +
            ", visitantScore=" + getVisitantScore() +
            ", round=" + getRound() +
            ", homeTeam=" + getHomeTeam() +
            ", visitantTeam=" + getVisitantTeam() +
            "}";
    }
}
