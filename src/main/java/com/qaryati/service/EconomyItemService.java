package com.qaryati.service;

import com.qaryati.dto.EconomyItemRequestDto;
import com.qaryati.dto.EconomyItemResponseDto;

import java.util.List;

public interface EconomyItemService {
    EconomyItemResponseDto addEconomyItem(EconomyItemRequestDto requestDto);
    List<EconomyItemResponseDto> getEconomyItemsByVillageId(Long villageId);
    EconomyItemResponseDto updateEconomyItem(Long id, EconomyItemRequestDto requestDto);
    void deleteEconomyItem(Long id);
}
