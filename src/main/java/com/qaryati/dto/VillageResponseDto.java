package com.qaryati.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VillageResponseDto {
    private Long id;
    private String currentNameAr;
    private String currentNameEn;
    private String meaningOrOrigin;
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

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;
}
