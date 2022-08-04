package com.marlonramon.predicts.service.impl;

import com.marlonramon.predicts.domain.Round;
import com.marlonramon.predicts.repository.RoundRepository;
import com.marlonramon.predicts.service.RoundService;
import com.marlonramon.predicts.service.dto.RoundDTO;
import com.marlonramon.predicts.service.mapper.RoundMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Round}.
 */
@Service
@Transactional
public class RoundServiceImpl implements RoundService {

    private final Logger log = LoggerFactory.getLogger(RoundServiceImpl.class);

    private final RoundRepository roundRepository;

    private final RoundMapper roundMapper;

    public RoundServiceImpl(RoundRepository roundRepository, RoundMapper roundMapper) {
        this.roundRepository = roundRepository;
        this.roundMapper = roundMapper;
    }

    @Override
    public RoundDTO save(RoundDTO roundDTO) {
        log.debug("Request to save Round : {}", roundDTO);
        Round round = roundMapper.toEntity(roundDTO);
        round = roundRepository.save(round);
        return roundMapper.toDto(round);
    }

    @Override
    public RoundDTO update(RoundDTO roundDTO) {
        log.debug("Request to save Round : {}", roundDTO);
        Round round = roundMapper.toEntity(roundDTO);
        round = roundRepository.save(round);
        return roundMapper.toDto(round);
    }

    @Override
    public Optional<RoundDTO> partialUpdate(RoundDTO roundDTO) {
        log.debug("Request to partially update Round : {}", roundDTO);

        return roundRepository
            .findById(roundDTO.getId())
            .map(existingRound -> {
                roundMapper.partialUpdate(existingRound, roundDTO);

                return existingRound;
            })
            .map(roundRepository::save)
            .map(roundMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoundDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rounds");
        return roundRepository.findAll(pageable).map(roundMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoundDTO> findOne(Long id) {
        log.debug("Request to get Round : {}", id);
        return roundRepository.findById(id).map(roundMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Round : {}", id);
        roundRepository.deleteById(id);
    }
}
