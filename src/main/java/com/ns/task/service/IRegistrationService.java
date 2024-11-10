package com.ns.task.service;

import com.ns.task.dto.CustomerInfoDto;
import com.ns.task.dto.Response;
import com.ns.task.dto.UpdateDetailDto;
import com.ns.task.entity.UserEntity;

public interface IRegistrationService {
    Response<Boolean> registerNewUser(UserEntity newUserDetails);
    Response<CustomerInfoDto> updateDetails(UpdateDetailDto userUpdateDetails);
}
