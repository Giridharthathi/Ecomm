package com.ns.task.service.impl;

import com.ns.task.dto.CustomerInfoDto;
import com.ns.task.dto.Response;
import com.ns.task.dto.UpdateDetailDto;
import com.ns.task.entity.UserEntity;
import com.ns.task.mapper.MapperClass;
import com.ns.task.repository.RegistrationRepository;
import com.ns.task.service.IRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements IRegistrationService {
    private final RegistrationRepository registrationRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Response<Boolean> registerNewUser(UserEntity newUserDetails) {
        Response<Boolean> saveResponse = new Response<>();

        Optional<UserEntity> byEmail = registrationRepository.findByEmail(newUserDetails.getEmail());
        if (byEmail.isPresent()) {
            saveResponse.setData(false);
            saveResponse.setResponseDescription("already a user present with the mail-Id");
        } else {
            newUserDetails.setPassword(passwordEncoder.encode(newUserDetails.getPassword()));
            registrationRepository.save(newUserDetails);
                saveResponse.setData(true);
                saveResponse.setResponseDescription("User saved successful");
        }
        return saveResponse;
    }

    @Override
    public Response<CustomerInfoDto> updateDetails(UpdateDetailDto userUpdateDetails) {
        Response<CustomerInfoDto> updateResponse = new Response<>();

        Optional<UserEntity> userDetail = registrationRepository.findById(userUpdateDetails.getId());
        if (userDetail.isPresent()) {

            UserEntity userEntity = MapperClass.INSTANCE.dtoToEntity(userUpdateDetails);

            userEntity.setId(userDetail.get().getId());
            userEntity.setPreferenceId(userDetail.get().getPreferenceId());

            UserEntity updateData = registrationRepository.save(userEntity);

            CustomerInfoDto customerInfoDto = MapperClass.INSTANCE.userToCustomerInfoDto(updateData);
            updateResponse.setData(customerInfoDto);
            updateResponse.setResponseDescription("Updated successful");
        } else {
            updateResponse.setData(null);
            updateResponse.setResponseDescription("Updation failed due to no user found by the id");
        }
        return updateResponse;
    }
}