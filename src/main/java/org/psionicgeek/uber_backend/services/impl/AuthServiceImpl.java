package org.psionicgeek.uber_backend.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.psionicgeek.uber_backend.dto.DriverDto;
import org.psionicgeek.uber_backend.dto.SignupDto;
import org.psionicgeek.uber_backend.dto.UserDto;
import org.psionicgeek.uber_backend.entities.User;
import org.psionicgeek.uber_backend.entities.enums.Role;
import org.psionicgeek.uber_backend.exceptions.RuntimeConflictException;
import org.psionicgeek.uber_backend.repositories.UserRepository;
import org.psionicgeek.uber_backend.services.AuthService;
import org.psionicgeek.uber_backend.services.RiderService;
import org.psionicgeek.uber_backend.services.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final WalletService walletService;


    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    @Transactional
    public UserDto signup(SignupDto signupDto) {
        userRepository.findByEmail(signupDto.getEmail()).ifPresent(user -> {
            throw new RuntimeConflictException("User already exists");
        });


        String password = signupDto.getPassword();
        String hashedPassword = password; // TODO: hash the password
        signupDto.setPassword(hashedPassword);
        User user = modelMapper.map(signupDto, User.class);
        user.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepository.save(user);
        riderService.createRider(savedUser);
        //TODO: add wallet related service
        walletService.createNewWallet(savedUser);
        return modelMapper.map(savedUser, UserDto.class);


    }

    @Override
    public DriverDto onboardNewDriver(Long userId) {
        return null;
    }
}
