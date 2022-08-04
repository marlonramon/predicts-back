package com.marlonramon.predicts.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.marlonramon.predicts.domain.Sweepstake} entity.
 */
public class SweepstakeDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private Integer pointsForTwoScore;

    @NotNull
    private Integer pointsForOneScore;

    @NotNull
    private Integer pointsForOnlyResult;

    private UserDTO user;

    private Set<UserDTO> users = new HashSet<>();

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

    public Integer getPointsForTwoScore() {
        return pointsForTwoScore;
    }

    public void setPointsForTwoScore(Integer pointsForTwoScore) {
        this.pointsForTwoScore = pointsForTwoScore;
    }

    public Integer getPointsForOneScore() {
        return pointsForOneScore;
    }

    public void setPointsForOneScore(Integer pointsForOneScore) {
        this.pointsForOneScore = pointsForOneScore;
    }

    public Integer getPointsForOnlyResult() {
        return pointsForOnlyResult;
    }

    public void setPointsForOnlyResult(Integer pointsForOnlyResult) {
        this.pointsForOnlyResult = pointsForOnlyResult;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SweepstakeDTO)) {
            return false;
        }

        SweepstakeDTO sweepstakeDTO = (SweepstakeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sweepstakeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SweepstakeDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", pointsForTwoScore=" + getPointsForTwoScore() +
            ", pointsForOneScore=" + getPointsForOneScore() +
            ", pointsForOnlyResult=" + getPointsForOnlyResult() +
            ", user=" + getUser() +
            ", users=" + getUsers() +
            "}";
    }
}
