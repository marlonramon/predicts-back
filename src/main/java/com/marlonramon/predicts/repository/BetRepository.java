package com.marlonramon.predicts.repository;

import com.marlonramon.predicts.domain.Bet;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
    @Query("select bet from Bet bet where bet.user.login = ?#{principal.preferredUsername}")
    List<Bet> findByUserIsCurrentUser();
}
