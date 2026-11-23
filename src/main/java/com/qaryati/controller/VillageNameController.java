package com.qaryati.controller;
import com.qaryati.dto.VillageNameRequestDto;
import com.qaryati.dto.VillageNameResponseDto;
import com.qaryati.service.VillageNameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/village-names")
@RequiredArgsConstructor
public class VillageNameController {
    private final VillageNameService villageNameService;

    @PostMapping
    public ResponseEntity<VillageNameResponseDto> addVillageName(@Valid @RequestBody VillageNameRequestDto requestDto) {
        return new ResponseEntity<>(villageNameService.addVillageName(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/village/{villageId}")
    public ResponseEntity<List<VillageNameResponseDto>> getNamesByVillageId(@PathVariable Long villageId) {
        return ResponseEntity.ok(villageNameService.getNamesByVillageId(villageId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVillageName(@PathVariable Long id) {
        villageNameService.deleteVillageName(id);
        return ResponseEntity.noContent().build();
    }
}
