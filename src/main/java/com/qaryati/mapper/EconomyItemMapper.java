package com.qaryati.mapper;

import com.qaryati.dto.EconomyItemRequestDto;
import com.qaryati.dto.EconomyItemResponseDto;
import com.qaryati.entity.EconomyItem;
import com.qaryati.entity.Village;
import org.springframework.stereotype.Component;

@Component
public class EconomyItemMapper {

    public EconomyItem toEntity(EconomyItemRequestDto dto, Village village) {
        if (dto == null) return null;
        return EconomyItem.builder()
                .village(village)
                .name(dto.getName())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .build();
    }

    public void updateEntityFromDto(EconomyItemRequestDto dto, EconomyItem entity, Village village) {
        if (dto == null || entity == null) return;
        entity.setVillage(village);
        entity.setName(dto.getName());
        entity.setCategory(dto.getCategory());
        entity.setDescription(dto.getDescription());
    }

    public EconomyItemResponseDto toDto(EconomyItem entity) {
        if (entity == null) return null;
        EconomyItemResponseDto dto = new EconomyItemResponseDto();
        dto.setId(entity.getId());
        dto.setVillageId(entity.getVillage().getId());
        dto.setName(entity.getName());
        dto.setCategory(entity.getCategory());
        dto.setDescription(entity.getDescription());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLastModifiedBy(entity.getLastModifiedBy());
        dto.setLastModifiedAt(entity.getLastModifiedAt());
        return dto;
    }
}
