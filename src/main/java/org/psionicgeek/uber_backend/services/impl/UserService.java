package org.psionicgeek.uber_backend.services.impl;

import lombok.RequiredArgsConstructor;
import org.psionicgeek.uber_backend.entities.User;
import org.psionicgeek.uber_backend.exceptions.ResourceNotFoundException;
import org.psionicgeek.uber_backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElse(null);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
             "User not found with id: "+id
        ));
    }
}
