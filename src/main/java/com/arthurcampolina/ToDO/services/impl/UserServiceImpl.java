package com.arthurcampolina.ToDO.services.impl;

import com.arthurcampolina.ToDO.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserServiceImpl {
    Page<UserDTO> findAll(Pageable pageable);
    UserDTO findById(Integer id);
    UserDTO save(UserDTO dto, Authentication auth);
    UserDTO update(Integer id, UserDTO dto);
    void delete(Integer id);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
