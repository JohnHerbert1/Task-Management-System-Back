package com.jadson.controllers;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.jadson.dto.requests.TaskRequestDTO;
import com.jadson.dto.responses.TaskResponseDTO;
import com.jadson.services.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskService taskService;

    @GetMapping (value = "/{idTask}")
    public ResponseEntity<TaskResponseDTO> findById(@PathVariable Long idTask) {
        return ResponseEntity.ok().body(taskService.findById(idTask));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO, UriComponentsBuilder uriComponentsBuilder) {

        TaskResponseDTO taskResponseDTO = taskService.createTask(taskRequestDTO);

        URI uri = uriComponentsBuilder.path("/tasks/{id}").buildAndExpand(taskResponseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(taskResponseDTO);
    }

    @PutMapping (value = "/{idTask}")
    public ResponseEntity<TaskResponseDTO> updateTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO, @PathVariable Long idTask) {
        return ResponseEntity.ok(taskService.updateTask(taskRequestDTO, idTask));
    }

    @DeleteMapping (value = "/{idTask}")
    public ResponseEntity<String> deleteTask(@PathVariable Long idTask) {
        return ResponseEntity.ok(taskService.deleteTask(idTask));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllTasks() {
        return ResponseEntity.ok(taskService.deleteAllTasks());
    }

}
