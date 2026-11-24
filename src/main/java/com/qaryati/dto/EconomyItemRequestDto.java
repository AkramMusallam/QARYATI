package com.qaryati.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EconomyItemRequestDto {
    @NotNull(message = "Village ID is required")
    private Long villageId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Category is required (e.g., CROP, AGRICULTURAL_PRODUCT, CRAFT, INDUSTRY)")
    private String category;

    private String description;
}
