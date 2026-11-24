package com.qaryati.service.impl;

import com.qaryati.dto.PublicFacilityRequestDto;
import com.qaryati.dto.PublicFacilityResponseDto;
import com.qaryati.entity.PublicFacility;
import com.qaryati.entity.Village;
import com.qaryati.exception.ResourceNotFoundException;
import com.qaryati.mapper.PublicFacilityMapper;
import com.qaryati.repository.PublicFacilityRepository;
import com.qaryati.repository.VillageRepository;
import com.qaryati.service.PublicFacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicFacilityServiceImpl implements PublicFacilityService {

    private final PublicFacilityRepository publicFacilityRepository;
    private final VillageRepository villageRepository;
    private final PublicFacilityMapper publicFacilityMapper;

    @Override
    @Transactional
    public PublicFacilityResponseDto addPublicFacility(PublicFacilityRequestDto requestDto) {
        Village village = findVillage(requestDto.getVillageId());
        PublicFacility facility = publicFacilityMapper.toEntity(requestDto, village);
        return publicFacilityMapper.toDto(publicFacilityRepository.save(facility));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicFacilityResponseDto> getFacilitiesByVillageId(Long villageId) {
        findVillage(villageId);
        return publicFacilityRepository.findByVillageId(villageId)
                .stream()
                .map(publicFacilityMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public PublicFacilityResponseDto updatePublicFacility(Long id, PublicFacilityRequestDto requestDto) {
        PublicFacility facility = publicFacilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Public facility not found with id: " + id));
        Village village = findVillage(requestDto.getVillageId());
        publicFacilityMapper.updateEntityFromDto(requestDto, facility, village);
        return publicFacilityMapper.toDto(publicFacilityRepository.save(facility));
    }

    @Override
    @Transactional
    public void deletePublicFacility(Long id) {
        PublicFacility facility = publicFacilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Public facility not found with id: " + id));
        publicFacilityRepository.delete(facility);
    }

    private Village findVillage(Long villageId) {
        return villageRepository.findById(villageId)
                .orElseThrow(() -> new ResourceNotFoundException("Village not found with id: " + villageId));
    }
}
