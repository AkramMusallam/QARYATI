package com.qaryati.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HistoricalEventResponseDto {
    private Long id;
    private Long villageId;
    private String title;
    private Integer eventYear;
    private String period;
    private String description;
    private String sourceReference;
    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;
}
