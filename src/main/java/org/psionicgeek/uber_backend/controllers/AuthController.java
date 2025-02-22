package org.psionicgeek.uber_backend.controllers;


import lombok.RequiredArgsConstructor;
import org.psionicgeek.uber_backend.dto.SignupDto;
import org.psionicgeek.uber_backend.dto.UserDto;
import org.psionicgeek.uber_backend.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    UserDto signUp(@RequestBody SignupDto signupDto){
        return authService.signup(signupDto);
    }
}
