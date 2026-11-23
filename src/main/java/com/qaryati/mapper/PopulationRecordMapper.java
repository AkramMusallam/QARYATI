package com.qaryati.mapper;
import com.qaryati.dto.PopulationRecordRequestDto;
import com.qaryati.dto.PopulationRecordResponseDto;
import com.qaryati.entity.PopulationRecord;
import org.springframework.stereotype.Component;

@Component
public class PopulationRecordMapper {
    public PopulationRecord toEntity(PopulationRecordRequestDto dto) {
        if (dto == null) return null;
        PopulationRecord entity = new PopulationRecord();
        entity.setPopulationValue(dto.getPopulationValue());
        entity.setReferenceYear(dto.getReferenceYear());
        entity.setSourceInformation(dto.getSourceInformation());
        return entity;
    }

    public PopulationRecordResponseDto toDto(PopulationRecord entity) {
        if (entity == null) return null;
        PopulationRecordResponseDto dto = new PopulationRecordResponseDto();
        dto.setId(entity.getId());
        dto.setVillageId(entity.getVillage().getId());
        dto.setPopulationValue(entity.getPopulationValue());
        dto.setReferenceYear(entity.getReferenceYear());
        dto.setSourceInformation(entity.getSourceInformation());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
