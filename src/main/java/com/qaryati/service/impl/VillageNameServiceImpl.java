package com.qaryati.service.impl;
import com.qaryati.dto.VillageNameRequestDto;
import com.qaryati.dto.VillageNameResponseDto;
import com.qaryati.entity.Village;
import com.qaryati.entity.VillageName;
import com.qaryati.exception.ResourceNotFoundException;
import com.qaryati.mapper.VillageNameMapper;
import com.qaryati.repository.VillageNameRepository;
import com.qaryati.repository.VillageRepository;
import com.qaryati.service.VillageNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VillageNameServiceImpl implements VillageNameService {
    private final VillageNameRepository villageNameRepository;
    private final VillageRepository villageRepository;
    private final VillageNameMapper villageNameMapper;

    @Override
    @Transactional
    public VillageNameResponseDto addVillageName(VillageNameRequestDto requestDto) {
        Village village = villageRepository.findById(requestDto.getVillageId())
                .orElseThrow(() -> new ResourceNotFoundException("Village not found with id: " + requestDto.getVillageId()));
        VillageName villageName = villageNameMapper.toEntity(requestDto);
        villageName.setVillage(village);
        return villageNameMapper.toDto(villageNameRepository.save(villageName));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VillageNameResponseDto> getNamesByVillageId(Long villageId) {
        if (!villageRepository.existsById(villageId)) {
            throw new ResourceNotFoundException("Village not found with id: " + villageId);
        }
        return villageNameRepository.findByVillageId(villageId)
                .stream().map(villageNameMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteVillageName(Long id) {
        VillageName villageName = villageNameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VillageName not found with id: " + id));
        villageNameRepository.delete(villageName);
    }
}
