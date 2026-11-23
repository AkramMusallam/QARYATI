package com.qaryati.service;

import com.qaryati.dto.PopulationRecordRequestDto;
import com.qaryati.dto.PopulationRecordResponseDto;
import com.qaryati.entity.PopulationRecord;
import com.qaryati.entity.Village;
import com.qaryati.exception.DuplicateResourceException;
import com.qaryati.mapper.PopulationRecordMapper;
import com.qaryati.repository.PopulationRecordRepository;
import com.qaryati.repository.VillageRepository;
import com.qaryati.service.impl.PopulationRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PopulationRecordServiceTest {

    @Mock
    private PopulationRecordRepository populationRecordRepository;

    @Mock
    private VillageRepository villageRepository;

    private PopulationRecordServiceImpl populationRecordService;

    @BeforeEach
    void setUp() {
        PopulationRecordMapper mapper = new PopulationRecordMapper();
        populationRecordService = new PopulationRecordServiceImpl(populationRecordRepository, villageRepository, mapper);
    }

    @Test
    void addPopulationRecord_whenYearAlreadyExistsForVillage_throwsDuplicateResourceException() {
        PopulationRecordRequestDto dto = new PopulationRecordRequestDto();
        dto.setVillageId(1L);
        dto.setReferenceYear(1945);
        dto.setPopulationValue(2550);
        dto.setSourceInformation("Village Statistics 1945");

        when(villageRepository.findById(1L)).thenReturn(Optional.of(Village.builder().id(1L).build()));
        when(populationRecordRepository.existsByVillageIdAndReferenceYear(1L, 1945)).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> populationRecordService.addPopulationRecord(dto));
        verify(populationRecordRepository, never()).save(any());
    }

    @Test
    void addPopulationRecord_whenValid_savesRecord() {
        PopulationRecordRequestDto dto = new PopulationRecordRequestDto();
        dto.setVillageId(1L);
        dto.setReferenceYear(1931);
        dto.setPopulationValue(1893);
        dto.setSourceInformation("1931 Census of Palestine");

        Village village = Village.builder().id(1L).currentNameAr("لفتا").build();
        when(villageRepository.findById(1L)).thenReturn(Optional.of(village));
        when(populationRecordRepository.existsByVillageIdAndReferenceYear(1L, 1931)).thenReturn(false);
        when(populationRecordRepository.save(any(PopulationRecord.class))).thenAnswer(inv -> {
            PopulationRecord r = inv.getArgument(0);
            r.setId(10L);
            return r;
        });

        PopulationRecordResponseDto result = populationRecordService.addPopulationRecord(dto);
        assertEquals(10L, result.getId());
        assertEquals(1893, result.getPopulationValue());
        assertEquals(1931, result.getReferenceYear());
    }
}
