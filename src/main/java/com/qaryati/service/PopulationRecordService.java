package com.qaryati.service;
import com.qaryati.dto.PopulationRecordRequestDto;
import com.qaryati.dto.PopulationRecordResponseDto;
import java.util.List;

public interface PopulationRecordService {
    PopulationRecordResponseDto addPopulationRecord(PopulationRecordRequestDto requestDto);
    List<PopulationRecordResponseDto> getRecordsByVillageId(Long villageId);
    PopulationRecordResponseDto updatePopulationRecord(Long id, PopulationRecordRequestDto requestDto);
    void deletePopulationRecord(Long id);
}
