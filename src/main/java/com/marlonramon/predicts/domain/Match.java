package com.marlonramon.predicts.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Match.
 */
@Entity
@Table(name = "match")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "match_date", nullable = false)
    private Instant matchDate;

    @Column(name = "home_score")
    private Integer homeScore;

    @Column(name = "visitant_score")
    private Integer visitantScore;

    @ManyToOne
    @JsonIgnoreProperties(value = { "championship" }, allowSetters = true)
    private Round round;

    @ManyToOne
    private Team homeTeam;

    @ManyToOne
    private Team visitantTeam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Match id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMatchDate() {
        return this.matchDate;
    }

    public Match matchDate(Instant matchDate) {
        this.setMatchDate(matchDate);
        return this;
    }

    public void setMatchDate(Instant matchDate) {
        this.matchDate = matchDate;
    }

    public Integer getHomeScore() {
        return this.homeScore;
    }

    public Match homeScore(Integer homeScore) {
        this.setHomeScore(homeScore);
        return this;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getVisitantScore() {
        return this.visitantScore;
    }

    public Match visitantScore(Integer visitantScore) {
        this.setVisitantScore(visitantScore);
        return this;
    }

    public void setVisitantScore(Integer visitantScore) {
        this.visitantScore = visitantScore;
    }

    public Round getRound() {
        return this.round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public Match round(Round round) {
        this.setRound(round);
        return this;
    }

    public Team getHomeTeam() {
        return this.homeTeam;
    }

    public void setHomeTeam(Team team) {
        this.homeTeam = team;
    }

    public Match homeTeam(Team team) {
        this.setHomeTeam(team);
        return this;
    }

    public Team getVisitantTeam() {
        return this.visitantTeam;
    }

    public void setVisitantTeam(Team team) {
        this.visitantTeam = team;
    }

    public Match visitantTeam(Team team) {
        this.setVisitantTeam(team);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Match)) {
            return false;
        }
        return id != null && id.equals(((Match) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Match{" +
            "id=" + getId() +
            ", matchDate='" + getMatchDate() + "'" +
            ", homeScore=" + getHomeScore() +
            ", visitantScore=" + getVisitantScore() +
            "}";
    }
}
