package com.marlonramon.predicts.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Sweepstake.
 */
@Entity
@Table(name = "sweepstake")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sweepstake implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "points_for_two_score", nullable = false)
    private Integer pointsForTwoScore;

    @NotNull
    @Column(name = "points_for_one_score", nullable = false)
    private Integer pointsForOneScore;

    @NotNull
    @Column(name = "points_for_only_result", nullable = false)
    private Integer pointsForOnlyResult;

    @ManyToOne
    @ManyToMany
    @JoinTable(
        name = "rel_sweepstake__user",
        joinColumns = @JoinColumn(name = "sweepstake_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private User user;

    @ManyToOne
    @ManyToMany
    @JoinTable(
        name = "rel_sweepstake__user",
        joinColumns = @JoinColumn(name = "sweepstake_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<User> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sweepstake id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Sweepstake description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPointsForTwoScore() {
        return this.pointsForTwoScore;
    }

    public Sweepstake pointsForTwoScore(Integer pointsForTwoScore) {
        this.setPointsForTwoScore(pointsForTwoScore);
        return this;
    }

    public void setPointsForTwoScore(Integer pointsForTwoScore) {
        this.pointsForTwoScore = pointsForTwoScore;
    }

    public Integer getPointsForOneScore() {
        return this.pointsForOneScore;
    }

    public Sweepstake pointsForOneScore(Integer pointsForOneScore) {
        this.setPointsForOneScore(pointsForOneScore);
        return this;
    }

    public void setPointsForOneScore(Integer pointsForOneScore) {
        this.pointsForOneScore = pointsForOneScore;
    }

    public Integer getPointsForOnlyResult() {
        return this.pointsForOnlyResult;
    }

    public Sweepstake pointsForOnlyResult(Integer pointsForOnlyResult) {
        this.setPointsForOnlyResult(pointsForOnlyResult);
        return this;
    }

    public void setPointsForOnlyResult(Integer pointsForOnlyResult) {
        this.pointsForOnlyResult = pointsForOnlyResult;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Sweepstake user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Sweepstake users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Sweepstake addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Sweepstake removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sweepstake)) {
            return false;
        }
        return id != null && id.equals(((Sweepstake) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sweepstake{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", pointsForTwoScore=" + getPointsForTwoScore() +
            ", pointsForOneScore=" + getPointsForOneScore() +
            ", pointsForOnlyResult=" + getPointsForOnlyResult() +
            "}";
    }
}
