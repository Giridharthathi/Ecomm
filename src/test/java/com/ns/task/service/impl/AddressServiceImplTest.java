package com.ns.task.service.impl;

import com.ns.task.dto.AddressDto;
import com.ns.task.dto.Response;
import com.ns.task.entity.AddressEntity;
import com.ns.task.entity.UserEntity;
import com.ns.task.repository.AddressRepository;
import com.ns.task.repository.RegistrationRepository;
import com.ns.task.service.IAddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AddressServiceImplTest {
    @Autowired
    private IAddressService addressService;
    @Mock
    private RegistrationRepository registrationRepository;
    @Mock
    private AddressRepository addressRepository;
    private AddressEntity addressEntity;
    private AddressDto addressDto;
    private UserEntity user;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        addressService = new AddressServiceImpl(addressRepository,registrationRepository);
        user = new UserEntity(2,"Giri", "giridhar@gmail.com","123456",9515141929L,null,null,null);
        addressEntity = new AddressEntity(1,"1-88","ABC","HYD",523110L,"TELANGANA",user);
        addressDto = new AddressDto(1,"1-88","ABC","HYD",523110L,"TELANGANA",1,"Giri");
    }

    @Test
    void insertNewAddress(){
        when(registrationRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(registrationRepository.save(any(UserEntity.class))).thenReturn(user);
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);

        Response<AddressDto> insertResponse = addressService.insert(addressDto, 1);
        assertNotNull(insertResponse.getData());
    }

    @Test
    void insertNewAddress_WhenUserNotFound(){
        when(registrationRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(registrationRepository.save(any(UserEntity.class))).thenReturn(user);
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);

        Response<AddressDto> insertResponse = addressService.insert(addressDto, 1);
        assertNull(insertResponse.getData());
    }

    @Test
    void updateAddress(){
        AddressEntity updateAddress = new AddressEntity(1,"1-88","XYZ","HYD",523110L,"TELANGANA",user);

        when(registrationRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(addressRepository.findByAddressid(any(Integer.class))).thenReturn(Optional.of(addressEntity));
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(updateAddress);

        Response<AddressDto> addressDtoResponse = addressService.updateAddress(addressDto);
        assertNotNull(addressDtoResponse.getData());
    }

    @Test
    void updateAddress_WhenUserNotFound(){
        AddressEntity updateAddress = new AddressEntity(1,"1-88","XYZ","HYD",523110L,"TELANGANA",user);

        when(registrationRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(addressRepository.findByAddressid(any(Integer.class))).thenReturn(Optional.of(addressEntity));
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(updateAddress);

        Response<AddressDto> addressDtoResponse = addressService.updateAddress(addressDto);
        assertNull(addressDtoResponse.getData());
    }

    @Test
    void updateAddress_WhenUserFound_AddressNotFound(){
        AddressEntity updateAddress = new AddressEntity(1,"1-88","XYZ","HYD",523110L,"TELANGANA",user);

        when(registrationRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(addressRepository.findByAddressid(any(Integer.class))).thenReturn(Optional.empty());
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(updateAddress);

        Response<AddressDto> addressDtoResponse = addressService.updateAddress(addressDto);
        assertNull(addressDtoResponse.getData());
    }

    @Test
    void getUserSpecificAddress(){
        when(addressRepository.findByUserid_Fk(any(Integer.class))).thenReturn(Arrays.asList(addressEntity));
        Response<List<AddressDto>> allUserAddress = addressService.getAllUserAddress(1);
        assertNotNull(allUserAddress.getData());
    }

    @Test
    void getUserSpecificAddress_WhenNoAddressFound(){
        when(addressRepository.findByUserid_Fk(any(Integer.class))).thenReturn(null);
        Response<List<AddressDto>> allUserAddress = addressService.getAllUserAddress(1);
        assertNull(allUserAddress.getData());
    }

}