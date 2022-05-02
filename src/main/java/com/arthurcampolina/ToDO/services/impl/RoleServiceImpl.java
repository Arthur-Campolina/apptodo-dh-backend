package com.arthurcampolina.ToDO.services.impl;

import com.arthurcampolina.ToDO.dtos.RoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface RoleServiceImpl {

    Page<RoleDTO> findAll(Pageable pageable);
    RoleDTO findById(Integer id);
    RoleDTO update(Integer id, RoleDTO roleDTO);
    RoleDTO save(RoleDTO roleDTO);
    void delete(Integer id);
    Page<String> findAllRoleNames(Pageable pageable);
}
