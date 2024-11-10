package com.ns.task.controller;

import com.ns.task.dto.CustomerInfoDto;
import com.ns.task.dto.Response;
import com.ns.task.entity.UserEntity;
import com.ns.task.dto.UpdateDetailDto;
import com.ns.task.service.IRegistrationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class RegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final IRegistrationService registrationService;

    @PostMapping("/register")
    private ResponseEntity<Object> registerNewUser(@Valid @RequestBody UserEntity userRegistrationDetails) {
        logger.info("Registering new User");
            logger.info("User entered details has no errors");
            Response<Boolean> saveResponse = registrationService.registerNewUser(userRegistrationDetails);
            if (saveResponse.getData()!=null) {
                logger.info(saveResponse.getResponseDescription());
                return new ResponseEntity<>(saveResponse,HttpStatus.OK);
            }else {
                logger.info(saveResponse.getResponseDescription());
                return new ResponseEntity<>(saveResponse, HttpStatus.BAD_REQUEST);
            }
    }

    @PostMapping("/update")
    private ResponseEntity<Response<CustomerInfoDto>> updateDetails(@Valid @RequestBody UpdateDetailDto userUpdateDetails) {
        logger.info("User detail updation");
        Response<CustomerInfoDto> updateResponse = registrationService.updateDetails(userUpdateDetails);
        if (updateResponse.getData()!=null) {
            logger.info("Updated successful");
            return new ResponseEntity<>(updateResponse,HttpStatus.OK);
        } else {
            logger.info("Updation failed");
            return new ResponseEntity<>(updateResponse,HttpStatus.BAD_REQUEST);
        }
    }
}
