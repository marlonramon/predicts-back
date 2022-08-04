package com.marlonramon.predicts.repository;

import com.marlonramon.predicts.domain.Sweepstake;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class SweepstakeRepositoryWithBagRelationshipsImpl implements SweepstakeRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Sweepstake> fetchBagRelationships(Optional<Sweepstake> sweepstake) {
        return sweepstake.map(this::fetchUsers);
    }

    @Override
    public Page<Sweepstake> fetchBagRelationships(Page<Sweepstake> sweepstakes) {
        return new PageImpl<>(fetchBagRelationships(sweepstakes.getContent()), sweepstakes.getPageable(), sweepstakes.getTotalElements());
    }

    @Override
    public List<Sweepstake> fetchBagRelationships(List<Sweepstake> sweepstakes) {
        return Optional.of(sweepstakes).map(this::fetchUsers).orElse(Collections.emptyList());
    }

    Sweepstake fetchUsers(Sweepstake result) {
        return entityManager
            .createQuery(
                "select sweepstake from Sweepstake sweepstake left join fetch sweepstake.users where sweepstake is :sweepstake",
                Sweepstake.class
            )
            .setParameter("sweepstake", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Sweepstake> fetchUsers(List<Sweepstake> sweepstakes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, sweepstakes.size()).forEach(index -> order.put(sweepstakes.get(index).getId(), index));
        List<Sweepstake> result = entityManager
            .createQuery(
                "select distinct sweepstake from Sweepstake sweepstake left join fetch sweepstake.users where sweepstake in :sweepstakes",
                Sweepstake.class
            )
            .setParameter("sweepstakes", sweepstakes)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
