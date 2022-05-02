package com.arthurcampolina.ToDO.services;

import com.arthurcampolina.ToDO.dtos.TaskDTO;
import com.arthurcampolina.ToDO.entities.Task;
import com.arthurcampolina.ToDO.entities.Task;
import com.arthurcampolina.ToDO.entities.User;
import com.arthurcampolina.ToDO.exceptions.DataBaseException;
import com.arthurcampolina.ToDO.exceptions.NotFoundException;
import com.arthurcampolina.ToDO.repositories.TaskRepository;
import com.arthurcampolina.ToDO.dtos.TaskDTO;
import com.arthurcampolina.ToDO.services.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TaskService implements TaskServiceImpl {

    @Autowired
    private TaskRepository repository;

    @Transactional(readOnly = true)
    public Page<TaskDTO> findAll(Pageable page){
        Page<Task> list = repository.findAll(page);
        return list.map(TaskDTO::new);
    }

    public TaskDTO findById(Integer id) {
        Task task = repository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        return new TaskDTO(task);
    }

    public TaskDTO update(Integer id, TaskDTO dto) {
        Task task = repository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        copyDtoToEntity(dto, task);
        task = repository.save(task);
        return new TaskDTO(task);
    }

    public TaskDTO save(TaskDTO dto, Authentication auth) {
        Task task = new Task();
        copyDtoToEntity(dto, task);
        task = repository.save(task);
        return new TaskDTO(task);
    }

    public void delete(Integer id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Id não encontrado: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Violação de integridade.");
        }
    }

    private void copyDtoToEntity(TaskDTO dto, Task entity) {
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setCompleted(dto.getCompleted());
        entity.setCreatedAt(dto.getCreatedAt());
    }
}

