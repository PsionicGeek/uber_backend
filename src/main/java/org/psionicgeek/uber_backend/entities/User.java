package org.psionicgeek.uber_backend.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.psionicgeek.uber_backend.entities.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "app_user", indexes = {
        @Index(name = "idx_user_email", columnList = "email")}

)

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(
                role->new SimpleGrantedAuthority("ROLE_"+role.name())
        ).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }
}
