package com.arthurcampolina.ToDO.entities;

import lombok.Data;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;


@Data
@Entity
@Table(name = "tb_tasks")
public class Task extends AbstractEntity {

    @Column(columnDefinition="TEXT")
    private String description;
    private Boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
