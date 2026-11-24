package com.qaryati.service.impl;

import com.qaryati.dto.EconomyItemRequestDto;
import com.qaryati.dto.EconomyItemResponseDto;
import com.qaryati.entity.EconomyItem;
import com.qaryati.entity.Village;
import com.qaryati.exception.ResourceNotFoundException;
import com.qaryati.mapper.EconomyItemMapper;
import com.qaryati.repository.EconomyItemRepository;
import com.qaryati.repository.VillageRepository;
import com.qaryati.service.EconomyItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EconomyItemServiceImpl implements EconomyItemService {

    private final EconomyItemRepository economyItemRepository;
    private final VillageRepository villageRepository;
    private final EconomyItemMapper economyItemMapper;

    @Override
    @Transactional
    public EconomyItemResponseDto addEconomyItem(EconomyItemRequestDto requestDto) {
        Village village = findVillage(requestDto.getVillageId());
        EconomyItem item = economyItemMapper.toEntity(requestDto, village);
        return economyItemMapper.toDto(economyItemRepository.save(item));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EconomyItemResponseDto> getEconomyItemsByVillageId(Long villageId) {
        findVillage(villageId);
        return economyItemRepository.findByVillageId(villageId)
                .stream()
                .map(economyItemMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public EconomyItemResponseDto updateEconomyItem(Long id, EconomyItemRequestDto requestDto) {
        EconomyItem item = economyItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Economy item not found with id: " + id));
        Village village = findVillage(requestDto.getVillageId());
        economyItemMapper.updateEntityFromDto(requestDto, item, village);
        return economyItemMapper.toDto(economyItemRepository.save(item));
    }

    @Override
    @Transactional
    public void deleteEconomyItem(Long id) {
        EconomyItem item = economyItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Economy item not found with id: " + id));
        economyItemRepository.delete(item);
    }

    private Village findVillage(Long villageId) {
        return villageRepository.findById(villageId)
                .orElseThrow(() -> new ResourceNotFoundException("Village not found with id: " + villageId));
    }
}
