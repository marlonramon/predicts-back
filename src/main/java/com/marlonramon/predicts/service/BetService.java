package com.marlonramon.predicts.service;

import com.marlonramon.predicts.service.dto.BetDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.marlonramon.predicts.domain.Bet}.
 */
public interface BetService {
    /**
     * Save a bet.
     *
     * @param betDTO the entity to save.
     * @return the persisted entity.
     */
    BetDTO save(BetDTO betDTO);

    /**
     * Updates a bet.
     *
     * @param betDTO the entity to update.
     * @return the persisted entity.
     */
    BetDTO update(BetDTO betDTO);

    /**
     * Partially updates a bet.
     *
     * @param betDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BetDTO> partialUpdate(BetDTO betDTO);

    /**
     * Get all the bets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BetDTO> findOne(Long id);

    /**
     * Delete the "id" bet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
