package com.marlonramon.predicts.repository;

import com.marlonramon.predicts.domain.Sweepstake;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface SweepstakeRepositoryWithBagRelationships {
    Optional<Sweepstake> fetchBagRelationships(Optional<Sweepstake> sweepstake);

    List<Sweepstake> fetchBagRelationships(List<Sweepstake> sweepstakes);

    Page<Sweepstake> fetchBagRelationships(Page<Sweepstake> sweepstakes);
}
