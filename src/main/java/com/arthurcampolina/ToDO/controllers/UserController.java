package com.arthurcampolina.ToDO.controllers;


import com.arthurcampolina.ToDO.dtos.UserDTO;
import com.arthurcampolina.ToDO.dtos.UserEditDTO;
import com.arthurcampolina.ToDO.dtos.UserFindDTO;
import com.arthurcampolina.ToDO.services.impl.UserServiceImpl;
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
@RequestMapping(value = "/users")
public class UserController {

    private final UserServiceImpl service;

    @Autowired
    public UserController(UserServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        Page<UserDTO> page = service.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> novo (@RequestBody UserEditDTO userDTO){
        UserDTO user = service.save(userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update (@PathVariable Integer id, @RequestBody UserEditDTO userDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id, Authentication auth){
        service.delete(id, auth);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
