package com.qaryati.mapper;
import com.qaryati.dto.VillageNameRequestDto;
import com.qaryati.dto.VillageNameResponseDto;
import com.qaryati.entity.VillageName;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class VillageNameMapper {
    public VillageName toEntity(VillageNameRequestDto dto) {
        if (dto == null) return null;
        VillageName entity = new VillageName();
        entity.setName(dto.getName());
        entity.setLanguage(parseEnum(VillageName.Language.class, dto.getLanguage(), "language"));
        entity.setNameType(parseEnum(VillageName.NameType.class, dto.getNameType(), "nameType"));
        entity.setHistoricalContext(dto.getHistoricalContext());
        return entity;
    }

    public VillageNameResponseDto toDto(VillageName entity) {
        if (entity == null) return null;
        VillageNameResponseDto dto = new VillageNameResponseDto();
        dto.setId(entity.getId());
        dto.setVillageId(entity.getVillage().getId());
        dto.setName(entity.getName());
        if (entity.getLanguage() != null) dto.setLanguage(entity.getLanguage().name());
        if (entity.getNameType() != null) dto.setNameType(entity.getNameType().name());
        dto.setHistoricalContext(entity.getHistoricalContext());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    static <E extends Enum<E>> E parseEnum(Class<E> type, String value, String fieldName) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Enum.valueOf(type, value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            String allowed = Arrays.stream(type.getEnumConstants())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException(
                    "Invalid " + fieldName + " '" + value + "'. Allowed values: " + allowed);
        }
    }
}
