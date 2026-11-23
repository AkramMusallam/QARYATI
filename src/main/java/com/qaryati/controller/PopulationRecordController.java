package com.qaryati.controller;
import com.qaryati.dto.PopulationRecordRequestDto;
import com.qaryati.dto.PopulationRecordResponseDto;
import com.qaryati.service.PopulationRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/population-records")
@RequiredArgsConstructor
public class PopulationRecordController {
    private final PopulationRecordService populationRecordService;

    @PostMapping
    public ResponseEntity<PopulationRecordResponseDto> addPopulationRecord(@Valid @RequestBody PopulationRecordRequestDto requestDto) {
        return new ResponseEntity<>(populationRecordService.addPopulationRecord(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/village/{villageId}")
    public ResponseEntity<List<PopulationRecordResponseDto>> getRecordsByVillageId(@PathVariable Long villageId) {
        return ResponseEntity.ok(populationRecordService.getRecordsByVillageId(villageId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PopulationRecordResponseDto> updatePopulationRecord(@PathVariable Long id, @Valid @RequestBody PopulationRecordRequestDto requestDto) {
        return ResponseEntity.ok(populationRecordService.updatePopulationRecord(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePopulationRecord(@PathVariable Long id) {
        populationRecordService.deletePopulationRecord(id);
        return ResponseEntity.noContent().build();
    }
}
