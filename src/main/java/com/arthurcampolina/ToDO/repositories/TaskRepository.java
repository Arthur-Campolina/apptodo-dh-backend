package com.arthurcampolina.ToDO.repositories;

import com.arthurcampolina.ToDO.entities.Task;
import com.arthurcampolina.ToDO.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    Page<Task> findAllByUser(User user, Pageable pageable);
}
