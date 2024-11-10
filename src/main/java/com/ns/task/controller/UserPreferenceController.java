package com.ns.task.controller;

import com.ns.task.dto.Response;
import com.ns.task.dto.UserPreferenceDto;
import com.ns.task.entity.UserPreferenceEntity;
import com.ns.task.service.IUserPreference;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/preference")
@AllArgsConstructor
public class UserPreferenceController {
    private static final Logger logger = LoggerFactory.getLogger(UserPreferenceController.class);
    private final IUserPreference userPreferenceService;

    @PostMapping("/insert/{userId}")
    private ResponseEntity<String> insertUserPreference(@RequestBody UserPreferenceEntity userPreference,
                                                        @PathVariable("userId") int userId) {
        logger.info("Inserting new user preference");
        Response<UserPreferenceDto> insertPreferenceResponse = userPreferenceService.insertUserPreference(userPreference, userId);
        if (insertPreferenceResponse.getData()!=null) {
            logger.info(insertPreferenceResponse.getResponseDescription());
            return new ResponseEntity<>("Successfully inserted", HttpStatus.OK);
        } else {
            logger.info(insertPreferenceResponse.getResponseDescription());
            return new ResponseEntity<>("Insertion failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update/{preferenceId}/{userid}")
    private ResponseEntity<Response<UserPreferenceDto>> updatePreference(@RequestBody UserPreferenceEntity userPreference,
                                                    @PathVariable("userid") int userId,
                                                    @PathVariable("preferenceId") int preferenceId) {
        logger.info("update user preference");
        Response<UserPreferenceDto> userUpdatePreferenceResponse = userPreferenceService.updateUserPreference(userPreference, userId, preferenceId);
        if (userUpdatePreferenceResponse.getData()!=null) {
            logger.info(userUpdatePreferenceResponse.getResponseDescription());
            return new ResponseEntity<>(userUpdatePreferenceResponse, HttpStatus.OK);
        } else {
            logger.info(userUpdatePreferenceResponse.getResponseDescription());
            return new ResponseEntity<>(userUpdatePreferenceResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
