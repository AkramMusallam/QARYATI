package com.qaryati.service;

import com.qaryati.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VillageService {
    VillageResponseDto createVillage(VillageRequestDto requestDto);
    VillageResponseDto getVillageById(Long id);
    Page<VillageResponseDto> getAllVillages(String search, String governorate, Pageable pageable);
    VillageResponseDto updateVillage(Long id, VillageRequestDto requestDto);
    void deleteVillage(Long id);

    VillageAnalyticsDto getVillageAnalytics(Long id);
    List<GovernorateStatsDto> getGovernorateStatistics();
    List<NearbyVillageDto> getNearbyVillages(Long villageId, double radiusKm);
}
