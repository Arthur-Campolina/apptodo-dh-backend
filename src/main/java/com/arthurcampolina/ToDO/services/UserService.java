package com.arthurcampolina.ToDO.services;

import com.arthurcampolina.ToDO.dtos.UserDTO;
import com.arthurcampolina.ToDO.dtos.UserDTO;
import com.arthurcampolina.ToDO.entities.User;
import com.arthurcampolina.ToDO.entities.User;
import com.arthurcampolina.ToDO.exceptions.DataBaseException;
import com.arthurcampolina.ToDO.exceptions.NotFoundException;
import com.arthurcampolina.ToDO.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.arthurcampolina.ToDO.repositories.UserRepository;

@Service
public class UserService implements UserServiceImpl {

    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable page){
        Page<User> list = repository.findAll(page);
        return list.map(UserDTO::new);
    }

    public UserDTO findById(Integer id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return new UserDTO(user);
    }

    public UserDTO update(Integer id, UserDTO dto) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        copyDtoToEntity(dto, user);
        user = repository.save(user);
        return new UserDTO(user);
    }

    public UserDTO save(UserDTO dto, Authentication auth) {
        User user = new User();
        copyDtoToEntity(dto, user);
        user = repository.save(user);
        return new UserDTO(user);
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

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
    }
}

