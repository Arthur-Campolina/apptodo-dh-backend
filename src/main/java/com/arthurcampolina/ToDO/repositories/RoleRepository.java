package com.arthurcampolina.ToDO.repositories;

import com.arthurcampolina.ToDO.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByAuthority (String authority);
}
