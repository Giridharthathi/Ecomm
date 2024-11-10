package com.ns.task.mapper;

import com.ns.task.dto.AddressDto;
import com.ns.task.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(target = "userid", source = "entity.userid_Fk.id")
    @Mapping(target = "userName", source = "entity.userid_Fk.name")
    AddressDto addressEntityToDto(AddressEntity entity);

    AddressEntity dtoToAddressEntity(AddressDto dto);


}
