package com.qaryati.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VillageNameResponseDto {
    private Long id;
    private Long villageId;
    private String name;
    private String language;
    private String nameType;
    private String historicalContext;
    private String createdBy;
    private LocalDateTime createdAt;
}
