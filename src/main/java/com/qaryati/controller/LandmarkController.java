package com.qaryati.controller;

import com.qaryati.dto.LandmarkRequestDto;
import com.qaryati.dto.LandmarkResponseDto;
import com.qaryati.service.LandmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/landmarks")
@RequiredArgsConstructor
@Tag(name = "Landmarks & Heritage", description = "Heritage sites, archaeological locations, springs, religious sites & viewpoints")
public class LandmarkController {

    private final LandmarkService landmarkService;

    @PostMapping
    @Operation(summary = "Add a landmark or heritage site to a village")
    public ResponseEntity<LandmarkResponseDto> addLandmark(@Valid @RequestBody LandmarkRequestDto requestDto) {
        return new ResponseEntity<>(landmarkService.addLandmark(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/village/{villageId}")
    @Operation(summary = "Get all landmarks for a village")
    public ResponseEntity<List<LandmarkResponseDto>> getLandmarksByVillageId(@PathVariable Long villageId) {
        return ResponseEntity.ok(landmarkService.getLandmarksByVillageId(villageId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a landmark")
    public ResponseEntity<LandmarkResponseDto> updateLandmark(@PathVariable Long id, @Valid @RequestBody LandmarkRequestDto requestDto) {
        return ResponseEntity.ok(landmarkService.updateLandmark(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a landmark (ADMIN only)")
    public ResponseEntity<Void> deleteLandmark(@PathVariable Long id) {
        landmarkService.deleteLandmark(id);
        return ResponseEntity.noContent().build();
    }
}
