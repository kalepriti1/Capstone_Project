package com.natwest.CapstoneUserService.controller;


import com.natwest.CapstoneUserService.dto.RegisterDto;
import com.natwest.CapstoneUserService.dto.UserDto;
import com.natwest.CapstoneUserService.entity.User;
import com.natwest.CapstoneUserService.exception.*;
import com.natwest.CapstoneUserService.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {
    private final UserServiceImpl userServiceImpl;
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
    @PostMapping("/signup")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto registerDto) throws UserAlreadyExistException, InvalidNameException, InvalidPhoneNoException, InvalidPasswordException, InvalidEmailException {
        UserDto registeredUser = userServiceImpl.signup(registerDto);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/details")
    public ResponseEntity<User> getDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/deactivation")
    public ResponseEntity<String> deactivateUser() throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        userServiceImpl.removeUser(currentUser.getEmail());
        return new ResponseEntity<>("Successfully deactivated", HttpStatus.OK);
    }

}
