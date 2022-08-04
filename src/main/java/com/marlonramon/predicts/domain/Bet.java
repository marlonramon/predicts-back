package com.marlonramon.predicts.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Bet.
 */
@Entity
@Table(name = "bet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bet extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "bet_date", nullable = false)
    private Instant betDate;

    @Column(name = "total_points")
    private Integer totalPoints;

    @Embedded
    private Scores scores;

    @ManyToOne
    @JsonIgnoreProperties(value = { "round", "homeTeam", "visitantTeam" }, allowSetters = true)
    private Match match;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBetDate() {
        return this.betDate;
    }

    public Bet betDate(Instant betDate) {
        this.setBetDate(betDate);
        return this;
    }

    public void setBetDate(Instant betDate) {
        this.betDate = betDate;
    }

    public Integer getTotalPoints() {
        return this.totalPoints;
    }

    public Bet totalPoints(Integer totalPoints) {
        this.setTotalPoints(totalPoints);
        return this;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Short getHomeScore() {
        return Objects.nonNull(this.scores) ? this.scores.getHomeScore() : 0;
    }

    public Bet homeScore(Short homeScore) {
        this.setHomeScore(homeScore);
        return this;
    }

    public void setHomeScore(Short homeScore) {
        if(Objects.isNull(this.scores)) {
           this.scores = new Scores();
        }
        this.scores.setHomeScore(homeScore);
    }

    public Short getVisitantScore() {
        return Objects.nonNull(this.scores) ? this.scores.getVisitantScore() : 0;
    }

    public Bet visitantScore(Short visitantScore) {
        this.setVisitantScore(visitantScore);
        return this;
    }

    public void setVisitantScore(Short visitantScore) {
        if(Objects.isNull(this.scores)) {
            this.scores = new Scores();
        }
        this.scores.setVisitantScore(visitantScore);
    }

    public Match getMatch() {
        return this.match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Bet match(Match match) {
        this.setMatch(match);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bet user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bet)) {
            return false;
        }
        return id != null && id.equals(((Bet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bet{" +
            "id=" + getId() +
            ", betDate='" + getBetDate() + "'" +
            ", totalPoints=" + getTotalPoints() +
            ", homeScore=" + getHomeScore() +
            ", visitantScore=" + getVisitantScore() +
            "}";
    }
}
