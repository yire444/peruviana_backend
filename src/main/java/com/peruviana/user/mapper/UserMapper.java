package com.peruviana.user.mapper;

import com.peruviana.user.dto.UserRegisterDto;
import com.peruviana.user.dto.UserResponseDto;
import com.peruviana.user.entity.UserEntity;
import com.peruviana.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(UserRegisterDto dto) {
        if (dto == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setDocumentType(dto.getDocumentType());
        entity.setDocumentNumber(dto.getDocumentNumber());
        entity.setName(dto.getName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setPassword(dto.getPassword());
        entity.setRole(Role.CUSTOMER);
        entity.setActive(false);
        return entity;
    }

    public UserResponseDto toResponseDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return new UserResponseDto(
                entity.getId(),
                entity.getEmail(),
                entity.getName(),
                entity.getLastName(),
                entity.getDocumentType(),
                entity.getDocumentNumber(),
                entity.getPhoneNumber(),
                entity.getRole(),
                entity.getActive()
        );
    }
}