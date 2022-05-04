package com.arthurcampolina.ToDO.dtos;

import com.arthurcampolina.ToDO.entities.User;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserEditDTO extends UserDTO {

    private String password;

    public UserEditDTO(User entity) {
        super(entity);
        this.password = entity.getPassword();
    }
}
