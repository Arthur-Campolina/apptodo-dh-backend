package com.arthurcampolina.ToDO.services;

import com.arthurcampolina.ToDO.dtos.TaskDTO;
import com.arthurcampolina.ToDO.entities.Task;
import com.arthurcampolina.ToDO.entities.User;
import com.arthurcampolina.ToDO.exceptions.DataBaseException;
import com.arthurcampolina.ToDO.exceptions.NotFoundException;
import com.arthurcampolina.ToDO.repositories.TaskRepository;
import com.arthurcampolina.ToDO.repositories.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<TaskDTO> findAll(Pageable page, Authentication auth){
        User user = userRepository.findByEmail(auth.getName());
        if(user == null) {
            throw new NotFoundException("User not found!");
        }
        Page<Task> list = repository.findAllByUser(user, page);
        return list.map(TaskDTO::new);
    }

    public TaskDTO findById(Integer id, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName());
        if(user == null) {
            throw new NotFoundException("User not found!");
        }
        Task task = repository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        if (!task.getUser().getId().equals(user.getId())) {
            throw new NotFoundException("Unauthorized");
        }
        return new TaskDTO(task);
    }

    public TaskDTO update(Integer id, TaskDTO dto, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName());
        if(user == null) {
            throw new NotFoundException("User not found!");
        }
        Task task = repository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        if (!task.getUser().getId().equals(user.getId())) {
            throw new NotFoundException("Unauthorized");
        }
        copyDtoToEntity(dto, task);
        task = repository.save(task);
        return new TaskDTO(task);
    }

    public TaskDTO save(TaskDTO dto, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName());
        if(user == null) {
            throw new NotFoundException("User not found!");
        }
        Task task = new Task();
        copyDtoToEntity(dto, task);
        task.setUser(user);
        task = repository.save(task);
        return new TaskDTO(task);
    }

    public void updateStatus(Integer id,  Authentication auth) {
        Task task = repository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        if(task == null) {
            throw new NotFoundException("Task not found");
        }
        if(task.getCompleted()) {
            task.setCompleted(false);
            repository.save(task);
        } else {
            task.setCompleted(true);
            repository.save(task);
        }
    }

    public void delete(Integer id,  Authentication auth) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Id não encontrado: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Violação de integridade.");
        }
    }

    private void copyDtoToEntity(TaskDTO dto, Task entity) {

        entity.setDescription(dto.getDescription());
        entity.setCompleted(dto.getCompleted());
    }
}

