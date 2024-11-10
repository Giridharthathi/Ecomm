package com.ns.task.service;

import com.ns.task.dto.Response;
import com.ns.task.entity.AddressEntity;
import com.ns.task.dto.AddressDto;
import com.ns.task.service.impl.AddressServiceImpl;

import java.util.List;

public interface IAddressService {
    Response<AddressDto> insert(AddressDto addressEntity, int id);
    Response<AddressDto> updateAddress(AddressDto updateAddressEntity);
    Response<List<AddressDto>> getAllUserAddress(int userId);

}
