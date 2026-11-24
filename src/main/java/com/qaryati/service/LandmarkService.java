package com.qaryati.service;

import com.qaryati.dto.LandmarkRequestDto;
import com.qaryati.dto.LandmarkResponseDto;

import java.util.List;

public interface LandmarkService {
    LandmarkResponseDto addLandmark(LandmarkRequestDto requestDto);
    List<LandmarkResponseDto> getLandmarksByVillageId(Long villageId);
    LandmarkResponseDto updateLandmark(Long id, LandmarkRequestDto requestDto);
    void deleteLandmark(Long id);
}
