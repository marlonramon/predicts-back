package com.marlonramon.predicts.service;

import com.marlonramon.predicts.service.dto.ChampionshipDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.marlonramon.predicts.domain.Championship}.
 */
public interface ChampionshipService {
    /**
     * Save a championship.
     *
     * @param championshipDTO the entity to save.
     * @return the persisted entity.
     */
    ChampionshipDTO save(ChampionshipDTO championshipDTO);

    /**
     * Updates a championship.
     *
     * @param championshipDTO the entity to update.
     * @return the persisted entity.
     */
    ChampionshipDTO update(ChampionshipDTO championshipDTO);

    /**
     * Partially updates a championship.
     *
     * @param championshipDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ChampionshipDTO> partialUpdate(ChampionshipDTO championshipDTO);

    /**
     * Get all the championships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChampionshipDTO> findAll(Pageable pageable);

    /**
     * Get the "id" championship.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChampionshipDTO> findOne(Long id);

    /**
     * Delete the "id" championship.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
