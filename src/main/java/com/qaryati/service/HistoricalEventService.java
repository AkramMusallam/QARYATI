package com.qaryati.service;

import com.qaryati.dto.HistoricalEventRequestDto;
import com.qaryati.dto.HistoricalEventResponseDto;

import java.util.List;

public interface HistoricalEventService {
    HistoricalEventResponseDto addHistoricalEvent(HistoricalEventRequestDto requestDto);
    List<HistoricalEventResponseDto> getEventsByVillageId(Long villageId);
    HistoricalEventResponseDto updateHistoricalEvent(Long id, HistoricalEventRequestDto requestDto);
    void deleteHistoricalEvent(Long id);
}
