package com.marlonramon.predicts.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.marlonramon.predicts.domain.Bet} entity.
 */
public class BetDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant betDate;

    private Integer totalPoints;

    private Integer homeScore;

    private Integer visitantScore;

    private MatchDTO match;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBetDate() {
        return betDate;
    }

    public void setBetDate(Instant betDate) {
        this.betDate = betDate;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
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

    public MatchDTO getMatch() {
        return match;
    }

    public void setMatch(MatchDTO match) {
        this.match = match;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BetDTO)) {
            return false;
        }

        BetDTO betDTO = (BetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, betDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BetDTO{" +
            "id=" + getId() +
            ", betDate='" + getBetDate() + "'" +
            ", totalPoints=" + getTotalPoints() +
            ", homeScore=" + getHomeScore() +
            ", visitantScore=" + getVisitantScore() +
            ", match=" + getMatch() +
            ", user=" + getUser() +
            "}";
    }
}
