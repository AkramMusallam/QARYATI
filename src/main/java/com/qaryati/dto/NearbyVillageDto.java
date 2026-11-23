package com.qaryati.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearbyVillageDto {
    private Long villageId;
    private String currentNameAr;
    private String currentNameEn;
    private String governorate;
    private Double latitude;
    private Double longitude;
    private Double distanceKm;
}
