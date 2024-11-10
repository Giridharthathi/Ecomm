package com.ns.task.service.impl;

import com.ns.task.dto.CustomerInfoDto;
import com.ns.task.dto.Response;
import com.ns.task.dto.UpdateDetailDto;
import com.ns.task.entity.UserEntity;
import com.ns.task.mapper.MapperClass;
import com.ns.task.repository.RegistrationRepository;
import com.ns.task.service.IRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    @Autowired
    private IRegistrationService registrationService;
    @Mock
    private RegistrationRepository registrationRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserEntity userEntity;
    private UpdateDetailDto userUpdateDetails;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registrationService = new RegistrationServiceImpl(registrationRepository,passwordEncoder);
        userEntity = new UserEntity(1,"Giri","giridhar@gmail.com","123456",4445557771L,null,null,null);
        userUpdateDetails = new UpdateDetailDto(1,"Giri","giridhar@gmail.com","1234567",4445557771L,null);
    }

    @Test
    void registerNewUser() {
        when(registrationRepository.findByEmail(any(String.class))).thenReturn(Optional.ofNullable(null));
        when(registrationRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(passwordEncoder.encode(any(String.class))).thenReturn("Password Endcoded");
        Response<Boolean> registerResponse = registrationService.registerNewUser(userEntity);
        assertTrue(registerResponse.getData());
        assertEquals("User saved successful",registerResponse.getResponseDescription());
    }

    @Test
    void userRegisterWhenAlreadyPresent_GivesFalse() {
        when(registrationRepository.findByEmail(any(String.class))).thenReturn(Optional.ofNullable(userEntity));
        when(registrationRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(passwordEncoder.encode(any(String.class))).thenReturn("Password Endcoded");
        Response<Boolean> registerResponse = registrationService.registerNewUser(userEntity);

        assertFalse(registerResponse.getData());
        assertEquals("already a user present with the mail-Id",registerResponse.getResponseDescription());
    }

    @Test
    void updateSuccess(){
        when(registrationRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(userEntity));

        UserEntity userEntityDetails = MapperClass.INSTANCE.dtoToEntity(userUpdateDetails);

        when(registrationRepository.save(any(UserEntity.class)))
                .thenReturn(userEntityDetails);
        
        Response<CustomerInfoDto> updateResponse = registrationService.updateDetails(userUpdateDetails);
        assertEquals("1234567",updateResponse.getData().getPassword());
        assertEquals("Updated successful",updateResponse.getResponseDescription());
    }

    @Test
    void updateScenarioWhenUserNotFound(){
        when(registrationRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        Response<CustomerInfoDto> updateResponse = registrationService.updateDetails(userUpdateDetails);
        assertNull(updateResponse.getData());
        assertEquals("Updation failed due to no user found by the id",updateResponse.getResponseDescription());

    }
}