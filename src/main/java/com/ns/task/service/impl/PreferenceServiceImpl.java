package com.ns.task.service.impl;

import com.ns.task.dto.Response;
import com.ns.task.dto.UserPreferenceDto;
import com.ns.task.entity.UserEntity;
import com.ns.task.entity.UserPreferenceEntity;
import com.ns.task.repository.PreferenceRepository;
import com.ns.task.repository.RegistrationRepository;
import com.ns.task.service.IUserPreference;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PreferenceServiceImpl implements IUserPreference {

    private final PreferenceRepository preferenceRepository;

    private final RegistrationRepository userRepository;
    public Response<UserPreferenceDto> insertUserPreference(UserPreferenceEntity userPreference, int userId) {
        Optional<UserEntity> userDetails = userRepository.findById(userId);
        Response<UserPreferenceDto> insertResponse = new Response<>();
        if (userDetails.isPresent()) {
            insertResponse.setData(new UserPreferenceDto());
            UserEntity userEntity = userDetails.get();
            UserPreferenceEntity preference = preferenceRepository.save(userPreference);
            userEntity.setPreferenceId(preference);

            UserEntity save = userRepository.save(userEntity);

            insertResponse.getData().setId(save.getId());
            insertResponse.getData().setSmsNotification(preference.isSmsNotification());
            insertResponse.getData().setEmailNotification(preference.isEmailNotification());
            insertResponse.getData().setUser_Id(save);

            insertResponse.setResponseDescription("inserted successful");
        } else {
            insertResponse.setData(null);
            insertResponse.setResponseDescription(" not inserted, no user found by userId");
        }
        return insertResponse;
    }

    public Response<UserPreferenceDto> updateUserPreference(UserPreferenceEntity userPreference,
                                                            int userId, int preferenceId) {
        Response<UserPreferenceDto> updateResponse = new Response<>();
        Optional<UserEntity> userExistence = userRepository.findById(userId);

        Optional<UserPreferenceEntity> userPreferenceExistence =
                preferenceRepository.findById(preferenceId);

        if(userExistence.isEmpty()){
         updateResponse.setResponseDescription("No user found by the userId");
         return updateResponse;
        }

        if(userPreferenceExistence.isEmpty()){
            updateResponse.setResponseDescription("No preferences found to update");
            return updateResponse;
        }
            UserPreferenceEntity userPreferenceEntity = userPreferenceExistence.get();
            userPreferenceEntity.setEmailNotification(userPreference.isEmailNotification());
            userPreferenceEntity.setSmsNotification(userPreference.isSmsNotification());

            UserPreferenceEntity save = preferenceRepository.save(userPreferenceEntity);

            updateResponse.getData().setId(save.getId());
            updateResponse.getData().setSmsNotification(save.isSmsNotification());
            updateResponse.getData().setEmailNotification(save.isEmailNotification());

            updateResponse.setResponseDescription("Updated successful");
        return updateResponse;
    }
}
