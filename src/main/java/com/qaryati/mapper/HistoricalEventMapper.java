package com.qaryati.mapper;

import com.qaryati.dto.HistoricalEventRequestDto;
import com.qaryati.dto.HistoricalEventResponseDto;
import com.qaryati.entity.HistoricalEvent;
import com.qaryati.entity.Village;
import org.springframework.stereotype.Component;

@Component
public class HistoricalEventMapper {

    public HistoricalEvent toEntity(HistoricalEventRequestDto dto, Village village) {
        if (dto == null) return null;
        return HistoricalEvent.builder()
                .village(village)
                .title(dto.getTitle())
                .eventYear(dto.getEventYear())
                .period(dto.getPeriod())
                .description(dto.getDescription())
                .sourceReference(dto.getSourceReference())
                .build();
    }

    public void updateEntityFromDto(HistoricalEventRequestDto dto, HistoricalEvent entity, Village village) {
        if (dto == null || entity == null) return;
        entity.setVillage(village);
        entity.setTitle(dto.getTitle());
        entity.setEventYear(dto.getEventYear());
        entity.setPeriod(dto.getPeriod());
        entity.setDescription(dto.getDescription());
        entity.setSourceReference(dto.getSourceReference());
    }

    public HistoricalEventResponseDto toDto(HistoricalEvent entity) {
        if (entity == null) return null;
        HistoricalEventResponseDto dto = new HistoricalEventResponseDto();
        dto.setId(entity.getId());
        dto.setVillageId(entity.getVillage().getId());
        dto.setTitle(entity.getTitle());
        dto.setEventYear(entity.getEventYear());
        dto.setPeriod(entity.getPeriod());
        dto.setDescription(entity.getDescription());
        dto.setSourceReference(entity.getSourceReference());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLastModifiedBy(entity.getLastModifiedBy());
        dto.setLastModifiedAt(entity.getLastModifiedAt());
        return dto;
    }
}
