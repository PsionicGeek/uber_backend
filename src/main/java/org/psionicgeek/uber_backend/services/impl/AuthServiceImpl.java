package org.psionicgeek.uber_backend.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.psionicgeek.uber_backend.dto.DriverDto;
import org.psionicgeek.uber_backend.dto.SignupDto;
import org.psionicgeek.uber_backend.dto.UserDto;
import org.psionicgeek.uber_backend.entities.Driver;
import org.psionicgeek.uber_backend.entities.User;
import org.psionicgeek.uber_backend.entities.enums.Role;
import org.psionicgeek.uber_backend.exceptions.ResourceNotFoundException;
import org.psionicgeek.uber_backend.exceptions.RuntimeConflictException;
import org.psionicgeek.uber_backend.repositories.UserRepository;
import org.psionicgeek.uber_backend.security.JWTService;
import org.psionicgeek.uber_backend.services.AuthService;
import org.psionicgeek.uber_backend.services.DriverService;
import org.psionicgeek.uber_backend.services.RiderService;
import org.psionicgeek.uber_backend.services.WalletService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager  authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;


    @Override
    public String[] login(String email, String password) {

       Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
       User user =(User) authentication.getPrincipal();
       String accessToken = jwtService.generateAccessToken(user);
         String refreshToken = jwtService.generateRefreshToken(user);
        return new String[]{accessToken,refreshToken};


    }

    @Override
    @Transactional
    public UserDto signup(SignupDto signupDto) {
        userRepository.findByEmail(signupDto.getEmail()).ifPresent(user -> {
            throw new RuntimeConflictException("User already exists");
        });


        String password = signupDto.getPassword();
        String hashedPassword = passwordEncoder.encode( password);
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
    public DriverDto onboardNewDriver(Long userId,String vehicleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getRoles().contains(Role.DRIVER)) {
            throw new RuntimeConflictException("User is already a driver");
        }
        Driver driver = Driver.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();
        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);
        Driver savedDriver = driverService.createNewDriver(driver);
        return modelMapper.map(savedDriver, DriverDto.class);

    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        return jwtService.generateAccessToken(user);
    }
}
