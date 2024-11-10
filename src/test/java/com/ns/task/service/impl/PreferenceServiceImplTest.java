package com.ns.task.service.impl;

import com.ns.task.dto.Response;
import com.ns.task.dto.UserPreferenceDto;
import com.ns.task.entity.UserEntity;
import com.ns.task.entity.UserPreferenceEntity;
import com.ns.task.repository.PreferenceRepository;
import com.ns.task.repository.RegistrationRepository;
import com.ns.task.service.IUserPreference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PreferenceServiceImplTest {
    @Autowired
    private IUserPreference userPreference;

    @Mock
    private RegistrationRepository userRepository;
    @Mock
    private PreferenceRepository preferenceRepository;
    private UserEntity user;
    private UserPreferenceEntity preference;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        userPreference = new PreferenceServiceImpl(preferenceRepository, userRepository);

        preference = new UserPreferenceEntity(2,true,false,user);
        user= new UserEntity(1,"Giri","giridhar@gmail.com","123456",9515141929L,null,null
                ,null);
    }
    @Test
    void insertNewPreference(){
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(preferenceRepository.save(any(UserPreferenceEntity.class))).thenReturn(preference);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        Response<UserPreferenceDto> userPreferenceDtoResponse = userPreference.insertUserPreference(preference, 1);
        assertNotNull(userPreferenceDtoResponse.getData());
    }

    @Test
    void insertNewPreference_WhenUserNotFound(){
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(preferenceRepository.save(any(UserPreferenceEntity.class))).thenReturn(preference);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        Response<UserPreferenceDto> userPreferenceDtoResponse = userPreference.insertUserPreference(preference, 1);
        assertNull(userPreferenceDtoResponse.getData());
    }

    @Test
    void updatePreference(){
        UserPreferenceEntity updatedPreference = new UserPreferenceEntity(1,false,false,user);
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(preferenceRepository.findById(any(Integer.class))).thenReturn(Optional.of(preference));
        when(preferenceRepository.save(any(UserPreferenceEntity.class))).thenReturn(updatedPreference);

        Response<UserPreferenceDto> userPreferenceDtoResponse = userPreference.updateUserPreference(preference, 1, 1);
        assertNotNull(userPreferenceDtoResponse.getData());
        assertEquals("Updated successful",userPreferenceDtoResponse.getResponseDescription());
    }

    @Test
    void updatePreference_WhenUserNotFound(){
        UserPreferenceEntity updatedPreference = new UserPreferenceEntity(1,false,false,user);
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(preferenceRepository.findById(any(Integer.class))).thenReturn(Optional.of(preference));
        when(preferenceRepository.save(any(UserPreferenceEntity.class))).thenReturn(updatedPreference);

        Response<UserPreferenceDto> userPreferenceDtoResponse = userPreference.updateUserPreference(preference, 1, 1);

        assertNull(userPreferenceDtoResponse.getData());
        assertEquals("No user found by the userId",userPreferenceDtoResponse.getResponseDescription());
    }
    @Test
    void updatePreference_WhenUserFound_PreferenceNotFound(){
        UserPreferenceEntity updatedPreference = new UserPreferenceEntity(1,false,false,user);
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(preferenceRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(preferenceRepository.save(any(UserPreferenceEntity.class))).thenReturn(updatedPreference);

        Response<UserPreferenceDto> userPreferenceDtoResponse = userPreference.updateUserPreference(preference, 1, 1);
        assertNull(userPreferenceDtoResponse.getData());
        assertEquals("No preferences found to update",userPreferenceDtoResponse.getResponseDescription());
    }
}