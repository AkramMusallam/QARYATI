package com.qaryati.service.impl;
import com.qaryati.dto.PopulationRecordRequestDto;
import com.qaryati.dto.PopulationRecordResponseDto;
import com.qaryati.entity.PopulationRecord;
import com.qaryati.entity.Village;
import com.qaryati.exception.DuplicateResourceException;
import com.qaryati.exception.ResourceNotFoundException;
import com.qaryati.mapper.PopulationRecordMapper;
import com.qaryati.repository.PopulationRecordRepository;
import com.qaryati.repository.VillageRepository;
import com.qaryati.service.PopulationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopulationRecordServiceImpl implements PopulationRecordService {
    private static final String DUPLICATE_MESSAGE = "A population record already exists for this village in the specified year";

    private final PopulationRecordRepository populationRecordRepository;
    private final VillageRepository villageRepository;
    private final PopulationRecordMapper populationRecordMapper;

    @Override
    @Transactional
    public PopulationRecordResponseDto addPopulationRecord(PopulationRecordRequestDto requestDto) {
        Village village = findVillage(requestDto.getVillageId());

        if (populationRecordRepository.existsByVillageIdAndReferenceYear(requestDto.getVillageId(), requestDto.getReferenceYear())) {
            throw new DuplicateResourceException(DUPLICATE_MESSAGE);
        }

        PopulationRecord record = populationRecordMapper.toEntity(requestDto);
        record.setVillage(village);
        return populationRecordMapper.toDto(populationRecordRepository.save(record));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PopulationRecordResponseDto> getRecordsByVillageId(Long villageId) {
        if (!villageRepository.existsById(villageId)) {
            throw new ResourceNotFoundException("Village not found with id: " + villageId);
        }
        return populationRecordRepository.findByVillageIdOrderByReferenceYearDesc(villageId)
                .stream().map(populationRecordMapper::toDto).toList();
    }

    @Override
    @Transactional
    public PopulationRecordResponseDto updatePopulationRecord(Long id, PopulationRecordRequestDto requestDto) {
        PopulationRecord record = findRecord(id);
        Village village = findVillage(requestDto.getVillageId());

        boolean keyChanged = !record.getVillage().getId().equals(requestDto.getVillageId())
                || !record.getReferenceYear().equals(requestDto.getReferenceYear());
        if (keyChanged && populationRecordRepository.existsByVillageIdAndReferenceYear(
                requestDto.getVillageId(), requestDto.getReferenceYear())) {
            throw new DuplicateResourceException(DUPLICATE_MESSAGE);
        }

        record.setPopulationValue(requestDto.getPopulationValue());
        record.setReferenceYear(requestDto.getReferenceYear());
        record.setSourceInformation(requestDto.getSourceInformation());
        record.setVillage(village);

        return populationRecordMapper.toDto(populationRecordRepository.save(record));
    }

    @Override
    @Transactional
    public void deletePopulationRecord(Long id) {
        populationRecordRepository.delete(findRecord(id));
    }

    private Village findVillage(Long villageId) {
        return villageRepository.findById(villageId)
                .orElseThrow(() -> new ResourceNotFoundException("Village not found with id: " + villageId));
    }

    private PopulationRecord findRecord(Long id) {
        return populationRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PopulationRecord not found with id: " + id));
    }
}
