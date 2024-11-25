package org.psionicgeek.uber_backend.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.psionicgeek.uber_backend.entities.enums.Role;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    Set<Role> roles;

}
