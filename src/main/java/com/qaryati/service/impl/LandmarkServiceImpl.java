package com.qaryati.service.impl;

import com.qaryati.dto.LandmarkRequestDto;
import com.qaryati.dto.LandmarkResponseDto;
import com.qaryati.entity.Landmark;
import com.qaryati.entity.Village;
import com.qaryati.exception.ResourceNotFoundException;
import com.qaryati.mapper.LandmarkMapper;
import com.qaryati.repository.LandmarkRepository;
import com.qaryati.repository.VillageRepository;
import com.qaryati.service.LandmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LandmarkServiceImpl implements LandmarkService {

    private final LandmarkRepository landmarkRepository;
    private final VillageRepository villageRepository;
    private final LandmarkMapper landmarkMapper;

    @Override
    @Transactional
    public LandmarkResponseDto addLandmark(LandmarkRequestDto requestDto) {
        Village village = findVillage(requestDto.getVillageId());
        Landmark landmark = landmarkMapper.toEntity(requestDto, village);
        return landmarkMapper.toDto(landmarkRepository.save(landmark));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LandmarkResponseDto> getLandmarksByVillageId(Long villageId) {
        findVillage(villageId);
        return landmarkRepository.findByVillageId(villageId)
                .stream()
                .map(landmarkMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public LandmarkResponseDto updateLandmark(Long id, LandmarkRequestDto requestDto) {
        Landmark landmark = landmarkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Landmark not found with id: " + id));
        Village village = findVillage(requestDto.getVillageId());
        landmarkMapper.updateEntityFromDto(requestDto, landmark, village);
        return landmarkMapper.toDto(landmarkRepository.save(landmark));
    }

    @Override
    @Transactional
    public void deleteLandmark(Long id) {
        Landmark landmark = landmarkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Landmark not found with id: " + id));
        landmarkRepository.delete(landmark);
    }

    private Village findVillage(Long villageId) {
        return villageRepository.findById(villageId)
                .orElseThrow(() -> new ResourceNotFoundException("Village not found with id: " + villageId));
    }
}
