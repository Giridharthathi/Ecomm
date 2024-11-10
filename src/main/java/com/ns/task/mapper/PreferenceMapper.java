package com.ns.task.mapper;

import com.ns.task.entity.UserEntity;
import com.ns.task.entity.UserPreferenceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PreferenceMapper {
    PreferenceMapper INSTANCE = Mappers.getMapper(PreferenceMapper.class);
    @Mapping(ignore = true,target = "user_Id")
    UserPreferenceEntity entityMapper(UserPreferenceEntity preference);
}
