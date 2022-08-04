package com.marlonramon.predicts.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.marlonramon.predicts.IntegrationTest;
import com.marlonramon.predicts.domain.Bet;
import com.marlonramon.predicts.repository.BetRepository;
import com.marlonramon.predicts.service.dto.BetDTO;
import com.marlonramon.predicts.service.mapper.BetMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link BetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BetResourceIT {

    private static final Instant DEFAULT_BET_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BET_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TOTAL_POINTS = 1;
    private static final Integer UPDATED_TOTAL_POINTS = 2;

    private static final Short DEFAULT_HOME_SCORE = 1;
    private static final Short UPDATED_HOME_SCORE = 2;

    private static final Short DEFAULT_VISITANT_SCORE = 1;
    private static final Short UPDATED_VISITANT_SCORE = 2;

    private static final String ENTITY_API_URL = "/api/bets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private BetMapper betMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBetMockMvc;

    private Bet bet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bet createEntity(EntityManager em) {
        Bet bet = new Bet()
            .betDate(DEFAULT_BET_DATE)
            .totalPoints(DEFAULT_TOTAL_POINTS)
            .homeScore(DEFAULT_HOME_SCORE)
            .visitantScore(DEFAULT_VISITANT_SCORE);
        return bet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bet createUpdatedEntity(EntityManager em) {
        Bet bet = new Bet()
            .betDate(UPDATED_BET_DATE)
            .totalPoints(UPDATED_TOTAL_POINTS)
            .homeScore(UPDATED_HOME_SCORE)
            .visitantScore(UPDATED_VISITANT_SCORE);
        return bet;
    }

    @BeforeEach
    public void initTest() {
        bet = createEntity(em);
    }

    @Test
    @Transactional
    void createBet() throws Exception {
        int databaseSizeBeforeCreate = betRepository.findAll().size();
        // Create the Bet
        BetDTO betDTO = betMapper.toDto(bet);
        restBetMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(betDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Bet in the database
        List<Bet> betList = betRepository.findAll();
        assertThat(betList).hasSize(databaseSizeBeforeCreate + 1);
        Bet testBet = betList.get(betList.size() - 1);
        assertThat(testBet.getBetDate()).isEqualTo(DEFAULT_BET_DATE);
        assertThat(testBet.getTotalPoints()).isEqualTo(DEFAULT_TOTAL_POINTS);
        assertThat(testBet.getHomeScore()).isEqualTo(DEFAULT_HOME_SCORE);
        assertThat(testBet.getVisitantScore()).isEqualTo(DEFAULT_VISITANT_SCORE);
    }

    @Test
    @Transactional
    void createBetWithExistingId() throws Exception {
        // Create the Bet with an existing ID
        bet.setId(1L);
        BetDTO betDTO = betMapper.toDto(bet);

        int databaseSizeBeforeCreate = betRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBetMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(betDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bet in the database
        List<Bet> betList = betRepository.findAll();
        assertThat(betList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBetDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = betRepository.findAll().size();
        // set the field null
        bet.setBetDate(null);

        // Create the Bet, which fails.
        BetDTO betDTO = betMapper.toDto(bet);

        restBetMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(betDTO))
            )
            .andExpect(status().isBadRequest());

        List<Bet> betList = betRepository.findAll();
        assertThat(betList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBets() throws Exception {
        // Initialize the database
        betRepository.saveAndFlush(bet);

        // Get all the betList
        restBetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bet.getId().intValue())))
            .andExpect(jsonPath("$.[*].betDate").value(hasItem(DEFAULT_BET_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalPoints").value(hasItem(DEFAULT_TOTAL_POINTS)))
            .andExpect(jsonPath("$.[*].homeScore").value(hasItem(DEFAULT_HOME_SCORE)))
            .andExpect(jsonPath("$.[*].visitantScore").value(hasItem(DEFAULT_VISITANT_SCORE)));
    }

    @Test
    @Transactional
    void getBet() throws Exception {
        // Initialize the database
        betRepository.saveAndFlush(bet);

        // Get the bet
        restBetMockMvc
            .perform(get(ENTITY_API_URL_ID, bet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bet.getId().intValue()))
            .andExpect(jsonPath("$.betDate").value(DEFAULT_BET_DATE.toString()))
            .andExpect(jsonPath("$.totalPoints").value(DEFAULT_TOTAL_POINTS))
            .andExpect(jsonPath("$.homeScore").value(DEFAULT_HOME_SCORE))
            .andExpect(jsonPath("$.visitantScore").value(DEFAULT_VISITANT_SCORE));
    }

    @Test
    @Transactional
    void getNonExistingBet() throws Exception {
        // Get the bet
        restBetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBet() throws Exception {
        // Initialize the database
        betRepository.saveAndFlush(bet);

        int databaseSizeBeforeUpdate = betRepository.findAll().size();

        // Update the bet
        Bet updatedBet = betRepository.findById(bet.getId()).get();
        // Disconnect from session so that the updates on updatedBet are not directly saved in db
        em.detach(updatedBet);
        updatedBet
            .betDate(UPDATED_BET_DATE)
            .totalPoints(UPDATED_TOTAL_POINTS)
            .homeScore(UPDATED_HOME_SCORE)
            .visitantScore(UPDATED_VISITANT_SCORE);
        BetDTO betDTO = betMapper.toDto(updatedBet);

        restBetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, betDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(betDTO))
            )
            .andExpect(status().isOk());

        // Validate the Bet in the database
        List<Bet> betList = betRepository.findAll();
        assertThat(betList).hasSize(databaseSizeBeforeUpdate);
        Bet testBet = betList.get(betList.size() - 1);
        assertThat(testBet.getBetDate()).isEqualTo(UPDATED_BET_DATE);
        assertThat(testBet.getTotalPoints()).isEqualTo(UPDATED_TOTAL_POINTS);
        assertThat(testBet.getHomeScore()).isEqualTo(UPDATED_HOME_SCORE);
        assertThat(testBet.getVisitantScore()).isEqualTo(UPDATED_VISITANT_SCORE);
    }

    @Test
    @Transactional
    void putNonExistingBet() throws Exception {
        int databaseSizeBeforeUpdate = betRepository.findAll().size();
        bet.setId(count.incrementAndGet());

        // Create the Bet
        BetDTO betDTO = betMapper.toDto(bet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, betDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(betDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bet in the database
        List<Bet> betList = betRepository.findAll();
        assertThat(betList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBet() throws Exception {
        int databaseSizeBeforeUpdate = betRepository.findAll().size();
        bet.setId(count.incrementAndGet());

        // Create the Bet
        BetDTO betDTO = betMapper.toDto(bet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(betDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bet in the database
        List<Bet> betList = betRepository.findAll();
        assertThat(betList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBet() throws Exception {
        int databaseSizeBeforeUpdate = betRepository.findAll().size();
        bet.setId(count.incrementAndGet());

        // Create the Bet
        BetDTO betDTO = betMapper.toDto(bet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(betDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bet in the database
        List<Bet> betList = betRepository.findAll();
        assertThat(betList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBetWithPatch() throws Exception {
        // Initialize the database
        betRepository.saveAndFlush(bet);

        int databaseSizeBeforeUpdate = betRepository.findAll().size();

        // Update the bet using partial update
        Bet partialUpdatedBet = new Bet();
        partialUpdatedBet.setId(bet.getId());

        partialUpdatedBet.betDate(UPDATED_BET_DATE).totalPoints(UPDATED_TOTAL_POINTS).homeScore(UPDATED_HOME_SCORE);

        restBetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBet.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBet))
            )
            .andExpect(status().isOk());

        // Validate the Bet in the database
        List<Bet> betList = betRepository.findAll();
        assertThat(betList).hasSize(databaseSizeBeforeUpdate);
        Bet testBet = betList.get(betList.size() - 1);
        assertThat(testBet.getBetDate()).isEqualTo(UPDATED_BET_DATE);
        assertThat(testBet.getTotalPoints()).isEqualTo(UPDATED_TOTAL_POINTS);
        assertThat(testBet.getHomeScore()).isEqualTo(UPDATED_HOME_SCORE);
        assertThat(testBet.getVisitantScore()).isEqualTo(DEFAULT_VISITANT_SCORE);
    }

    @Test
    @Transactional
    void fullUpdateBetWithPatch() throws Exception {
        // Initialize the database
        betRepository.saveAndFlush(bet);

        int databaseSizeBeforeUpdate = betRepository.findAll().size();

        // Update the bet using partial update
        Bet partialUpdatedBet = new Bet();
        partialUpdatedBet.setId(bet.getId());

        partialUpdatedBet
            .betDate(UPDATED_BET_DATE)
            .totalPoints(UPDATED_TOTAL_POINTS)
            .homeScore(UPDATED_HOME_SCORE)
            .visitantScore(UPDATED_VISITANT_SCORE);

        restBetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBet.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBet))
            )
            .andExpect(status().isOk());

        // Validate the Bet in the database
        List<Bet> betList = betRepository.findAll();
        assertThat(betList).hasSize(databaseSizeBeforeUpdate);
        Bet testBet = betList.get(betList.size() - 1);
        assertThat(testBet.getBetDate()).isEqualTo(UPDATED_BET_DATE);
        assertThat(testBet.getTotalPoints()).isEqualTo(UPDATED_TOTAL_POINTS);
        assertThat(testBet.getHomeScore()).isEqualTo(UPDATED_HOME_SCORE);
        assertThat(testBet.getVisitantScore()).isEqualTo(UPDATED_VISITANT_SCORE);
    }

    @Test
    @Transactional
    void patchNonExistingBet() throws Exception {
        int databaseSizeBeforeUpdate = betRepository.findAll().size();
        bet.setId(count.incrementAndGet());

        // Create the Bet
        BetDTO betDTO = betMapper.toDto(bet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, betDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(betDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bet in the database
        List<Bet> betList = betRepository.findAll();
        assertThat(betList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBet() throws Exception {
        int databaseSizeBeforeUpdate = betRepository.findAll().size();
        bet.setId(count.incrementAndGet());

        // Create the Bet
        BetDTO betDTO = betMapper.toDto(bet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(betDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bet in the database
        List<Bet> betList = betRepository.findAll();
        assertThat(betList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBet() throws Exception {
        int databaseSizeBeforeUpdate = betRepository.findAll().size();
        bet.setId(count.incrementAndGet());

        // Create the Bet
        BetDTO betDTO = betMapper.toDto(bet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(betDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bet in the database
        List<Bet> betList = betRepository.findAll();
        assertThat(betList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBet() throws Exception {
        // Initialize the database
        betRepository.saveAndFlush(bet);

        int databaseSizeBeforeDelete = betRepository.findAll().size();

        // Delete the bet
        restBetMockMvc
            .perform(delete(ENTITY_API_URL_ID, bet.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bet> betList = betRepository.findAll();
        assertThat(betList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
