package com.qaryati.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VillageAnalyticsDto {
    private Long villageId;
    private String currentNameAr;
    private String currentNameEn;
    private String governorate;
    private Integer latestPopulation;
    private Integer latestPopulationYear;
    private Long historicalNamesCount;
    private Long historicalEventsCount;
    private Long landmarksCount;
    private Long economyItemsCount;
    private Long publicFacilitiesCount;
}
