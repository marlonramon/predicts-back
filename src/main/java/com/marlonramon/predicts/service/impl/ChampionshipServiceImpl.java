package com.marlonramon.predicts.service.impl;

import com.marlonramon.predicts.domain.Championship;
import com.marlonramon.predicts.repository.ChampionshipRepository;
import com.marlonramon.predicts.service.ChampionshipService;
import com.marlonramon.predicts.service.dto.ChampionshipDTO;
import com.marlonramon.predicts.service.mapper.ChampionshipMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Championship}.
 */
@Service
@Transactional
public class ChampionshipServiceImpl implements ChampionshipService {

    private final Logger log = LoggerFactory.getLogger(ChampionshipServiceImpl.class);

    private final ChampionshipRepository championshipRepository;

    private final ChampionshipMapper championshipMapper;

    public ChampionshipServiceImpl(ChampionshipRepository championshipRepository, ChampionshipMapper championshipMapper) {
        this.championshipRepository = championshipRepository;
        this.championshipMapper = championshipMapper;
    }

    @Override
    public ChampionshipDTO save(ChampionshipDTO championshipDTO) {
        log.debug("Request to save Championship : {}", championshipDTO);
        Championship championship = championshipMapper.toEntity(championshipDTO);
        championship = championshipRepository.save(championship);
        return championshipMapper.toDto(championship);
    }

    @Override
    public ChampionshipDTO update(ChampionshipDTO championshipDTO) {
        log.debug("Request to save Championship : {}", championshipDTO);
        Championship championship = championshipMapper.toEntity(championshipDTO);
        championship = championshipRepository.save(championship);
        return championshipMapper.toDto(championship);
    }

    @Override
    public Optional<ChampionshipDTO> partialUpdate(ChampionshipDTO championshipDTO) {
        log.debug("Request to partially update Championship : {}", championshipDTO);

        return championshipRepository
            .findById(championshipDTO.getId())
            .map(existingChampionship -> {
                championshipMapper.partialUpdate(existingChampionship, championshipDTO);

                return existingChampionship;
            })
            .map(championshipRepository::save)
            .map(championshipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChampionshipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Championships");
        return championshipRepository.findAll(pageable).map(championshipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChampionshipDTO> findOne(Long id) {
        log.debug("Request to get Championship : {}", id);
        return championshipRepository.findById(id).map(championshipMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Championship : {}", id);
        championshipRepository.deleteById(id);
    }
}
