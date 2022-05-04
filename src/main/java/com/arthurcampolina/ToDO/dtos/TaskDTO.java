package com.arthurcampolina.ToDO.dtos;

import com.arthurcampolina.ToDO.entities.Task;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Integer id;
    private String description;
    private Boolean completed;

    public TaskDTO(Task entity) {

        this.id = entity.getId();
        this.description = entity.getDescription();
        this.completed = entity.getCompleted();
    }
}