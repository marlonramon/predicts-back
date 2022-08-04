package com.marlonramon.predicts.service;

import com.marlonramon.predicts.service.dto.MatchDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.marlonramon.predicts.domain.Match}.
 */
public interface MatchService {
    /**
     * Save a match.
     *
     * @param matchDTO the entity to save.
     * @return the persisted entity.
     */
    MatchDTO save(MatchDTO matchDTO);

    /**
     * Updates a match.
     *
     * @param matchDTO the entity to update.
     * @return the persisted entity.
     */
    MatchDTO update(MatchDTO matchDTO);

    /**
     * Partially updates a match.
     *
     * @param matchDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MatchDTO> partialUpdate(MatchDTO matchDTO);

    /**
     * Get all the matches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MatchDTO> findAll(Pageable pageable);

    /**
     * Get the "id" match.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatchDTO> findOne(Long id);

    /**
     * Delete the "id" match.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
