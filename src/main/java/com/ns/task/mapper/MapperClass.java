package com.ns.task.mapper;

import com.ns.task.dto.CustomerInfoDto;
import com.ns.task.dto.ProductDetailDto;
import com.ns.task.dto.UpdateDetailDto;
import com.ns.task.entity.ProductEntity;
import com.ns.task.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperClass {
    MapperClass INSTANCE = Mappers.getMapper(MapperClass.class);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "preferenceId", ignore = true)
    UserEntity dtoToEntity(UpdateDetailDto dto);

    @Mapping(source = "userEntity.id", target = "id")
    @Mapping(source = "userEntity.name", target = "name")
    @Mapping(source = "userEntity.email", target = "email")
    @Mapping(source = "userEntity.phoneNumber", target = "phoneNumber")
    CustomerInfoDto userToCustomerInfoDto(UserEntity userEntity);

    @Mapping(source = "userEntity.id", target = "id")
    @Mapping(source = "userEntity.name", target = "name")
    @Mapping(source = "userEntity.email", target = "email")
    @Mapping(source = "userEntity.phoneNumber", target = "phoneNumber")
    @Mapping(source = "token", target = "token")
    CustomerInfoDto userToCustomerInfoDtoWithToken(UserEntity userEntity, String token);
}
