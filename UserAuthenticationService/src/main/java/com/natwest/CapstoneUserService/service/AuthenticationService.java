package com.natwest.CapstoneUserService.service;

import com.natwest.CapstoneUserService.dto.LoginDto;
import com.natwest.CapstoneUserService.dto.RegisterDto;
import com.natwest.CapstoneUserService.dto.UserDto;
import com.natwest.CapstoneUserService.entity.User;
import com.natwest.CapstoneUserService.exception.UserAlreadyExistException;
import com.natwest.CapstoneUserService.exception.UserNotFoundException;
import com.natwest.CapstoneUserService.repository.UserRepository;
import com.natwest.CapstoneUserService.utility.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User authenticate(LoginDto input) throws UserNotFoundException {
        User user = userRepository.findByEmail(input.getEmail());
        if (user == null) throw new UserNotFoundException(AppConstant.USER_NOT_FOUND_MESSAGE);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail());
    }
}
