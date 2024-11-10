package com.ns.task.service;

import com.ns.task.dto.LoginDto;
import com.ns.task.dto.CustomerInfoDto;
import com.ns.task.dto.Response;

import java.util.Optional;

public interface ILoginService {
    Response<CustomerInfoDto> loginUser(LoginDto loginDto);
}
