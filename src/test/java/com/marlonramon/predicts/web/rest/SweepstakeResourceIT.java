package com.marlonramon.predicts.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.marlonramon.predicts.IntegrationTest;
import com.marlonramon.predicts.domain.Sweepstake;
import com.marlonramon.predicts.repository.SweepstakeRepository;
import com.marlonramon.predicts.service.SweepstakeService;
import com.marlonramon.predicts.service.dto.SweepstakeDTO;
import com.marlonramon.predicts.service.mapper.SweepstakeMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SweepstakeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SweepstakeResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_POINTS_FOR_TWO_SCORE = 1;
    private static final Integer UPDATED_POINTS_FOR_TWO_SCORE = 2;

    private static final Integer DEFAULT_POINTS_FOR_ONE_SCORE = 1;
    private static final Integer UPDATED_POINTS_FOR_ONE_SCORE = 2;

    private static final Integer DEFAULT_POINTS_FOR_ONLY_RESULT = 1;
    private static final Integer UPDATED_POINTS_FOR_ONLY_RESULT = 2;

    private static final String ENTITY_API_URL = "/api/sweepstakes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SweepstakeRepository sweepstakeRepository;

    @Mock
    private SweepstakeRepository sweepstakeRepositoryMock;

    @Autowired
    private SweepstakeMapper sweepstakeMapper;

    @Mock
    private SweepstakeService sweepstakeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSweepstakeMockMvc;

    private Sweepstake sweepstake;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sweepstake createEntity(EntityManager em) {
        Sweepstake sweepstake = new Sweepstake()
            .description(DEFAULT_DESCRIPTION)
            .pointsForTwoScore(DEFAULT_POINTS_FOR_TWO_SCORE)
            .pointsForOneScore(DEFAULT_POINTS_FOR_ONE_SCORE)
            .pointsForOnlyResult(DEFAULT_POINTS_FOR_ONLY_RESULT);
        return sweepstake;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sweepstake createUpdatedEntity(EntityManager em) {
        Sweepstake sweepstake = new Sweepstake()
            .description(UPDATED_DESCRIPTION)
            .pointsForTwoScore(UPDATED_POINTS_FOR_TWO_SCORE)
            .pointsForOneScore(UPDATED_POINTS_FOR_ONE_SCORE)
            .pointsForOnlyResult(UPDATED_POINTS_FOR_ONLY_RESULT);
        return sweepstake;
    }

    @BeforeEach
    public void initTest() {
        sweepstake = createEntity(em);
    }

    @Test
    @Transactional
    void createSweepstake() throws Exception {
        int databaseSizeBeforeCreate = sweepstakeRepository.findAll().size();
        // Create the Sweepstake
        SweepstakeDTO sweepstakeDTO = sweepstakeMapper.toDto(sweepstake);
        restSweepstakeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sweepstakeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Sweepstake in the database
        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeCreate + 1);
        Sweepstake testSweepstake = sweepstakeList.get(sweepstakeList.size() - 1);
        assertThat(testSweepstake.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSweepstake.getPointsForTwoScore()).isEqualTo(DEFAULT_POINTS_FOR_TWO_SCORE);
        assertThat(testSweepstake.getPointsForOneScore()).isEqualTo(DEFAULT_POINTS_FOR_ONE_SCORE);
        assertThat(testSweepstake.getPointsForOnlyResult()).isEqualTo(DEFAULT_POINTS_FOR_ONLY_RESULT);
    }

    @Test
    @Transactional
    void createSweepstakeWithExistingId() throws Exception {
        // Create the Sweepstake with an existing ID
        sweepstake.setId(1L);
        SweepstakeDTO sweepstakeDTO = sweepstakeMapper.toDto(sweepstake);

        int databaseSizeBeforeCreate = sweepstakeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSweepstakeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sweepstakeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sweepstake in the database
        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sweepstakeRepository.findAll().size();
        // set the field null
        sweepstake.setDescription(null);

        // Create the Sweepstake, which fails.
        SweepstakeDTO sweepstakeDTO = sweepstakeMapper.toDto(sweepstake);

        restSweepstakeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sweepstakeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPointsForTwoScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = sweepstakeRepository.findAll().size();
        // set the field null
        sweepstake.setPointsForTwoScore(null);

        // Create the Sweepstake, which fails.
        SweepstakeDTO sweepstakeDTO = sweepstakeMapper.toDto(sweepstake);

        restSweepstakeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sweepstakeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPointsForOneScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = sweepstakeRepository.findAll().size();
        // set the field null
        sweepstake.setPointsForOneScore(null);

        // Create the Sweepstake, which fails.
        SweepstakeDTO sweepstakeDTO = sweepstakeMapper.toDto(sweepstake);

        restSweepstakeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sweepstakeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPointsForOnlyResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = sweepstakeRepository.findAll().size();
        // set the field null
        sweepstake.setPointsForOnlyResult(null);

        // Create the Sweepstake, which fails.
        SweepstakeDTO sweepstakeDTO = sweepstakeMapper.toDto(sweepstake);

        restSweepstakeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sweepstakeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSweepstakes() throws Exception {
        // Initialize the database
        sweepstakeRepository.saveAndFlush(sweepstake);

        // Get all the sweepstakeList
        restSweepstakeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sweepstake.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].pointsForTwoScore").value(hasItem(DEFAULT_POINTS_FOR_TWO_SCORE)))
            .andExpect(jsonPath("$.[*].pointsForOneScore").value(hasItem(DEFAULT_POINTS_FOR_ONE_SCORE)))
            .andExpect(jsonPath("$.[*].pointsForOnlyResult").value(hasItem(DEFAULT_POINTS_FOR_ONLY_RESULT)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSweepstakesWithEagerRelationshipsIsEnabled() throws Exception {
        when(sweepstakeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSweepstakeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(sweepstakeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSweepstakesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(sweepstakeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSweepstakeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(sweepstakeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSweepstake() throws Exception {
        // Initialize the database
        sweepstakeRepository.saveAndFlush(sweepstake);

        // Get the sweepstake
        restSweepstakeMockMvc
            .perform(get(ENTITY_API_URL_ID, sweepstake.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sweepstake.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.pointsForTwoScore").value(DEFAULT_POINTS_FOR_TWO_SCORE))
            .andExpect(jsonPath("$.pointsForOneScore").value(DEFAULT_POINTS_FOR_ONE_SCORE))
            .andExpect(jsonPath("$.pointsForOnlyResult").value(DEFAULT_POINTS_FOR_ONLY_RESULT));
    }

    @Test
    @Transactional
    void getNonExistingSweepstake() throws Exception {
        // Get the sweepstake
        restSweepstakeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSweepstake() throws Exception {
        // Initialize the database
        sweepstakeRepository.saveAndFlush(sweepstake);

        int databaseSizeBeforeUpdate = sweepstakeRepository.findAll().size();

        // Update the sweepstake
        Sweepstake updatedSweepstake = sweepstakeRepository.findById(sweepstake.getId()).get();
        // Disconnect from session so that the updates on updatedSweepstake are not directly saved in db
        em.detach(updatedSweepstake);
        updatedSweepstake
            .description(UPDATED_DESCRIPTION)
            .pointsForTwoScore(UPDATED_POINTS_FOR_TWO_SCORE)
            .pointsForOneScore(UPDATED_POINTS_FOR_ONE_SCORE)
            .pointsForOnlyResult(UPDATED_POINTS_FOR_ONLY_RESULT);
        SweepstakeDTO sweepstakeDTO = sweepstakeMapper.toDto(updatedSweepstake);

        restSweepstakeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sweepstakeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sweepstakeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Sweepstake in the database
        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeUpdate);
        Sweepstake testSweepstake = sweepstakeList.get(sweepstakeList.size() - 1);
        assertThat(testSweepstake.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSweepstake.getPointsForTwoScore()).isEqualTo(UPDATED_POINTS_FOR_TWO_SCORE);
        assertThat(testSweepstake.getPointsForOneScore()).isEqualTo(UPDATED_POINTS_FOR_ONE_SCORE);
        assertThat(testSweepstake.getPointsForOnlyResult()).isEqualTo(UPDATED_POINTS_FOR_ONLY_RESULT);
    }

    @Test
    @Transactional
    void putNonExistingSweepstake() throws Exception {
        int databaseSizeBeforeUpdate = sweepstakeRepository.findAll().size();
        sweepstake.setId(count.incrementAndGet());

        // Create the Sweepstake
        SweepstakeDTO sweepstakeDTO = sweepstakeMapper.toDto(sweepstake);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSweepstakeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sweepstakeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sweepstakeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sweepstake in the database
        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSweepstake() throws Exception {
        int databaseSizeBeforeUpdate = sweepstakeRepository.findAll().size();
        sweepstake.setId(count.incrementAndGet());

        // Create the Sweepstake
        SweepstakeDTO sweepstakeDTO = sweepstakeMapper.toDto(sweepstake);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSweepstakeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sweepstakeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sweepstake in the database
        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSweepstake() throws Exception {
        int databaseSizeBeforeUpdate = sweepstakeRepository.findAll().size();
        sweepstake.setId(count.incrementAndGet());

        // Create the Sweepstake
        SweepstakeDTO sweepstakeDTO = sweepstakeMapper.toDto(sweepstake);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSweepstakeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sweepstakeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sweepstake in the database
        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSweepstakeWithPatch() throws Exception {
        // Initialize the database
        sweepstakeRepository.saveAndFlush(sweepstake);

        int databaseSizeBeforeUpdate = sweepstakeRepository.findAll().size();

        // Update the sweepstake using partial update
        Sweepstake partialUpdatedSweepstake = new Sweepstake();
        partialUpdatedSweepstake.setId(sweepstake.getId());

        partialUpdatedSweepstake.pointsForTwoScore(UPDATED_POINTS_FOR_TWO_SCORE);

        restSweepstakeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSweepstake.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSweepstake))
            )
            .andExpect(status().isOk());

        // Validate the Sweepstake in the database
        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeUpdate);
        Sweepstake testSweepstake = sweepstakeList.get(sweepstakeList.size() - 1);
        assertThat(testSweepstake.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSweepstake.getPointsForTwoScore()).isEqualTo(UPDATED_POINTS_FOR_TWO_SCORE);
        assertThat(testSweepstake.getPointsForOneScore()).isEqualTo(DEFAULT_POINTS_FOR_ONE_SCORE);
        assertThat(testSweepstake.getPointsForOnlyResult()).isEqualTo(DEFAULT_POINTS_FOR_ONLY_RESULT);
    }

    @Test
    @Transactional
    void fullUpdateSweepstakeWithPatch() throws Exception {
        // Initialize the database
        sweepstakeRepository.saveAndFlush(sweepstake);

        int databaseSizeBeforeUpdate = sweepstakeRepository.findAll().size();

        // Update the sweepstake using partial update
        Sweepstake partialUpdatedSweepstake = new Sweepstake();
        partialUpdatedSweepstake.setId(sweepstake.getId());

        partialUpdatedSweepstake
            .description(UPDATED_DESCRIPTION)
            .pointsForTwoScore(UPDATED_POINTS_FOR_TWO_SCORE)
            .pointsForOneScore(UPDATED_POINTS_FOR_ONE_SCORE)
            .pointsForOnlyResult(UPDATED_POINTS_FOR_ONLY_RESULT);

        restSweepstakeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSweepstake.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSweepstake))
            )
            .andExpect(status().isOk());

        // Validate the Sweepstake in the database
        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeUpdate);
        Sweepstake testSweepstake = sweepstakeList.get(sweepstakeList.size() - 1);
        assertThat(testSweepstake.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSweepstake.getPointsForTwoScore()).isEqualTo(UPDATED_POINTS_FOR_TWO_SCORE);
        assertThat(testSweepstake.getPointsForOneScore()).isEqualTo(UPDATED_POINTS_FOR_ONE_SCORE);
        assertThat(testSweepstake.getPointsForOnlyResult()).isEqualTo(UPDATED_POINTS_FOR_ONLY_RESULT);
    }

    @Test
    @Transactional
    void patchNonExistingSweepstake() throws Exception {
        int databaseSizeBeforeUpdate = sweepstakeRepository.findAll().size();
        sweepstake.setId(count.incrementAndGet());

        // Create the Sweepstake
        SweepstakeDTO sweepstakeDTO = sweepstakeMapper.toDto(sweepstake);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSweepstakeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sweepstakeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sweepstakeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sweepstake in the database
        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSweepstake() throws Exception {
        int databaseSizeBeforeUpdate = sweepstakeRepository.findAll().size();
        sweepstake.setId(count.incrementAndGet());

        // Create the Sweepstake
        SweepstakeDTO sweepstakeDTO = sweepstakeMapper.toDto(sweepstake);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSweepstakeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sweepstakeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sweepstake in the database
        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSweepstake() throws Exception {
        int databaseSizeBeforeUpdate = sweepstakeRepository.findAll().size();
        sweepstake.setId(count.incrementAndGet());

        // Create the Sweepstake
        SweepstakeDTO sweepstakeDTO = sweepstakeMapper.toDto(sweepstake);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSweepstakeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sweepstakeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sweepstake in the database
        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSweepstake() throws Exception {
        // Initialize the database
        sweepstakeRepository.saveAndFlush(sweepstake);

        int databaseSizeBeforeDelete = sweepstakeRepository.findAll().size();

        // Delete the sweepstake
        restSweepstakeMockMvc
            .perform(delete(ENTITY_API_URL_ID, sweepstake.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sweepstake> sweepstakeList = sweepstakeRepository.findAll();
        assertThat(sweepstakeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
