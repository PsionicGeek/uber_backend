package org.psionicgeek.uber_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.psionicgeek.uber_backend.entities.enums.Role;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;
    private String email;
    private Set<Role> roles;
}
