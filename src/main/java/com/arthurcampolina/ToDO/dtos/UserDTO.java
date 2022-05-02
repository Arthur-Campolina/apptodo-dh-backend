package com.arthurcampolina.ToDO.dtos;
import com.arthurcampolina.ToDO.entities.Task;
import com.arthurcampolina.ToDO.entities.User;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Setter(AccessLevel.PROTECTED)
    private Set<TaskDTO> tasks = new LinkedHashSet<>();

    public UserDTO(User entity) {

        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        entity.getTasks().forEach(task -> this.addTask(new TaskDTO(task)));
    }

    public void addTask(TaskDTO dto) {
        this.tasks.add(dto);
    }

    public void removeTask(TaskDTO dto) {
        this.tasks.remove(dto);
    }
}
