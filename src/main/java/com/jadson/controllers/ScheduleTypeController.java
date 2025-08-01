package com.jadson.controllers;

import com.jadson.dto.requests.ScheduleTypeRequestDTO;
import com.jadson.dto.responses.ScheduleTypeResponseDTO;
import com.jadson.services.ScheduleTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/ScheduleType")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ScheduleTypeController {

    private final ScheduleTypeService scheduleTypeService;

    @GetMapping(value = "/{idScheduleType}")
    public ResponseEntity<ScheduleTypeResponseDTO> findById(@PathVariable Long idScheduleType) {
        return ResponseEntity.ok().body(scheduleTypeService.findById(idScheduleType));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleTypeResponseDTO>> findAll() { return ResponseEntity.ok(scheduleTypeService.findAll());
    }

    @PostMapping
    public ResponseEntity<ScheduleTypeResponseDTO> createScheduleType(@Valid @RequestBody ScheduleTypeRequestDTO scheduleTypeRequestDTO, UriComponentsBuilder uriComponentsBuilder) {

        ScheduleTypeResponseDTO scheduleTypeResponseDTO = scheduleTypeService.createScheduleType(scheduleTypeRequestDTO);

        URI uri = uriComponentsBuilder.path("/ScheduleType/{id}").buildAndExpand(scheduleTypeResponseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(scheduleTypeResponseDTO);
    }

    @PutMapping (value = "/{idScheduleType}")
    public ResponseEntity<ScheduleTypeResponseDTO> updateScheduleType(@Valid @RequestBody ScheduleTypeRequestDTO scheduleTypeRequestDTO, @PathVariable Long idScheduleType) {
        return ResponseEntity.ok(scheduleTypeService.updateScheduleType(scheduleTypeRequestDTO, idScheduleType));
    }

    @DeleteMapping (value = "/{idScheduleType}")
    public ResponseEntity<String> deleteScheduleType(@PathVariable Long idScheduleType) {
        return ResponseEntity.ok(scheduleTypeService.deleteScheduleType(idScheduleType));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllScheduleType() {
        return ResponseEntity.ok(scheduleTypeService.deleteAllScheduleType());
    }

}
