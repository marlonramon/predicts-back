package com.marlonramon.predicts.web.rest;

import com.marlonramon.predicts.repository.BetRepository;
import com.marlonramon.predicts.service.BetService;
import com.marlonramon.predicts.service.dto.BetDTO;
import com.marlonramon.predicts.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.marlonramon.predicts.domain.Bet}.
 */
@RestController
@RequestMapping("/api")
public class BetResource {

    private final Logger log = LoggerFactory.getLogger(BetResource.class);

    private static final String ENTITY_NAME = "bet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BetService betService;

    private final BetRepository betRepository;

    public BetResource(BetService betService, BetRepository betRepository) {
        this.betService = betService;
        this.betRepository = betRepository;
    }

    /**
     * {@code POST  /bets} : Create a new bet.
     *
     * @param betDTO the betDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new betDTO, or with status {@code 400 (Bad Request)} if the bet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bets")
    public ResponseEntity<BetDTO> createBet(@Valid @RequestBody BetDTO betDTO) throws URISyntaxException {
        log.debug("REST request to save Bet : {}", betDTO);
        if (betDTO.getId() != null) {
            throw new BadRequestAlertException("A new bet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BetDTO result = betService.save(betDTO);
        return ResponseEntity
            .created(new URI("/api/bets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bets/:id} : Updates an existing bet.
     *
     * @param id the id of the betDTO to save.
     * @param betDTO the betDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated betDTO,
     * or with status {@code 400 (Bad Request)} if the betDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the betDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bets/{id}")
    public ResponseEntity<BetDTO> updateBet(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody BetDTO betDTO)
        throws URISyntaxException {
        log.debug("REST request to update Bet : {}, {}", id, betDTO);
        if (betDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, betDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!betRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BetDTO result = betService.update(betDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, betDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bets/:id} : Partial updates given fields of an existing bet, field will ignore if it is null
     *
     * @param id the id of the betDTO to save.
     * @param betDTO the betDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated betDTO,
     * or with status {@code 400 (Bad Request)} if the betDTO is not valid,
     * or with status {@code 404 (Not Found)} if the betDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the betDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BetDTO> partialUpdateBet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BetDTO betDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bet partially : {}, {}", id, betDTO);
        if (betDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, betDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!betRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BetDTO> result = betService.partialUpdate(betDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, betDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bets} : get all the bets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bets in body.
     */
    @GetMapping("/bets")
    public ResponseEntity<List<BetDTO>> getAllBets(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Bets");
        Page<BetDTO> page = betService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bets/:id} : get the "id" bet.
     *
     * @param id the id of the betDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the betDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bets/{id}")
    public ResponseEntity<BetDTO> getBet(@PathVariable Long id) {
        log.debug("REST request to get Bet : {}", id);
        Optional<BetDTO> betDTO = betService.findOne(id);
        return ResponseUtil.wrapOrNotFound(betDTO);
    }

    /**
     * {@code DELETE  /bets/:id} : delete the "id" bet.
     *
     * @param id the id of the betDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bets/{id}")
    public ResponseEntity<Void> deleteBet(@PathVariable Long id) {
        log.debug("REST request to delete Bet : {}", id);
        betService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
