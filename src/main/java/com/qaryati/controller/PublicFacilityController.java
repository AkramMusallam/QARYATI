package com.qaryati.controller;

import com.qaryati.dto.PublicFacilityRequestDto;
import com.qaryati.dto.PublicFacilityResponseDto;
import com.qaryati.service.PublicFacilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public-facilities")
@RequiredArgsConstructor
@Tag(name = "Public Facilities", description = "Supporting context for schools, clinics, libraries, community centers, parks, and sports facilities")
public class PublicFacilityController {

    private final PublicFacilityService publicFacilityService;

    @PostMapping
    @Operation(summary = "Add a public facility to a village")
    public ResponseEntity<PublicFacilityResponseDto> addPublicFacility(@Valid @RequestBody PublicFacilityRequestDto requestDto) {
        return new ResponseEntity<>(publicFacilityService.addPublicFacility(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/village/{villageId}")
    @Operation(summary = "Get all public facilities for a village")
    public ResponseEntity<List<PublicFacilityResponseDto>> getFacilitiesByVillageId(@PathVariable Long villageId) {
        return ResponseEntity.ok(publicFacilityService.getFacilitiesByVillageId(villageId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a public facility")
    public ResponseEntity<PublicFacilityResponseDto> updatePublicFacility(@PathVariable Long id, @Valid @RequestBody PublicFacilityRequestDto requestDto) {
        return ResponseEntity.ok(publicFacilityService.updatePublicFacility(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a public facility (ADMIN only)")
    public ResponseEntity<Void> deletePublicFacility(@PathVariable Long id) {
        publicFacilityService.deletePublicFacility(id);
        return ResponseEntity.noContent().build();
    }
}
