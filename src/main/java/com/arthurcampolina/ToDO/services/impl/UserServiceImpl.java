package com.arthurcampolina.ToDO.services.impl;

import com.arthurcampolina.ToDO.dtos.UserDTO;
import com.arthurcampolina.ToDO.dtos.UserEditDTO;
import com.arthurcampolina.ToDO.dtos.UserFindDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserServiceImpl {
    Page<UserDTO> findAll(Pageable pageable);
    UserDTO findById(Integer id);
    UserDTO save(UserEditDTO dto);
    UserDTO update(Integer id, UserEditDTO dto);
    void delete(Integer id, Authentication auth);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
