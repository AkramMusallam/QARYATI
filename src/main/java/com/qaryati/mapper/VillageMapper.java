package com.qaryati.mapper;

import com.qaryati.dto.VillageRequestDto;
import com.qaryati.dto.VillageResponseDto;
import com.qaryati.entity.Village;
import org.springframework.stereotype.Component;

@Component
public class VillageMapper {

    public Village toEntity(VillageRequestDto dto) {
        if (dto == null) return null;
        
        Village village = new Village();
        village.setCurrentNameAr(dto.getCurrentNameAr());
        village.setCurrentNameEn(dto.getCurrentNameEn());
        village.setMeaningOrOrigin(dto.getMeaningOrOrigin());
        village.setGovernorate(dto.getGovernorate());
        village.setAdministrativeAffiliation(dto.getAdministrativeAffiliation());
        village.setLatitude(dto.getLatitude());
        village.setLongitude(dto.getLongitude());
        village.setLocationDescription(dto.getLocationDescription());
        village.setElevation(dto.getElevation());
        village.setLandArea(dto.getLandArea());
        village.setNearbyLocations(dto.getNearbyLocations());
        village.setHistoricalOverview(dto.getHistoricalOverview());
        village.setCulturalCharacteristics(dto.getCulturalCharacteristics());
        
        return village;
    }

    public void updateEntityFromDto(VillageRequestDto dto, Village village) {
        if (dto == null || village == null) return;
        
        village.setCurrentNameAr(dto.getCurrentNameAr());
        village.setCurrentNameEn(dto.getCurrentNameEn());
        village.setMeaningOrOrigin(dto.getMeaningOrOrigin());
        village.setGovernorate(dto.getGovernorate());
        village.setAdministrativeAffiliation(dto.getAdministrativeAffiliation());
        village.setLatitude(dto.getLatitude());
        village.setLongitude(dto.getLongitude());
        village.setLocationDescription(dto.getLocationDescription());
        village.setElevation(dto.getElevation());
        village.setLandArea(dto.getLandArea());
        village.setNearbyLocations(dto.getNearbyLocations());
        village.setHistoricalOverview(dto.getHistoricalOverview());
        village.setCulturalCharacteristics(dto.getCulturalCharacteristics());
    }

    public VillageResponseDto toDto(Village entity) {
        if (entity == null) return null;
        
        VillageResponseDto dto = new VillageResponseDto();
        dto.setId(entity.getId());
        dto.setCurrentNameAr(entity.getCurrentNameAr());
        dto.setCurrentNameEn(entity.getCurrentNameEn());
        dto.setMeaningOrOrigin(entity.getMeaningOrOrigin());
        dto.setGovernorate(entity.getGovernorate());
        dto.setAdministrativeAffiliation(entity.getAdministrativeAffiliation());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setLocationDescription(entity.getLocationDescription());
        dto.setElevation(entity.getElevation());
        dto.setLandArea(entity.getLandArea());
        dto.setNearbyLocations(entity.getNearbyLocations());
        dto.setHistoricalOverview(entity.getHistoricalOverview());
        dto.setCulturalCharacteristics(entity.getCulturalCharacteristics());
        
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLastModifiedBy(entity.getLastModifiedBy());
        dto.setLastModifiedAt(entity.getLastModifiedAt());
        
        return dto;
    }
}
