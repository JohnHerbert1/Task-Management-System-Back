package com.jadson.controllers;

import com.jadson.dto.requests.ScheduleRequestDTO;
import com.jadson.dto.responses.ScheduleResponseDTO;
import com.jadson.services.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/Schedule")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping(value = "/{idSchedule}")
    public ResponseEntity<ScheduleResponseDTO> findById(@PathVariable Long idSchedule) {
        return ResponseEntity.ok().body(scheduleService.findById(idSchedule));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDTO>> findAll() { return ResponseEntity.ok(scheduleService.findAll());
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDTO> createSchedule(@Valid @RequestBody ScheduleRequestDTO scheduleRequestDTO, UriComponentsBuilder uriComponentsBuilder) {

        ScheduleResponseDTO scheduleResponseDTO = scheduleService.createSchedule(scheduleRequestDTO);

        URI uri = uriComponentsBuilder.path("/Schedule/{id}").buildAndExpand(scheduleResponseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(scheduleResponseDTO);
    }

    @PutMapping (value = "/{idSchedule}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@Valid @RequestBody ScheduleRequestDTO scheduleRequestDTO, @PathVariable Long idSchedule) {
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleRequestDTO, idSchedule));
    }

    @DeleteMapping (value = "/{idSchedule}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long idSchedule) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(idSchedule));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllSchedule() {
        return ResponseEntity.ok(scheduleService.deleteAllSchedule());
    }

}
