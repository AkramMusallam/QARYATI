package com.qaryati.mapper;

import com.qaryati.dto.LandmarkRequestDto;
import com.qaryati.dto.LandmarkResponseDto;
import com.qaryati.entity.Landmark;
import com.qaryati.entity.Village;
import org.springframework.stereotype.Component;

@Component
public class LandmarkMapper {

    public Landmark toEntity(LandmarkRequestDto dto, Village village) {
        if (dto == null) return null;
        return Landmark.builder()
                .village(village)
                .name(dto.getName())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .sourceReference(dto.getSourceReference())
                .build();
    }

    public void updateEntityFromDto(LandmarkRequestDto dto, Landmark entity, Village village) {
        if (dto == null || entity == null) return;
        entity.setVillage(village);
        entity.setName(dto.getName());
        entity.setCategory(dto.getCategory());
        entity.setDescription(dto.getDescription());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setSourceReference(dto.getSourceReference());
    }

    public LandmarkResponseDto toDto(Landmark entity) {
        if (entity == null) return null;
        LandmarkResponseDto dto = new LandmarkResponseDto();
        dto.setId(entity.getId());
        dto.setVillageId(entity.getVillage().getId());
        dto.setName(entity.getName());
        dto.setCategory(entity.getCategory());
        dto.setDescription(entity.getDescription());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setSourceReference(entity.getSourceReference());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLastModifiedBy(entity.getLastModifiedBy());
        dto.setLastModifiedAt(entity.getLastModifiedAt());
        return dto;
    }
}
