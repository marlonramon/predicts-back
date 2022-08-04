package com.marlonramon.predicts.service;

import com.marlonramon.predicts.service.dto.SweepstakeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.marlonramon.predicts.domain.Sweepstake}.
 */
public interface SweepstakeService {
    /**
     * Save a sweepstake.
     *
     * @param sweepstakeDTO the entity to save.
     * @return the persisted entity.
     */
    SweepstakeDTO save(SweepstakeDTO sweepstakeDTO);

    /**
     * Updates a sweepstake.
     *
     * @param sweepstakeDTO the entity to update.
     * @return the persisted entity.
     */
    SweepstakeDTO update(SweepstakeDTO sweepstakeDTO);

    /**
     * Partially updates a sweepstake.
     *
     * @param sweepstakeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SweepstakeDTO> partialUpdate(SweepstakeDTO sweepstakeDTO);

    /**
     * Get all the sweepstakes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SweepstakeDTO> findAll(Pageable pageable);

    /**
     * Get all the sweepstakes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SweepstakeDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" sweepstake.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SweepstakeDTO> findOne(Long id);

    /**
     * Delete the "id" sweepstake.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
