package com.ns.task.service.impl;

import com.ns.task.dto.CustomerInfoDto;
import com.ns.task.dto.LoginDto;
import com.ns.task.dto.Response;
import com.ns.task.entity.UserEntity;
import com.ns.task.repository.RegistrationRepository;
import com.ns.task.service.ILoginService;
import com.ns.task.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LoginServiceImplTest {
    @Autowired
    private ILoginService loginService;
    @Mock
    private RegistrationRepository registrationRepository;
    private UserEntity customerInfoDto;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerInfoDto = new UserEntity(1, "Giridhar", "giri1@gmail.com", "123456", 9515141929L,null,null,null);
        loginService = new LoginServiceImpl(registrationRepository,passwordEncoder,jwtService);
    }

    @Test
    void loginWithCorrectCredentials() {
        LoginDto loginDto = new LoginDto("giridhar1@gmail.com", "123456");

        when(registrationRepository.findByEmail(any(String.class))).
                thenReturn(Optional.of(customerInfoDto));
        when(jwtService.generateToken(any(String.class),any(Integer.class))).thenReturn("Token Generated");
        when(passwordEncoder.matches(any(String.class),any(String.class))).thenReturn(true);
        Response<CustomerInfoDto> loginResponse = loginService.loginUser(loginDto);
        assertEquals(loginResponse.getResponseDescription(),"Login Successful");
        assertNotNull(loginResponse.getData());
    }

    @Test
    void testLoginWithInCorrectCredentials() {
        when(registrationRepository.findByEmail(any(String.class))).
                thenReturn(Optional.empty());
        LoginDto loginDto = new LoginDto("giridhar1@gmail.com", "1234567");
        when(jwtService.generateToken(any(String.class),any(Integer.class))).thenReturn("Token Generated");
        when(passwordEncoder.matches(any(String.class),any(String.class))).thenReturn(false);

        Response<CustomerInfoDto> loginResponse = loginService.loginUser(loginDto);
        assertNull(loginResponse.getData());
        assertEquals(loginResponse.getResponseDescription(),"Login in failed,Please enter correct credentials");
    }

}