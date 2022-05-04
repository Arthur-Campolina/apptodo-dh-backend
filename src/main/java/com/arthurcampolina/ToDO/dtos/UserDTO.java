package com.arthurcampolina.ToDO.dtos;

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

    public UserDTO(User entity) {

        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
    }
}
