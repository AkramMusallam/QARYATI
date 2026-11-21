package com.qaryati.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VillageRequestDto {

    @NotBlank(message = "Arabic name is required")
    private String currentNameAr;

    private String currentNameEn;
    
    private String meaningOrOrigin;

    @NotBlank(message = "Governorate is required")
    private String governorate;

    private String administrativeAffiliation;
    
    private Double latitude;
    private Double longitude;
    private String locationDescription;
    private Integer elevation;
    private Double landArea;
    private String nearbyLocations;
    private String historicalOverview;
    private String culturalCharacteristics;
}
