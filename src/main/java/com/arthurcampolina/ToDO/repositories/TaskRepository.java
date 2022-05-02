package com.arthurcampolina.ToDO.repositories;

import com.arthurcampolina.ToDO.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
