package com.qaryati.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GovernorateStatsDto {
    private String governorate;
    private Long villagesCount;
    private Long totalLandmarksCount;
    private Long totalLatestPopulation;
}
