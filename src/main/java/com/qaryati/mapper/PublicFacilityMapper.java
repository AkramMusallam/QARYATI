package com.qaryati.mapper;

import com.qaryati.dto.PublicFacilityRequestDto;
import com.qaryati.dto.PublicFacilityResponseDto;
import com.qaryati.entity.PublicFacility;
import com.qaryati.entity.Village;
import org.springframework.stereotype.Component;

@Component
public class PublicFacilityMapper {

    public PublicFacility toEntity(PublicFacilityRequestDto dto, Village village) {
        if (dto == null) return null;
        return PublicFacility.builder()
                .village(village)
                .name(dto.getName())
                .facilityType(dto.getFacilityType())
                .description(dto.getDescription())
                .build();
    }

    public void updateEntityFromDto(PublicFacilityRequestDto dto, PublicFacility entity, Village village) {
        if (dto == null || entity == null) return;
        entity.setVillage(village);
        entity.setName(dto.getName());
        entity.setFacilityType(dto.getFacilityType());
        entity.setDescription(dto.getDescription());
    }

    public PublicFacilityResponseDto toDto(PublicFacility entity) {
        if (entity == null) return null;
        PublicFacilityResponseDto dto = new PublicFacilityResponseDto();
        dto.setId(entity.getId());
        dto.setVillageId(entity.getVillage().getId());
        dto.setName(entity.getName());
        dto.setFacilityType(entity.getFacilityType());
        dto.setDescription(entity.getDescription());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLastModifiedBy(entity.getLastModifiedBy());
        dto.setLastModifiedAt(entity.getLastModifiedAt());
        return dto;
    }
}
