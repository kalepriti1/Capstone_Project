package com.natwest.CapstoneUserService.service;

import com.natwest.CapstoneUserService.dto.RegisterDto;
import com.natwest.CapstoneUserService.dto.UserDto;
import com.natwest.CapstoneUserService.entity.User;
import com.natwest.CapstoneUserService.exception.*;

public interface UserService {
    public boolean validateEmailId(String email);
    public boolean validateName(String name);
    public boolean validateMobileNo(Long mobileNo);
    public boolean validatePassword(String password);
    public UserDto signup(RegisterDto input) throws UserAlreadyExistException, InvalidEmailException, InvalidNameException, InvalidPhoneNoException, InvalidPasswordException;
    public User getUser(String email) throws UserNotFoundException;
    public String removeUser(String email) throws UserNotFoundException;
}
