package com.natwest.CapstoneUserService.service;
import com.natwest.CapstoneUserService.dto.RegisterDto;
import com.natwest.CapstoneUserService.dto.UserDto;
import com.natwest.CapstoneUserService.entity.User;
import com.natwest.CapstoneUserService.exception.*;
import com.natwest.CapstoneUserService.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void validateEmailId_ValidEmail_ReturnsTrue() {
        assertTrue(userServiceImpl.validateEmailId("test@example.com"));
    }

    @Test
    void validateEmailId_InvalidEmail_ReturnsFalse() {
        assertFalse(userServiceImpl.validateEmailId("invalid.email"));
    }
    @Test
    void validateName_ValidName_ReturnsTrue() {
        assertTrue(userServiceImpl.validateName("John Doe"));
    }

    @Test
    void validateName_BlankName_ReturnsFalse() {
        assertFalse(userServiceImpl.validateName(""));
    }
    @Test
    void validateMobileNo_ValidMobileNumber_ReturnsTrue() {
        assertTrue(userServiceImpl.validateMobileNo(1234567890L));
    }

    @Test
    void validateMobileNo_InvalidMobileNumber_ReturnsFalse() {
        assertFalse(userServiceImpl.validateMobileNo(123456L));
    }
    @Test
    void getUser_ExistingUser_ReturnsUser() throws UserNotFoundException {
        String email = "";
        User user = new User();
        when(userRepository.existsById(email)).thenReturn(true);
        when(userRepository.findById(email)).thenReturn(java.util.Optional.of(user));

        assertEquals(user, userServiceImpl.getUser(email));
    }

    @Test
    void getUser_NonExistingUser_ThrowsUserNotFoundException() {
        String email = "";
        when(userRepository.existsById(email)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUser(email));
    }

    @Test
    void removeUser_ExistingUser_ReturnsSuccessfullyRemoved() throws UserNotFoundException {
        String email = "test@example.com";
        User user = new User();
        when(userRepository.existsById(email)).thenReturn(true);
        when(userRepository.findById(email)).thenReturn(java.util.Optional.of(user));

        assertEquals("successfully removed", userServiceImpl.removeUser(email));
    }

    @Test
    void removeUser_NonExistingUser_ThrowsUserNotFoundException() {
        String email = "nonexisting@example.com";
        when(userRepository.existsById(email)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userServiceImpl.removeUser(email));
    }

    @Test
    void signup_ValidInput_ReturnsUserDto() throws UserAlreadyExistException, InvalidEmailException, InvalidNameException, InvalidPhoneNoException, InvalidPasswordException {
        RegisterDto input = new RegisterDto(1122334455L, "Vanshika Karan", "vans@gmai.com", "vanK@123");
        User user = new User(input.getMobileNumber(), "userId", input.getFullName(), input.getEmail(), input.getPassword());
        when(userRepository.existsById(input.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(input.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto userDto = userServiceImpl.signup(input);
        assertNotNull(userDto);
        assertEquals(input.getFullName(), userDto.getFullName());
        assertEquals(input.getEmail(), userDto.getEmail());
        assertEquals(input.getMobileNumber(), userDto.getMobileNumber());
    }

    @Test
    void signup_UserAlreadyExists_ThrowsUserAlreadyExistException() {
        RegisterDto input = new RegisterDto(1122334455L, "Vanshika Karan", "vans@gmai.com", "vanK@123");
        when(userRepository.existsById(input.getEmail())).thenReturn(true);
        assertThrows(UserAlreadyExistException.class, () -> userServiceImpl.signup(input));
    }

    @Test
    void signup_InvalidEmail_ThrowsInvalidEmailException() {
        RegisterDto input = new RegisterDto(1122334455L, "Vanshika Karan", "vans.com", "vanK@123");
        assertThrows(InvalidEmailException.class, () -> userServiceImpl.signup(input));
    }

    @Test
    void signup_InvalidName_ThrowsInvalidNameException() {
        RegisterDto input = new RegisterDto(1122334455L, "", "vans@gmai.com", "vanK@123");
        assertThrows(InvalidNameException.class, () -> userServiceImpl.signup(input));
    }

    @Test
    void signup_InvalidMobileNumber_ThrowsInvalidPhoneNoException() {
        RegisterDto input = new RegisterDto(1134455L, "Vanshika Karan", "vans@gmai.com", "vanK@123");
        assertThrows(InvalidPhoneNoException.class, () -> userServiceImpl.signup(input));
    }

    @Test
    void signup_InvalidPassword_ThrowsInvalidPasswordException() {
        RegisterDto input = new RegisterDto(1122334455L, "Vanshika Karan", "vans@gmai.com", "va vf23");
        assertThrows(InvalidPasswordException.class, () -> userServiceImpl.signup(input));
    }
}
