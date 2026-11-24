package com.qaryati.controller;

import com.qaryati.dto.EconomyItemRequestDto;
import com.qaryati.dto.EconomyItemResponseDto;
import com.qaryati.service.EconomyItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/economy-items")
@RequiredArgsConstructor
@Tag(name = "Agriculture & Local Economy", description = "Agricultural products, crops, traditional crafts, and local industries")
public class EconomyItemController {

    private final EconomyItemService economyItemService;

    @PostMapping
    @Operation(summary = "Add an agricultural or economic item to a village")
    public ResponseEntity<EconomyItemResponseDto> addEconomyItem(@Valid @RequestBody EconomyItemRequestDto requestDto) {
        return new ResponseEntity<>(economyItemService.addEconomyItem(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/village/{villageId}")
    @Operation(summary = "Get all agricultural and economy items for a village")
    public ResponseEntity<List<EconomyItemResponseDto>> getEconomyItemsByVillageId(@PathVariable Long villageId) {
        return ResponseEntity.ok(economyItemService.getEconomyItemsByVillageId(villageId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an agricultural or economy item")
    public ResponseEntity<EconomyItemResponseDto> updateEconomyItem(@PathVariable Long id, @Valid @RequestBody EconomyItemRequestDto requestDto) {
        return ResponseEntity.ok(economyItemService.updateEconomyItem(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an economy item (ADMIN only)")
    public ResponseEntity<Void> deleteEconomyItem(@PathVariable Long id) {
        economyItemService.deleteEconomyItem(id);
        return ResponseEntity.noContent().build();
    }
}
