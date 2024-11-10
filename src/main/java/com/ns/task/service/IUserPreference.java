package com.ns.task.service;

import com.ns.task.dto.Response;
import com.ns.task.dto.UserPreferenceDto;
import com.ns.task.entity.UserEntity;
import com.ns.task.entity.UserPreferenceEntity;

public interface IUserPreference {
    Response<UserPreferenceDto> insertUserPreference(UserPreferenceEntity userPreference, int userId);
    Response<UserPreferenceDto> updateUserPreference(UserPreferenceEntity userPreference, int userId,int preferenceId);
}
