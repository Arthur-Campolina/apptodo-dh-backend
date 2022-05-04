package com.arthurcampolina.ToDO.dtos;

import com.arthurcampolina.ToDO.entities.User;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserFindDTO extends UserDTO {

    @Setter(AccessLevel.PROTECTED)
    private Set<TaskDTO> tasks = new LinkedHashSet<>();

    public UserFindDTO(User entity) {
        super(entity);
        entity.getTasks().forEach(task -> this.addTask(new TaskDTO(task)));
    }

    public void addTask(TaskDTO dto) {
        this.tasks.add(dto);
    }

    public void removeTask(TaskDTO dto) {
        this.tasks.remove(dto);
    }
}
