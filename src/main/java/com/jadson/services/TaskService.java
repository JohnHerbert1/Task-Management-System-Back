package com.jadson.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.jadson.dto.requests.TaskRequestDTO;
import com.jadson.dto.responses.TaskResponseDTO;

@Service
public interface TaskService {

    TaskResponseDTO findById(Long idTask);

    List<TaskResponseDTO> findAll();

    TaskResponseDTO createTask(TaskRequestDTO registerTaskDTO);

    TaskResponseDTO updateTask(TaskRequestDTO registerTaskDTO, Long idTask);

    String deleteTask(Long idTask);

    String deleteAllTasks();

}
