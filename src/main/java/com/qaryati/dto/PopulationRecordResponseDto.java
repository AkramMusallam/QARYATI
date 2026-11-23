package com.qaryati.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PopulationRecordResponseDto {
    private Long id;
    private Long villageId;
    private Integer populationValue;
    private Integer referenceYear;
    private String sourceInformation;
    private String createdBy;
    private LocalDateTime createdAt;
}
