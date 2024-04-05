package com.natwest.CapstoneUserService.controller;

import com.natwest.CapstoneUserService.dto.LoginDto;
import com.natwest.CapstoneUserService.entity.User;
import com.natwest.CapstoneUserService.exception.UserNotFoundException;
import com.natwest.CapstoneUserService.response.LoginResponse;
import com.natwest.CapstoneUserService.service.AuthenticationService;
import com.natwest.CapstoneUserService.service.JwtService;
import com.natwest.CapstoneUserService.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private AuthenticationService authenticationService;



    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginDto) throws UserNotFoundException {
        User authenticatedUser = authenticationService.authenticate(loginDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}
