package com.qaryati.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LandmarkRequestDto {
    @NotNull(message = "Village ID is required")
    private Long villageId;

    @NotBlank(message = "Landmark name is required")
    private String name;

    @NotBlank(message = "Landmark category is required (e.g., ARCHAEOLOGICAL, RELIGIOUS, SPRING, TRADITIONAL_BUILDING, VIEWPOINT, HERITAGE_SITE)")
    private String category;

    private String description;
    private Double latitude;
    private Double longitude;
    private String sourceReference;
}
