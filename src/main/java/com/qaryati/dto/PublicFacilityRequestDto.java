package com.qaryati.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PublicFacilityRequestDto {
    @NotNull(message = "Village ID is required")
    private Long villageId;

    @NotBlank(message = "Facility name is required")
    private String name;

    @NotBlank(message = "Facility type is required (e.g., SCHOOL, CLINIC, LIBRARY, COMMUNITY_CENTER, PARK, SPORTS_FACILITY)")
    private String facilityType;

    private String description;
}
