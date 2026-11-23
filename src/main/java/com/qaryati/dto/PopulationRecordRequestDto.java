package com.qaryati.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PopulationRecordRequestDto {
    @NotNull(message = "Village ID is required")
    private Long villageId;

    @NotNull(message = "Population value is required")
    @Min(value = 0, message = "Population value cannot be negative")
    private Integer populationValue;

    @NotNull(message = "Reference year is required")
    @Min(value = 1800, message = "Reference year is too old")
    @Max(value = 2100, message = "Reference year is in the future")
    private Integer referenceYear;

    @NotBlank(message = "Source information is required")
    private String sourceInformation;
}
