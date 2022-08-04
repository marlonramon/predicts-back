package com.marlonramon.predicts.service;

import com.marlonramon.predicts.service.dto.RoundDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.marlonramon.predicts.domain.Round}.
 */
public interface RoundService {
    /**
     * Save a round.
     *
     * @param roundDTO the entity to save.
     * @return the persisted entity.
     */
    RoundDTO save(RoundDTO roundDTO);

    /**
     * Updates a round.
     *
     * @param roundDTO the entity to update.
     * @return the persisted entity.
     */
    RoundDTO update(RoundDTO roundDTO);

    /**
     * Partially updates a round.
     *
     * @param roundDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoundDTO> partialUpdate(RoundDTO roundDTO);

    /**
     * Get all the rounds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RoundDTO> findAll(Pageable pageable);

    /**
     * Get the "id" round.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoundDTO> findOne(Long id);

    /**
     * Delete the "id" round.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
