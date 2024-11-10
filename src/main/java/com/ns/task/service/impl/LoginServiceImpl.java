package com.ns.task.service.impl;

import com.ns.task.dto.CustomerInfoDto;
import com.ns.task.dto.LoginDto;
import com.ns.task.dto.Response;
import com.ns.task.entity.UserEntity;
import com.ns.task.mapper.MapperClass;
import com.ns.task.repository.RegistrationRepository;
import com.ns.task.service.EmailService;
import com.ns.task.service.ILoginService;
import com.ns.task.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements ILoginService {
    private final RegistrationRepository registrationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    public Response<CustomerInfoDto> loginUser(LoginDto loginDto) {
        Response<CustomerInfoDto> loginResponse = new Response<>();

        Optional<UserEntity> fetchUserDetails =
                registrationRepository.findByEmail(loginDto.getMail());

        if (fetchUserDetails.isPresent()
                && passwordEncoder.matches(loginDto.getPassword(),fetchUserDetails.get().getPassword())) {
            UserEntity userEntity = fetchUserDetails.get();

            String generatedToken = jwtService.generateToken(userEntity.getEmail(), userEntity.getId());

            CustomerInfoDto customerInfoDto = MapperClass.INSTANCE.userToCustomerInfoDtoWithToken(userEntity, generatedToken);
            loginResponse.setResponseDescription("Login Successful");
            loginResponse.setData(customerInfoDto);
            emailService.sendSimpleEmail(customerInfoDto.getEmail(),"LoggedIn","LoggedIn successful");
        } else {
            loginResponse.setData(null);
            loginResponse.setResponseDescription("Login in failed,Please enter correct credentials");
        }
        return loginResponse;
    }

}
