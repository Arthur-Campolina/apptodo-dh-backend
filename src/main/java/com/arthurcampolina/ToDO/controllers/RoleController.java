package com.arthurcampolina.ToDO.controllers;

import com.arthurcampolina.ToDO.dtos.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.arthurcampolina.ToDO.services.impl.RoleServiceImpl;

import java.net.URI;

@RestController
@RequestMapping("/roles")
public class RoleController {


    private final RoleServiceImpl service;

    @Autowired
    public RoleController(RoleServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<RoleDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<String>> findAllRoleNames(Pageable pageable) {
        return ResponseEntity.ok(service.findAllRoleNames(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<RoleDTO> novo (@RequestBody RoleDTO roleDTO){
        RoleDTO role = service.save(roleDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(role.getId()).toUri();
        return ResponseEntity.created(uri).body(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> update (@PathVariable Integer id, @RequestBody RoleDTO roleDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, roleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

