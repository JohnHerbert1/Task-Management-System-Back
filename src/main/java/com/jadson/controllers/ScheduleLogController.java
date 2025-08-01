package com.jadson.controllers;

import com.jadson.dto.requests.ScheduleLogRequestDTO;
import com.jadson.dto.responses.ScheduleLogResponseDTO;
import com.jadson.exceptions.BusinessRuleException;
import com.jadson.services.ScheduleLogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@RequestMapping("/ScheduleLogController")
@AllArgsConstructor
public class ScheduleLogController {
    private final ScheduleLogService scheduleLogService;

    @PostMapping
    public ResponseEntity<ScheduleLogResponseDTO> createScheduleLog(ScheduleLogRequestDTO scheduleLogRequestDTO)  {
        return new ResponseEntity<>(scheduleLogService.registerEntity(scheduleLogRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{idScheduleLog}")
    public ResponseEntity<ScheduleLogResponseDTO> updateScheduleLog(ScheduleLogRequestDTO scheduleLogRequestDTO, @PathVariable(name = "idScheduleLog") Long id) throws BusinessRuleException {
        return new ResponseEntity<>(scheduleLogService.updateEntity(scheduleLogRequestDTO, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{idScheduleLog}")
    public ResponseEntity<Boolean> deleteScheduleLog(@PathVariable(name = "idScheduleLog") Long id) throws BusinessRuleException {
        return new ResponseEntity<>(scheduleLogService.deleteEntity(id), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/all/logs")
    public ResponseEntity<Boolean> deleteAllScheduleLog() {
        return new ResponseEntity<>(scheduleLogService.deleteAllEntity(), HttpStatus.NO_CONTENT);
    }

    @GetMapping(name = "/search-id/", value = "/{idScheduleLog}")
    public ResponseEntity<ScheduleLogResponseDTO> findByIdScheduleLog(@PathVariable(name = "idScheduleLog") Long id) throws BusinessRuleException {
        return new ResponseEntity<>(scheduleLogService.findByIdEntity(id), HttpStatus.OK);
    }

    @GetMapping(name = "/search-all")
    public ResponseEntity<Page<ScheduleLogResponseDTO>> findByIdScheduleLog(Pageable pageable) throws BusinessRuleException {
        return new ResponseEntity<>(scheduleLogService.findAllEntity(pageable), HttpStatus.OK);
    }
}
