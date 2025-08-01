package com.jadson.controllers;

import com.jadson.dto.requests.TaskLogRequestDTO;
import com.jadson.dto.responses.TaskLogResponseDTO;
import com.jadson.exceptions.BusinessRuleException;
import com.jadson.services.TaskLogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@RequestMapping("/TaskLogController")
@AllArgsConstructor
public class TaskLogController {
    private final TaskLogService taskLogService;

    @PostMapping
    public ResponseEntity<TaskLogResponseDTO> createTaskLog(TaskLogRequestDTO taskLogRequestDTO)  {
        return new ResponseEntity<>(taskLogService.registerEntity(taskLogRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{idTaskLog}")
    public ResponseEntity<TaskLogResponseDTO> updateTaskLog(TaskLogRequestDTO taskLogRequestDTO, @PathVariable(name = "idTaskLog") Long id) throws BusinessRuleException {
        return new ResponseEntity<>(taskLogService.updateEntity(taskLogRequestDTO, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{idTaskLog}")
    public ResponseEntity<Boolean> deleteTaskLog(@PathVariable(name = "idTaskLog") Long id) throws BusinessRuleException {
        return new ResponseEntity<>(taskLogService.deleteEntity(id), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/all/logs")
    public ResponseEntity<Boolean> deleteAllTaskLog() {
        return new ResponseEntity<>(taskLogService.deleteAllEntity(), HttpStatus.NO_CONTENT);
    }

    @GetMapping(name = "/search-id/", value = "/{idTaskLog}")
    public ResponseEntity<TaskLogResponseDTO> findByIdTaskLog(@PathVariable(name = "idTaskLog") Long id) throws BusinessRuleException {
        return new ResponseEntity<>(taskLogService.findByIdEntity(id), HttpStatus.OK);
    }

    @GetMapping(name = "/search-all")
    public ResponseEntity<Page<TaskLogResponseDTO>> findByIdTaskLog(Pageable pageable) throws BusinessRuleException {
        return new ResponseEntity<>(taskLogService.findAllEntity(pageable), HttpStatus.OK);
    }
}
