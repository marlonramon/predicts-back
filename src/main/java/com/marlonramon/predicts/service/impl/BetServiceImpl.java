package com.marlonramon.predicts.service.impl;

import com.marlonramon.predicts.domain.Bet;
import com.marlonramon.predicts.repository.BetRepository;
import com.marlonramon.predicts.service.BetService;
import com.marlonramon.predicts.service.dto.BetDTO;
import com.marlonramon.predicts.service.mapper.BetMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Bet}.
 */
@Service
@Transactional
public class BetServiceImpl implements BetService {

    private final Logger log = LoggerFactory.getLogger(BetServiceImpl.class);

    private final BetRepository betRepository;

    private final BetMapper betMapper;

    public BetServiceImpl(BetRepository betRepository, BetMapper betMapper) {
        this.betRepository = betRepository;
        this.betMapper = betMapper;
    }

    @Override
    public BetDTO save(BetDTO betDTO) {
        log.debug("Request to save Bet : {}", betDTO);
        Bet bet = betMapper.toEntity(betDTO);
        bet = betRepository.save(bet);
        return betMapper.toDto(bet);
    }

    @Override
    public BetDTO update(BetDTO betDTO) {
        log.debug("Request to save Bet : {}", betDTO);
        Bet bet = betMapper.toEntity(betDTO);
        bet = betRepository.save(bet);
        return betMapper.toDto(bet);
    }

    @Override
    public Optional<BetDTO> partialUpdate(BetDTO betDTO) {
        log.debug("Request to partially update Bet : {}", betDTO);

        return betRepository
            .findById(betDTO.getId())
            .map(existingBet -> {
                betMapper.partialUpdate(existingBet, betDTO);

                return existingBet;
            })
            .map(betRepository::save)
            .map(betMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bets");
        return betRepository.findAll(pageable).map(betMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BetDTO> findOne(Long id) {
        log.debug("Request to get Bet : {}", id);
        return betRepository.findById(id).map(betMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bet : {}", id);
        betRepository.deleteById(id);
    }
}
