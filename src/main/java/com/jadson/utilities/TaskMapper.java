package com.jadson.utilities;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.jadson.dto.requests.TaskRequestDTO;
import com.jadson.dto.responses.TaskResponseDTO;
import com.jadson.models.entities.Task;

@Component
public class TaskMapper {

    public Task toTask(TaskRequestDTO taskRequestDTO) {
        return Task.builder()
        .title(taskRequestDTO.getTitle())
        .description(taskRequestDTO.getDescription())
        .date(taskRequestDTO.getDate())
        .hour(taskRequestDTO.getHour())
        .build();
    }

    public TaskResponseDTO toTaskDTO(Task task) {
        return new TaskResponseDTO(task);
    }

    public List<TaskResponseDTO> toTaskListDTO(List<Task> taskList) {
        return taskList.stream().map(TaskResponseDTO::new).collect(Collectors.toList());
    }

    public void updateTaskData(Task task, TaskRequestDTO taskRequestDTO) {
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setDate(taskRequestDTO.getDate());
        task.setHour(taskRequestDTO.getHour());
    }

}
