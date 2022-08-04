package com.marlonramon.predicts.service.impl;

import com.marlonramon.predicts.domain.Sweepstake;
import com.marlonramon.predicts.repository.SweepstakeRepository;
import com.marlonramon.predicts.service.SweepstakeService;
import com.marlonramon.predicts.service.dto.SweepstakeDTO;
import com.marlonramon.predicts.service.mapper.SweepstakeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sweepstake}.
 */
@Service
@Transactional
public class SweepstakeServiceImpl implements SweepstakeService {

    private final Logger log = LoggerFactory.getLogger(SweepstakeServiceImpl.class);

    private final SweepstakeRepository sweepstakeRepository;

    private final SweepstakeMapper sweepstakeMapper;

    public SweepstakeServiceImpl(SweepstakeRepository sweepstakeRepository, SweepstakeMapper sweepstakeMapper) {
        this.sweepstakeRepository = sweepstakeRepository;
        this.sweepstakeMapper = sweepstakeMapper;
    }

    @Override
    public SweepstakeDTO save(SweepstakeDTO sweepstakeDTO) {
        log.debug("Request to save Sweepstake : {}", sweepstakeDTO);
        Sweepstake sweepstake = sweepstakeMapper.toEntity(sweepstakeDTO);
        sweepstake = sweepstakeRepository.save(sweepstake);
        return sweepstakeMapper.toDto(sweepstake);
    }

    @Override
    public SweepstakeDTO update(SweepstakeDTO sweepstakeDTO) {
        log.debug("Request to save Sweepstake : {}", sweepstakeDTO);
        Sweepstake sweepstake = sweepstakeMapper.toEntity(sweepstakeDTO);
        sweepstake = sweepstakeRepository.save(sweepstake);
        return sweepstakeMapper.toDto(sweepstake);
    }

    @Override
    public Optional<SweepstakeDTO> partialUpdate(SweepstakeDTO sweepstakeDTO) {
        log.debug("Request to partially update Sweepstake : {}", sweepstakeDTO);

        return sweepstakeRepository
            .findById(sweepstakeDTO.getId())
            .map(existingSweepstake -> {
                sweepstakeMapper.partialUpdate(existingSweepstake, sweepstakeDTO);

                return existingSweepstake;
            })
            .map(sweepstakeRepository::save)
            .map(sweepstakeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SweepstakeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sweepstakes");
        return sweepstakeRepository.findAll(pageable).map(sweepstakeMapper::toDto);
    }

    public Page<SweepstakeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return sweepstakeRepository.findAllWithEagerRelationships(pageable).map(sweepstakeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SweepstakeDTO> findOne(Long id) {
        log.debug("Request to get Sweepstake : {}", id);
        return sweepstakeRepository.findOneWithEagerRelationships(id).map(sweepstakeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sweepstake : {}", id);
        sweepstakeRepository.deleteById(id);
    }
}
