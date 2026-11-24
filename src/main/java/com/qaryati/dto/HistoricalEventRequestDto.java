package com.qaryati.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HistoricalEventRequestDto {
    @NotNull(message = "Village ID is required")
    private Long villageId;

    @NotBlank(message = "Event title is required")
    private String title;

    private Integer eventYear;
    private String period;
    private String description;
    private String sourceReference;
}
