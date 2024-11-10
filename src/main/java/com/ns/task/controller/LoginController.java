package com.ns.task.controller;

import com.ns.task.dto.LoginDto;
import com.ns.task.dto.CustomerInfoDto;
import com.ns.task.dto.Response;
import com.ns.task.service.ILoginService;

import com.ns.task.service.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/login")
@AllArgsConstructor
public class LoginController {
    private final ILoginService loginService;

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final JwtService jwtService;
    @PostMapping
    public ResponseEntity<Response<CustomerInfoDto>> loginUser(@Valid @RequestBody LoginDto loginDto){

        logger.info("Login method");
        Response<CustomerInfoDto> loginResponse = loginService.loginUser(loginDto);
        if(loginResponse.getData()!=null) {
            logger.info("entered correct credentials and login successful");
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        }
        else {
            logger.info("failed due to incorrect credentials");
            return new ResponseEntity<>(loginResponse,HttpStatus.BAD_REQUEST);
        }
    }
}
