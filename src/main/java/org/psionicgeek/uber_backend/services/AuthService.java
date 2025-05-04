package org.psionicgeek.uber_backend.services;

import org.psionicgeek.uber_backend.dto.DriverDto;
import org.psionicgeek.uber_backend.dto.SignupDto;
import org.psionicgeek.uber_backend.dto.UserDto;

public interface AuthService {
    String[] login(String email, String password);
    UserDto signup(SignupDto signupDto);

    DriverDto onboardNewDriver(Long userId,String vehicleId);

    String refreshToken(String refreshToken);
}
