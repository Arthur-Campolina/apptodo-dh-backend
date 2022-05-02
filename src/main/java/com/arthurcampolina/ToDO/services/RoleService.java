package com.arthurcampolina.ToDO.services;
import com.arthurcampolina.ToDO.entities.Role;
import com.arthurcampolina.ToDO.exceptions.DataBaseException;
import com.arthurcampolina.ToDO.dtos.RoleDTO;
import com.arthurcampolina.ToDO.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.arthurcampolina.ToDO.repositories.RoleRepository;
import com.arthurcampolina.ToDO.services.impl.RoleServiceImpl;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleService implements RoleServiceImpl {
    private final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Page<RoleDTO> findAll(Pageable pageable) {
        Page<Role> roles = repository.findAll(pageable);
        return roles.map(RoleDTO::new);
    }

    public Page<String> findAllRoleNames(Pageable pageable) {
        Page<Role> roles = repository.findAll(pageable);
        return roles.map(Role::getAuthority);
    }

    public RoleDTO findById(Integer id) {
        Role role = repository.findById(id).orElseThrow(() -> new NotFoundException("Role not found"));
        return new RoleDTO(role);
    }

    public RoleDTO update(Integer id, RoleDTO dto) {
        Role role = repository.findById(id).orElseThrow(() -> new NotFoundException("Role not found"));
        copyDtoToEntity(dto, role);
        role = repository.save(role);
        return new RoleDTO(role);
    }

    public RoleDTO save(RoleDTO dto) {
        Role role = new Role();
        copyDtoToEntity(dto, role);
        role = repository.save(role);
        return new RoleDTO(role);
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

    private void copyDtoToEntity(RoleDTO dto, Role role) {
        role.setAuthority(dto.getAuthority());
        role.setDescription(dto.getDescription());
    }


}
