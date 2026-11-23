package com.qaryati.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VillageNameRequestDto {
    @NotNull(message = "Village ID is required")
    private Long villageId;

    @NotBlank(message = "Name is required")
    private String name;

    private String language;
    private String nameType;
    private String historicalContext;
}
