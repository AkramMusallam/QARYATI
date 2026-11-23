package com.qaryati.controller;

import com.qaryati.dto.*;
import com.qaryati.service.VillageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/villages")
@RequiredArgsConstructor
@Tag(name = "Villages", description = "Village Identity, Geography, Analytics & Spatial Search APIs")
public class VillageController {

    private final VillageService villageService;

    @PostMapping
    @Operation(summary = "Create a new village", description = "Creates a village record and resolves geographic coordinates from OpenStreetMap if not provided")
    public ResponseEntity<VillageResponseDto> createVillage(@Valid @RequestBody VillageRequestDto requestDto) {
        return new ResponseEntity<>(villageService.createVillage(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get village by ID", description = "Retrieves structured identity, administrative affiliation, geography, and historical overview")
    public ResponseEntity<VillageResponseDto> getVillageById(@PathVariable Long id) {
        return ResponseEntity.ok(villageService.getVillageById(id));
    }

    @GetMapping
    @Operation(summary = "List, filter, search, and sort villages", description = "Supports pagination, search by Arabic/English/affiliation name, and filtering by governorate")
    public ResponseEntity<Page<VillageResponseDto>> getAllVillages(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String governorate,
            Pageable pageable) {
        return ResponseEntity.ok(villageService.getAllVillages(search, governorate, pageable));
    }

    @GetMapping("/{id}/analytics")
    @Operation(summary = "Get village aggregated analytics (Raw SQL)", description = "Demonstrates native/raw SQL aggregation for latest population and counts of related knowledge entities")
    public ResponseEntity<VillageAnalyticsDto> getVillageAnalytics(@PathVariable Long id) {
        return ResponseEntity.ok(villageService.getVillageAnalytics(id));
    }

    @GetMapping("/{id}/nearby")
    @Operation(summary = "Find nearby villages within radius (Raw SQL Haversine)", description = "Uses native SQL trigonometric Haversine formula to find nearby villages within radiusKm")
    public ResponseEntity<List<NearbyVillageDto>> getNearbyVillages(
            @PathVariable Long id,
            @RequestParam(defaultValue = "15.0") double radiusKm) {
        return ResponseEntity.ok(villageService.getNearbyVillages(id, radiusKm));
    }

    @GetMapping("/stats/governorates")
    @Operation(summary = "Get statistics grouped by governorate (Raw SQL)", description = "Aggregates village count, total landmarks, and latest population sum per governorate using raw SQL")
    public ResponseEntity<List<GovernorateStatsDto>> getGovernorateStatistics() {
        return ResponseEntity.ok(villageService.getGovernorateStatistics());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing village")
    public ResponseEntity<VillageResponseDto> updateVillage(@PathVariable Long id, @Valid @RequestBody VillageRequestDto requestDto) {
        return ResponseEntity.ok(villageService.updateVillage(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a village (ADMIN only)")
    public ResponseEntity<Void> deleteVillage(@PathVariable Long id) {
        villageService.deleteVillage(id);
        return ResponseEntity.noContent().build();
    }
}

