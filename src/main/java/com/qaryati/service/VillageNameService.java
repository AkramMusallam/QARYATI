package com.qaryati.service;
import com.qaryati.dto.VillageNameRequestDto;
import com.qaryati.dto.VillageNameResponseDto;
import java.util.List;

public interface VillageNameService {
    VillageNameResponseDto addVillageName(VillageNameRequestDto requestDto);
    List<VillageNameResponseDto> getNamesByVillageId(Long villageId);
    void deleteVillageName(Long id);
}
