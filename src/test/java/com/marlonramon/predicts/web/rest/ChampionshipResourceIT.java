package com.marlonramon.predicts.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.marlonramon.predicts.IntegrationTest;
import com.marlonramon.predicts.domain.Championship;
import com.marlonramon.predicts.repository.ChampionshipRepository;
import com.marlonramon.predicts.service.dto.ChampionshipDTO;
import com.marlonramon.predicts.service.mapper.ChampionshipMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ChampionshipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChampionshipResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/championships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChampionshipRepository championshipRepository;

    @Autowired
    private ChampionshipMapper championshipMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChampionshipMockMvc;

    private Championship championship;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Championship createEntity(EntityManager em) {
        Championship championship = new Championship()
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return championship;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Championship createUpdatedEntity(EntityManager em) {
        Championship championship = new Championship()
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return championship;
    }

    @BeforeEach
    public void initTest() {
        championship = createEntity(em);
    }

    @Test
    @Transactional
    void createChampionship() throws Exception {
        int databaseSizeBeforeCreate = championshipRepository.findAll().size();
        // Create the Championship
        ChampionshipDTO championshipDTO = championshipMapper.toDto(championship);
        restChampionshipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(championshipDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Championship in the database
        List<Championship> championshipList = championshipRepository.findAll();
        assertThat(championshipList).hasSize(databaseSizeBeforeCreate + 1);
        Championship testChampionship = championshipList.get(championshipList.size() - 1);
        assertThat(testChampionship.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testChampionship.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testChampionship.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    void createChampionshipWithExistingId() throws Exception {
        // Create the Championship with an existing ID
        championship.setId(1L);
        ChampionshipDTO championshipDTO = championshipMapper.toDto(championship);

        int databaseSizeBeforeCreate = championshipRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChampionshipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(championshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Championship in the database
        List<Championship> championshipList = championshipRepository.findAll();
        assertThat(championshipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = championshipRepository.findAll().size();
        // set the field null
        championship.setDescription(null);

        // Create the Championship, which fails.
        ChampionshipDTO championshipDTO = championshipMapper.toDto(championship);

        restChampionshipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(championshipDTO))
            )
            .andExpect(status().isBadRequest());

        List<Championship> championshipList = championshipRepository.findAll();
        assertThat(championshipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = championshipRepository.findAll().size();
        // set the field null
        championship.setStartDate(null);

        // Create the Championship, which fails.
        ChampionshipDTO championshipDTO = championshipMapper.toDto(championship);

        restChampionshipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(championshipDTO))
            )
            .andExpect(status().isBadRequest());

        List<Championship> championshipList = championshipRepository.findAll();
        assertThat(championshipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = championshipRepository.findAll().size();
        // set the field null
        championship.setEndDate(null);

        // Create the Championship, which fails.
        ChampionshipDTO championshipDTO = championshipMapper.toDto(championship);

        restChampionshipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(championshipDTO))
            )
            .andExpect(status().isBadRequest());

        List<Championship> championshipList = championshipRepository.findAll();
        assertThat(championshipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChampionships() throws Exception {
        // Initialize the database
        championshipRepository.saveAndFlush(championship);

        // Get all the championshipList
        restChampionshipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(championship.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    void getChampionship() throws Exception {
        // Initialize the database
        championshipRepository.saveAndFlush(championship);

        // Get the championship
        restChampionshipMockMvc
            .perform(get(ENTITY_API_URL_ID, championship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(championship.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingChampionship() throws Exception {
        // Get the championship
        restChampionshipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewChampionship() throws Exception {
        // Initialize the database
        championshipRepository.saveAndFlush(championship);

        int databaseSizeBeforeUpdate = championshipRepository.findAll().size();

        // Update the championship
        Championship updatedChampionship = championshipRepository.findById(championship.getId()).get();
        // Disconnect from session so that the updates on updatedChampionship are not directly saved in db
        em.detach(updatedChampionship);
        updatedChampionship.description(UPDATED_DESCRIPTION).startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE);
        ChampionshipDTO championshipDTO = championshipMapper.toDto(updatedChampionship);

        restChampionshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, championshipDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(championshipDTO))
            )
            .andExpect(status().isOk());

        // Validate the Championship in the database
        List<Championship> championshipList = championshipRepository.findAll();
        assertThat(championshipList).hasSize(databaseSizeBeforeUpdate);
        Championship testChampionship = championshipList.get(championshipList.size() - 1);
        assertThat(testChampionship.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testChampionship.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testChampionship.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void putNonExistingChampionship() throws Exception {
        int databaseSizeBeforeUpdate = championshipRepository.findAll().size();
        championship.setId(count.incrementAndGet());

        // Create the Championship
        ChampionshipDTO championshipDTO = championshipMapper.toDto(championship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChampionshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, championshipDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(championshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Championship in the database
        List<Championship> championshipList = championshipRepository.findAll();
        assertThat(championshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChampionship() throws Exception {
        int databaseSizeBeforeUpdate = championshipRepository.findAll().size();
        championship.setId(count.incrementAndGet());

        // Create the Championship
        ChampionshipDTO championshipDTO = championshipMapper.toDto(championship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChampionshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(championshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Championship in the database
        List<Championship> championshipList = championshipRepository.findAll();
        assertThat(championshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChampionship() throws Exception {
        int databaseSizeBeforeUpdate = championshipRepository.findAll().size();
        championship.setId(count.incrementAndGet());

        // Create the Championship
        ChampionshipDTO championshipDTO = championshipMapper.toDto(championship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChampionshipMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(championshipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Championship in the database
        List<Championship> championshipList = championshipRepository.findAll();
        assertThat(championshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChampionshipWithPatch() throws Exception {
        // Initialize the database
        championshipRepository.saveAndFlush(championship);

        int databaseSizeBeforeUpdate = championshipRepository.findAll().size();

        // Update the championship using partial update
        Championship partialUpdatedChampionship = new Championship();
        partialUpdatedChampionship.setId(championship.getId());

        partialUpdatedChampionship.description(UPDATED_DESCRIPTION).startDate(UPDATED_START_DATE);

        restChampionshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChampionship.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChampionship))
            )
            .andExpect(status().isOk());

        // Validate the Championship in the database
        List<Championship> championshipList = championshipRepository.findAll();
        assertThat(championshipList).hasSize(databaseSizeBeforeUpdate);
        Championship testChampionship = championshipList.get(championshipList.size() - 1);
        assertThat(testChampionship.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testChampionship.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testChampionship.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    void fullUpdateChampionshipWithPatch() throws Exception {
        // Initialize the database
        championshipRepository.saveAndFlush(championship);

        int databaseSizeBeforeUpdate = championshipRepository.findAll().size();

        // Update the championship using partial update
        Championship partialUpdatedChampionship = new Championship();
        partialUpdatedChampionship.setId(championship.getId());

        partialUpdatedChampionship.description(UPDATED_DESCRIPTION).startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE);

        restChampionshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChampionship.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChampionship))
            )
            .andExpect(status().isOk());

        // Validate the Championship in the database
        List<Championship> championshipList = championshipRepository.findAll();
        assertThat(championshipList).hasSize(databaseSizeBeforeUpdate);
        Championship testChampionship = championshipList.get(championshipList.size() - 1);
        assertThat(testChampionship.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testChampionship.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testChampionship.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingChampionship() throws Exception {
        int databaseSizeBeforeUpdate = championshipRepository.findAll().size();
        championship.setId(count.incrementAndGet());

        // Create the Championship
        ChampionshipDTO championshipDTO = championshipMapper.toDto(championship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChampionshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, championshipDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(championshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Championship in the database
        List<Championship> championshipList = championshipRepository.findAll();
        assertThat(championshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChampionship() throws Exception {
        int databaseSizeBeforeUpdate = championshipRepository.findAll().size();
        championship.setId(count.incrementAndGet());

        // Create the Championship
        ChampionshipDTO championshipDTO = championshipMapper.toDto(championship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChampionshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(championshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Championship in the database
        List<Championship> championshipList = championshipRepository.findAll();
        assertThat(championshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChampionship() throws Exception {
        int databaseSizeBeforeUpdate = championshipRepository.findAll().size();
        championship.setId(count.incrementAndGet());

        // Create the Championship
        ChampionshipDTO championshipDTO = championshipMapper.toDto(championship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChampionshipMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(championshipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Championship in the database
        List<Championship> championshipList = championshipRepository.findAll();
        assertThat(championshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChampionship() throws Exception {
        // Initialize the database
        championshipRepository.saveAndFlush(championship);

        int databaseSizeBeforeDelete = championshipRepository.findAll().size();

        // Delete the championship
        restChampionshipMockMvc
            .perform(delete(ENTITY_API_URL_ID, championship.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Championship> championshipList = championshipRepository.findAll();
        assertThat(championshipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
