package com.qaryati.service.impl;

import com.qaryati.dto.*;
import com.qaryati.entity.Village;
import com.qaryati.exception.ResourceNotFoundException;
import com.qaryati.mapper.VillageMapper;
import com.qaryati.repository.VillageRawSqlRepository;
import com.qaryati.repository.VillageRepository;
import com.qaryati.service.GeolocationService;
import com.qaryati.service.VillageService;
import com.qaryati.specification.VillageSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VillageServiceImpl implements VillageService {

    private final VillageRepository villageRepository;
    private final VillageRawSqlRepository villageRawSqlRepository;
    private final VillageMapper villageMapper;
    private final GeolocationService geolocationService;

    @Override
    @CacheEvict(value = "villages", allEntries = true)
    public VillageResponseDto createVillage(VillageRequestDto requestDto) {
        Village village = villageMapper.toEntity(requestDto);

        if (!hasCoordinates(village)) {
            resolveCoordinates(village);
        }

        Village savedVillage = villageRepository.save(village);
        return villageMapper.toDto(savedVillage);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "villages", key = "#id")
    public VillageResponseDto getVillageById(Long id) {
        return villageMapper.toDto(findVillage(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VillageResponseDto> getAllVillages(String search, String governorate, Pageable pageable) {
        Specification<Village> spec = VillageSpecification.filterAndSearch(search, governorate);
        return villageRepository.findAll(spec, pageable)
                .map(villageMapper::toDto);
    }

    @Override
    @CacheEvict(value = "villages", key = "#id")
    public VillageResponseDto updateVillage(Long id, VillageRequestDto requestDto) {
        Village village = findVillage(id);

        boolean nameChanged = !Objects.equals(village.getCurrentNameAr(), requestDto.getCurrentNameAr());
        Double previousLatitude = village.getLatitude();
        Double previousLongitude = village.getLongitude();

        villageMapper.updateEntityFromDto(requestDto, village);

        if (!hasCoordinates(village)) {
            if (!nameChanged && previousLatitude != null && previousLongitude != null) {
                village.setLatitude(previousLatitude);
                village.setLongitude(previousLongitude);
            } else {
                resolveCoordinates(village);
            }
        }

        Village updatedVillage = villageRepository.save(village);
        return villageMapper.toDto(updatedVillage);
    }

    @Override
    @Transactional
    @CacheEvict(value = "villages", key = "#id")
    public void deleteVillage(Long id) {
        villageRepository.delete(findVillage(id));
    }

    @Override
    @Transactional(readOnly = true)
    public VillageAnalyticsDto getVillageAnalytics(Long id) {
        return villageRawSqlRepository.findVillageAnalyticsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Village not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GovernorateStatsDto> getGovernorateStatistics() {
        return villageRawSqlRepository.findGovernorateStatistics();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NearbyVillageDto> getNearbyVillages(Long villageId, double radiusKm) {
        Village village = findVillage(villageId);
        if (!hasCoordinates(village)) {
            return Collections.emptyList();
        }
        return villageRawSqlRepository.findNearbyVillages(
                village.getLatitude(), village.getLongitude(), radiusKm, village.getId());
    }

    private Village findVillage(Long id) {
        return villageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Village not found with id: " + id));
    }

    private static boolean hasCoordinates(Village village) {
        return village.getLatitude() != null && village.getLongitude() != null;
    }

    private void resolveCoordinates(Village village) {
        geolocationService.getCoordinates(village.getCurrentNameAr())
                .ifPresent(coords -> {
                    village.setLatitude(coords.latitude());
                    village.setLongitude(coords.longitude());
                });
    }
}
