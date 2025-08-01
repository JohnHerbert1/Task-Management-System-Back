package com.jadson.services;

import java.util.List;

import com.jadson.exceptions.TaskNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.jadson.dto.requests.TaskRequestDTO;
import com.jadson.dto.responses.TaskResponseDTO;
import com.jadson.models.entities.Task;
import com.jadson.repositories.TaskRepository;
import com.jadson.utilities.TaskMapper;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDTO findById(Long idTask) {
        return taskMapper.toTaskDTO(returnTask(idTask));
    }

    @Override
    public List<TaskResponseDTO> findAll() {
        return taskMapper.toTaskListDTO(taskRepository.findAll());
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Task task = taskMapper.toTask(taskRequestDTO);
        return taskMapper.toTaskDTO(taskRepository.save(task));
    }

    public TaskResponseDTO updateTask(TaskRequestDTO taskRequestDTO, Long idTask) {
        // 1. Buscar a tarefa existente pelo ID
        Task existingTask = taskRepository.findById(idTask)
                .orElseThrow(() -> new RuntimeException("Task not found for update with ID: " + idTask));
        // Substitua RuntimeException por uma exceção customizada (ex: ResourceNotFoundException)
        // e trate-a com um @ControllerAdvice para retornar 404 Not Found.

        // Atualiza os campos da tarefa existente APENAS SE o novo valor não for nulo/vazio,
        if (taskRequestDTO.getTitle() != null && !taskRequestDTO.getTitle().trim().isEmpty()) {
            existingTask.setTitle(taskRequestDTO.getTitle());
        }

        if (taskRequestDTO.getDescription() != null && !taskRequestDTO.getDescription().trim().isEmpty()) {
            existingTask.setDescription(taskRequestDTO.getDescription());
        }

        if (taskRequestDTO.getDate() != null) {
            existingTask.setDate(taskRequestDTO.getDate());
        }

        if (taskRequestDTO.getHour() != null) {
            existingTask.setHour(taskRequestDTO.getHour());
        }

        // Salva a tarefa atualizada no banco de dados
        Task updatedTask = taskRepository.save(existingTask);

        // Converte a entidade atualizada para ResponseDTO e retorna
        return new TaskResponseDTO(updatedTask);
    }

    @Override
    public String deleteTask(Long idTask) {
        taskRepository.deleteById(idTask);
        return "Task id: " + idTask + " deleted";
    }

    @Override
    public String deleteAllTasks() {
        taskRepository.deleteAll();
        return "All tasks deleted";
    }

    // Método para encontrar tarefa no banco de dados
    //  Method to find task in database
    private Task returnTask(Long idTask) {
        return taskRepository.findById(idTask)
                .orElseThrow(() -> new TaskNotFoundException(idTask));
    }

}
