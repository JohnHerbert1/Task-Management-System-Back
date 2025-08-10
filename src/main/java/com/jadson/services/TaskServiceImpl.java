package com.jadson.services;

import java.util.List;

import com.jadson.exceptions.TaskNotFoundException;
import com.jadson.models.entities.User;
import com.jadson.repositories.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Usuário não autenticado");
        }

        Object principal = auth.getPrincipal();

        // 1) Se já for a entidade User (quando você colocou o entity como principal)
        if (principal instanceof com.jadson.models.entities.User) {
            return (com.jadson.models.entities.User) principal;
        }

        // 2) Se for UserDetails (implementação do Spring Security)
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByEmail(username)
                    .orElseThrow(() -> new AccessDeniedException("Usuário não encontrado: " + username));
        }

        // 3) Se for String (ex.: "anonymousUser" ou apenas o username), tenta pelo auth.getName()
        String username = auth.getName();
        if (username != null && !username.isBlank()) {
            return userRepository.findByEmail(username)
                    .orElseThrow(() -> new AccessDeniedException("Usuário não encontrado: " + username));
        }

        throw new AccessDeniedException("Não foi possível obter o usuário corrente");
    }

    @Override
    public TaskResponseDTO findById(Long idTask) {
        return taskMapper.toTaskDTO(returnTask(idTask));
    }

    public List<TaskResponseDTO> findAll() {
        // Retorna apenas as tarefas do usuário logado
        User currentUser = getCurrentUser();
        return taskMapper.toTaskListDTO(taskRepository.findByUser(currentUser));
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        User currentUser = getCurrentUser();
        Task task = taskMapper.toTask(taskRequestDTO);
        task.setUser(currentUser);//salva tesk especifica do usuario logado.
        return taskMapper.toTaskDTO(taskRepository.save(task));
    }

    public TaskResponseDTO updateTask(TaskRequestDTO taskRequestDTO, Long idTask) {
        User currentUser = getCurrentUser();
        // ↘️ FILTRO POR USUÁRIO
        Task existingTask = taskRepository.findByIdAndUser(idTask, currentUser)
                .orElseThrow(() -> new TaskNotFoundException(idTask));

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

        User currentUser = getCurrentUser();
        // ↘️ FILTRO POR USUÁRIO
        Task existingTask = taskRepository.findByIdAndUser(idTask, currentUser)
                .orElseThrow(() -> new TaskNotFoundException(idTask));

        taskRepository.deleteById(idTask);
        return "Task id: " + idTask + " deleted";
    }

    @Override
    public String deleteAllTasks() {
        User currentUser = getCurrentUser();
        // ↘️ DELETA SÓ AS TASKS DO USUÁRIO
        taskRepository.deleteAllByUser(currentUser);
        return "All tasks of current user deleted";
    }

    // Método para encontrar tarefa no banco de dados
    //  Method to find task in database
    private Task returnTask(Long idTask) {
        User currentUser = getCurrentUser();
        return taskRepository.findByIdAndUser(idTask, currentUser)
                .orElseThrow(() -> new TaskNotFoundException(idTask));
    }

}
