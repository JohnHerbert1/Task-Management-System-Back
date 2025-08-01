package com.jadson.dao;

import com.jadson.dto.responses.TaskResponseDTO;

public interface DAO {

    public void createTaskInDatabase(TaskResponseDTO taskResponseDTO);

    public void saveTaskInDatabase(TaskResponseDTO taskResponseDTO);

    public void updateTaskInDatabase(TaskResponseDTO taskResponseDTO);

    public void deleteTaskInDatabase(TaskResponseDTO taskResponseDTO);

}
