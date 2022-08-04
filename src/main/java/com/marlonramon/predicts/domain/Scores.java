package com.marlonramon.predicts.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static java.util.Objects.*;

@Embeddable
public class Scores {

    @Column(name="home_score")
    private Short homeScore;

    @Column(name="visitant_score")
    private Short visitantScore;

    public Scores() {

    }

    public Short getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Short homeScore) {
        this.homeScore = homeScore;
    }

    public Short getVisitantScore() {
        return visitantScore;
    }

    public void setVisitantScore(Short visitantScore) {
        this.visitantScore = visitantScore;
    }

    public boolean isValid() {
        return nonNull(this.homeScore) && nonNull(this.visitantScore);
    }
}
