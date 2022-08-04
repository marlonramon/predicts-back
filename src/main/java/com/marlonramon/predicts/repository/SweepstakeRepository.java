package com.marlonramon.predicts.repository;

import com.marlonramon.predicts.domain.Sweepstake;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sweepstake entity.
 *
 * When extending this class, extend SweepstakeRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface SweepstakeRepository extends SweepstakeRepositoryWithBagRelationships, JpaRepository<Sweepstake, Long> {
    @Query("select sweepstake from Sweepstake sweepstake where sweepstake.user.login = ?#{principal.preferredUsername}")
    List<Sweepstake> findByUserIsCurrentUser();

    default Optional<Sweepstake> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Sweepstake> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Sweepstake> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
