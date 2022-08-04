package com.marlonramon.predicts.repository;

import com.marlonramon.predicts.domain.Championship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Championship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {}
