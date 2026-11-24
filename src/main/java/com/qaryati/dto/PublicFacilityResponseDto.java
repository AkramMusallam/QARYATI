package com.qaryati.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PublicFacilityResponseDto {
    private Long id;
    private Long villageId;
    private String name;
    private String facilityType;
    private String description;
    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;
}
