package com.arthurcampolina.ToDO.controllers;


import com.arthurcampolina.ToDO.dtos.TaskDTO;
import com.arthurcampolina.ToDO.services.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    private final TaskServiceImpl service;

    @Autowired
    public TaskController(TaskServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<TaskDTO>> findAll(Pageable pageable, Authentication auth) {
        Page<TaskDTO> page = service.findAll(pageable, auth);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> findById(@PathVariable Integer id, Authentication auth) {
        return ResponseEntity.ok(service.findById(id, auth));
    }

    @PostMapping
    public ResponseEntity<TaskDTO> novo (@RequestBody TaskDTO TaskDTO, Authentication auth){
        TaskDTO Task = service.save(TaskDTO, auth);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Task.getId()).toUri();
        return ResponseEntity.created(uri).body(Task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update (@PathVariable Integer id, @RequestBody TaskDTO TaskDTO, Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, TaskDTO, auth));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus (@PathVariable Integer id, Authentication auth){
        service.updateStatus(id, auth);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id, Authentication auth){
        service.delete(id, auth);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
