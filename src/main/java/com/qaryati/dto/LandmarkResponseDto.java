package com.qaryati.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LandmarkResponseDto {
    private Long id;
    private Long villageId;
    private String name;
    private String category;
    private String description;
    private Double latitude;
    private Double longitude;
    private String sourceReference;
    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;
}
