package com.epam.xstack.mapper.authentication_mapper;

import com.epam.xstack.models.dto.authentication_dto.AuthenticationChangeLoginRequestDTO;
import com.epam.xstack.models.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthenticationChangeLoginRequestMapper {
    @Mapping(source = "user.password", target = "currentPassword")
    AuthenticationChangeLoginRequestDTO toDto(User user);

    @InheritInverseConfiguration
    User toEntity(AuthenticationChangeLoginRequestDTO requestDTO);
}
