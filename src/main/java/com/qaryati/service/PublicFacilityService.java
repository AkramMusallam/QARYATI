package com.qaryati.service;

import com.qaryati.dto.PublicFacilityRequestDto;
import com.qaryati.dto.PublicFacilityResponseDto;

import java.util.List;

public interface PublicFacilityService {
    PublicFacilityResponseDto addPublicFacility(PublicFacilityRequestDto requestDto);
    List<PublicFacilityResponseDto> getFacilitiesByVillageId(Long villageId);
    PublicFacilityResponseDto updatePublicFacility(Long id, PublicFacilityRequestDto requestDto);
    void deletePublicFacility(Long id);
}
