package com.qaryati.service.impl;

import com.qaryati.dto.HistoricalEventRequestDto;
import com.qaryati.dto.HistoricalEventResponseDto;
import com.qaryati.entity.HistoricalEvent;
import com.qaryati.entity.Village;
import com.qaryati.exception.ResourceNotFoundException;
import com.qaryati.mapper.HistoricalEventMapper;
import com.qaryati.repository.HistoricalEventRepository;
import com.qaryati.repository.VillageRepository;
import com.qaryati.service.HistoricalEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricalEventServiceImpl implements HistoricalEventService {

    private final HistoricalEventRepository historicalEventRepository;
    private final VillageRepository villageRepository;
    private final HistoricalEventMapper historicalEventMapper;

    @Override
    @Transactional
    public HistoricalEventResponseDto addHistoricalEvent(HistoricalEventRequestDto requestDto) {
        Village village = findVillage(requestDto.getVillageId());
        HistoricalEvent event = historicalEventMapper.toEntity(requestDto, village);
        return historicalEventMapper.toDto(historicalEventRepository.save(event));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoricalEventResponseDto> getEventsByVillageId(Long villageId) {
        findVillage(villageId);
        return historicalEventRepository.findByVillageIdOrderByEventYearAsc(villageId)
                .stream()
                .map(historicalEventMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public HistoricalEventResponseDto updateHistoricalEvent(Long id, HistoricalEventRequestDto requestDto) {
        HistoricalEvent event = historicalEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historical event not found with id: " + id));
        Village village = findVillage(requestDto.getVillageId());
        historicalEventMapper.updateEntityFromDto(requestDto, event, village);
        return historicalEventMapper.toDto(historicalEventRepository.save(event));
    }

    @Override
    @Transactional
    public void deleteHistoricalEvent(Long id) {
        HistoricalEvent event = historicalEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historical event not found with id: " + id));
        historicalEventRepository.delete(event);
    }

    private Village findVillage(Long villageId) {
        return villageRepository.findById(villageId)
                .orElseThrow(() -> new ResourceNotFoundException("Village not found with id: " + villageId));
    }
}
