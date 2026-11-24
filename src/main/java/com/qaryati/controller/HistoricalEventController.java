package com.qaryati.controller;

import com.qaryati.dto.HistoricalEventRequestDto;
import com.qaryati.dto.HistoricalEventResponseDto;
import com.qaryati.service.HistoricalEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/historical-events")
@RequiredArgsConstructor
@Tag(name = "Historical Events", description = "Village timeline entries & historical events with provenance")
public class HistoricalEventController {

    private final HistoricalEventService historicalEventService;

    @PostMapping
    @Operation(summary = "Add a historical event to a village timeline")
    public ResponseEntity<HistoricalEventResponseDto> addHistoricalEvent(@Valid @RequestBody HistoricalEventRequestDto requestDto) {
        return new ResponseEntity<>(historicalEventService.addHistoricalEvent(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/village/{villageId}")
    @Operation(summary = "Get chronological historical events for a village")
    public ResponseEntity<List<HistoricalEventResponseDto>> getEventsByVillageId(@PathVariable Long villageId) {
        return ResponseEntity.ok(historicalEventService.getEventsByVillageId(villageId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a historical event")
    public ResponseEntity<HistoricalEventResponseDto> updateHistoricalEvent(@PathVariable Long id, @Valid @RequestBody HistoricalEventRequestDto requestDto) {
        return ResponseEntity.ok(historicalEventService.updateHistoricalEvent(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a historical event (ADMIN only)")
    public ResponseEntity<Void> deleteHistoricalEvent(@PathVariable Long id) {
        historicalEventService.deleteHistoricalEvent(id);
        return ResponseEntity.noContent().build();
    }
}
