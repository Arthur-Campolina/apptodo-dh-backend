package com.arthurcampolina.ToDO.services;

import com.arthurcampolina.ToDO.dtos.UserDTO;
import com.arthurcampolina.ToDO.dtos.UserEditDTO;
import com.arthurcampolina.ToDO.dtos.UserFindDTO;
import com.arthurcampolina.ToDO.entities.User;
import com.arthurcampolina.ToDO.exceptions.DataBaseException;
import com.arthurcampolina.ToDO.exceptions.NotFoundException;
import com.arthurcampolina.ToDO.repositories.UserRepository;
import com.arthurcampolina.ToDO.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserServiceImpl, UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable page){
        Page<User> list = repository.findAll(page);
        return list.map(UserDTO::new);
    }

    public UserDTO findById(Integer id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return new UserDTO(user);
    }

    public UserDTO update(Integer id, UserEditDTO dto) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        copyDtoToEntity(dto, user);
        user = repository.save(user);
        return new UserDTO(user);
    }

    public UserDTO save(UserEditDTO dto) {
        User user = new User();
        copyDtoToEntity(dto, user);
        user = repository.save(user);
        return new UserDTO(user);
    }

    public void delete(Integer id, Authentication auth) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Id não encontrado: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Violação de integridade.");
        }
    }

    private void copyDtoToEntity(UserEditDTO dto, User entity) {

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if(user == null) {
            logger.error("Usuário não encontrado: "+ username);
            throw new UsernameNotFoundException("Email não encontrado");
        }
        logger.info("Usuário encontrado: "+ username);
        return user;
    }
}

