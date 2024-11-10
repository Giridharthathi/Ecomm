package com.ns.task.controller;

import com.ns.task.dto.AddressDto;
import com.ns.task.dto.Response;
import com.ns.task.repository.AddressRepository;
import com.ns.task.service.IAddressService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/address")
@AllArgsConstructor
public class AddressController {
    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);
    private final IAddressService addressService;
    private final AddressRepository addressRepository;

    @PostMapping("/update")
    private ResponseEntity<Response<AddressDto>> updateAddress(@Valid  @RequestBody AddressDto updatedAddressEntity) {
        logger.info("Update address");
        Response<AddressDto> addressDtoResponse = addressService.updateAddress(updatedAddressEntity);
        logger.info(addressDtoResponse.getResponseDescription());
        return new ResponseEntity<>(addressDtoResponse, HttpStatus.OK);

    }
    @PostMapping("/insert/{userId}")
    private ResponseEntity<Response<AddressDto>> insertNewAddress(@Valid @RequestBody AddressDto addressEntity, @PathVariable("userId") int userId) {
        logger.info("insert new Address");
        Response<AddressDto> insertResponse = addressService.insert(addressEntity, userId);
        if (insertResponse.getData() != null) {
            logger.info(insertResponse.getResponseDescription());
            return new ResponseEntity<>(insertResponse, HttpStatus.OK);
        } else {
            logger.info(insertResponse.getResponseDescription());
            return new ResponseEntity<>(insertResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user")
    private ResponseEntity<Response<List<AddressDto>>> userListOfAddress(
            @RequestParam(name = "userId") int userId) {
        logger.info("User Specific list of address");
        Response<List<AddressDto>> allUserAddress = addressService.getAllUserAddress(userId);
        if (allUserAddress.getData() != null && !(allUserAddress.getData().isEmpty())) {
            logger.info(allUserAddress.getResponseDescription());
        } else {
            logger.info(allUserAddress.getResponseDescription());
        }
        return new ResponseEntity<>(allUserAddress, HttpStatus.OK);
    }
}
