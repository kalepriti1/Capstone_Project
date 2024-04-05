package com.natwest.CapstoneUserService.service;

import com.natwest.CapstoneUserService.dto.LoginDto;
import com.natwest.CapstoneUserService.entity.User;
import com.natwest.CapstoneUserService.exception.UserNotFoundException;
import com.natwest.CapstoneUserService.repository.UserRepository;
import com.natwest.CapstoneUserService.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAuthenticate_AuthenticationSuccess() throws UserNotFoundException {
        LoginDto loginDto = new LoginDto("test@example.com", "password");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(authenticationManager.authenticate(any())).thenReturn(null);
        User authenticatedUser = authenticationService.authenticate(loginDto);
        assertNotNull(authenticatedUser);
        assertEquals("test@example.com", authenticatedUser.getEmail());
        verify(userRepository, times(2)).findByEmail("test@example.com");
        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    public void testAuthenticate_throwsUserNotFoundException() {
        LoginDto loginDto = new LoginDto("nonexistent@example.com", "password");
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> authenticationService.authenticate(loginDto));
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
        verifyNoInteractions(authenticationManager);
    }

    @Test
    public void testAuthenticate_AuthenticationFailed() {
        LoginDto loginDto = new LoginDto("test@example.com", "wrongpassword");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(authenticationManager.authenticate(any()))
                .thenThrow(new RuntimeException("Authentication failed"));

        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(loginDto));
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(authenticationManager, times(1)).authenticate(any());
    }
}
