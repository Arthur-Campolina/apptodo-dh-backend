package com.arthurcampolina.ToDO.services.impl;


import com.arthurcampolina.ToDO.dtos.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface TaskServiceImpl {
    Page<TaskDTO> findAll(Pageable pageable);
    TaskDTO findById(Integer id);
    TaskDTO save(TaskDTO dto, Authentication auth);
    TaskDTO update(Integer id, TaskDTO dto);
    void delete(Integer id);
}
