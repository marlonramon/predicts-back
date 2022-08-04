package com.marlonramon.predicts.web.rest;

import com.marlonramon.predicts.repository.SweepstakeRepository;
import com.marlonramon.predicts.service.SweepstakeService;
import com.marlonramon.predicts.service.dto.SweepstakeDTO;
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
 * REST controller for managing {@link com.marlonramon.predicts.domain.Sweepstake}.
 */
@RestController
@RequestMapping("/api")
public class SweepstakeResource {

    private final Logger log = LoggerFactory.getLogger(SweepstakeResource.class);

    private static final String ENTITY_NAME = "sweepstake";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SweepstakeService sweepstakeService;

    private final SweepstakeRepository sweepstakeRepository;

    public SweepstakeResource(SweepstakeService sweepstakeService, SweepstakeRepository sweepstakeRepository) {
        this.sweepstakeService = sweepstakeService;
        this.sweepstakeRepository = sweepstakeRepository;
    }

    /**
     * {@code POST  /sweepstakes} : Create a new sweepstake.
     *
     * @param sweepstakeDTO the sweepstakeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sweepstakeDTO, or with status {@code 400 (Bad Request)} if the sweepstake has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sweepstakes")
    public ResponseEntity<SweepstakeDTO> createSweepstake(@Valid @RequestBody SweepstakeDTO sweepstakeDTO) throws URISyntaxException {
        log.debug("REST request to save Sweepstake : {}", sweepstakeDTO);
        if (sweepstakeDTO.getId() != null) {
            throw new BadRequestAlertException("A new sweepstake cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SweepstakeDTO result = sweepstakeService.save(sweepstakeDTO);
        return ResponseEntity
            .created(new URI("/api/sweepstakes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sweepstakes/:id} : Updates an existing sweepstake.
     *
     * @param id the id of the sweepstakeDTO to save.
     * @param sweepstakeDTO the sweepstakeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sweepstakeDTO,
     * or with status {@code 400 (Bad Request)} if the sweepstakeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sweepstakeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sweepstakes/{id}")
    public ResponseEntity<SweepstakeDTO> updateSweepstake(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SweepstakeDTO sweepstakeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Sweepstake : {}, {}", id, sweepstakeDTO);
        if (sweepstakeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sweepstakeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sweepstakeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SweepstakeDTO result = sweepstakeService.update(sweepstakeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sweepstakeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sweepstakes/:id} : Partial updates given fields of an existing sweepstake, field will ignore if it is null
     *
     * @param id the id of the sweepstakeDTO to save.
     * @param sweepstakeDTO the sweepstakeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sweepstakeDTO,
     * or with status {@code 400 (Bad Request)} if the sweepstakeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sweepstakeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sweepstakeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sweepstakes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SweepstakeDTO> partialUpdateSweepstake(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SweepstakeDTO sweepstakeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sweepstake partially : {}, {}", id, sweepstakeDTO);
        if (sweepstakeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sweepstakeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sweepstakeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SweepstakeDTO> result = sweepstakeService.partialUpdate(sweepstakeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sweepstakeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sweepstakes} : get all the sweepstakes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sweepstakes in body.
     */
    @GetMapping("/sweepstakes")
    public ResponseEntity<List<SweepstakeDTO>> getAllSweepstakes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Sweepstakes");
        Page<SweepstakeDTO> page;
        if (eagerload) {
            page = sweepstakeService.findAllWithEagerRelationships(pageable);
        } else {
            page = sweepstakeService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sweepstakes/:id} : get the "id" sweepstake.
     *
     * @param id the id of the sweepstakeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sweepstakeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sweepstakes/{id}")
    public ResponseEntity<SweepstakeDTO> getSweepstake(@PathVariable Long id) {
        log.debug("REST request to get Sweepstake : {}", id);
        Optional<SweepstakeDTO> sweepstakeDTO = sweepstakeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sweepstakeDTO);
    }

    /**
     * {@code DELETE  /sweepstakes/:id} : delete the "id" sweepstake.
     *
     * @param id the id of the sweepstakeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sweepstakes/{id}")
    public ResponseEntity<Void> deleteSweepstake(@PathVariable Long id) {
        log.debug("REST request to delete Sweepstake : {}", id);
        sweepstakeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
