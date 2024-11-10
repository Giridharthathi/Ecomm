package com.ns.task.service.impl;

import com.ns.task.dto.Response;
import com.ns.task.entity.AddressEntity;
import com.ns.task.entity.UserEntity;
import com.ns.task.dto.AddressDto;
import com.ns.task.mapper.AddressMapper;
import com.ns.task.repository.AddressRepository;
import com.ns.task.repository.RegistrationRepository;
import com.ns.task.service.IAddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements IAddressService {

    private final AddressRepository addressRepository;
    private final RegistrationRepository userRepository;

    @Override
    public  Response<AddressDto> insert(AddressDto addressEntityDto, int id) {
        Response<AddressDto> insertResponse = new Response<>();
        Optional<UserEntity> userExist = userRepository.findById(id);
        if(userExist.isPresent()){
            insertResponse.setData(new AddressDto());
            AddressEntity address = AddressMapper.INSTANCE.dtoToAddressEntity(addressEntityDto);
            address.setUserid_Fk(userExist.get());
            AddressEntity save = addressRepository.save(address);
            AddressDto addressDto = AddressMapper.INSTANCE.addressEntityToDto(save);
            insertResponse.setData(addressDto);
            insertResponse.setResponseDescription("New Address inserted successfully");
        }else{
            insertResponse.setResponseDescription("insertion failed due to no user found by userId");
        }
        return insertResponse;
    }

    @Override
    public Response<AddressDto> updateAddress(AddressDto updateAddressEntity) {
        Response<AddressDto> updateResponse = new Response<>();

        // Validate if the user exists
        Optional<UserEntity> userExistence = userRepository.findById(updateAddressEntity.getUserid());
        if (userExistence.isEmpty()) {
            updateResponse.setResponseDescription("No user found");
            return updateResponse;
        }

        Optional<AddressEntity> address = addressRepository.findByAddressid(updateAddressEntity.getAddressid());
        if (address.isEmpty()) {
            updateResponse.setResponseDescription("Update failed, no address record found");
            return updateResponse;
        }

        AddressEntity addressEntity = AddressMapper.INSTANCE.dtoToAddressEntity(updateAddressEntity);
        UserEntity user = userExistence.get();
        addressEntity.setUserid_Fk(user);

        AddressEntity savedAddress = addressRepository.save(addressEntity);
        AddressDto updatedData = AddressMapper.INSTANCE.addressEntityToDto(savedAddress);
        updateResponse.setData(updatedData);
        updateResponse.setResponseDescription("Updated successfully");

        return updateResponse;
    }



    public Response<List<AddressDto>> getAllUserAddress(int userId){
        Response<List<AddressDto>> userAddressList = new Response<>();
        List<AddressEntity> addressListByUseridFk = addressRepository.findByUserid_Fk(userId);
        if(addressListByUseridFk!=null && !addressListByUseridFk.isEmpty()) {
            userAddressList.setData(new ArrayList<>());
            addressListByUseridFk.forEach(userAddress -> {
                AddressDto mappedEntityToDto = AddressMapper.INSTANCE.addressEntityToDto(userAddress);
                userAddressList.getData().add(mappedEntityToDto);
                userAddressList.setResponseDescription("Success");
            });
        }else {
            userAddressList.setResponseDescription("No address records found by userId");
        }
        return userAddressList;
    }
}
