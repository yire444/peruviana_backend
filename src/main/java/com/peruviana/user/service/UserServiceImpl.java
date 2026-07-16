package com.peruviana.user.service;

import com.peruviana.user.entity.UserEntity;
import com.peruviana.user.dto.*;
import com.peruviana.user.entity.dto.*;
import com.peruviana.user.mapper.UserMapper;
import com.peruviana.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final IUserRepository userRepo;
    private final UserMapper userMap;
    private final JavaMailSender mail;

    // C: REGISTER USER
    @Override
    @Transactional
    public UserResponseDto registerUser(UserRegisterDto dto) {
        //VALIDATE DATA FOR REGISTER USER
        if(userRepo.existsByDocumentNumber(dto.getDocumentNumber())) {
            throw new RuntimeException("El número de documento ya ha sido registrado.");
        } else if(userRepo.existsByEmail(dto.getEmail())) {
            throw  new  RuntimeException("El email ya esta registrado");
        } else if (userRepo.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw  new  RuntimeException("El número de teléfono  ya esta registrado");
        }

        //ENTITY -> DTO
        UserEntity userEntity = userMap.toEntity(dto);

        String verificationToken = String.format("%06d", new Random().nextInt(999999));
        userEntity.setVerificationCode(verificationToken);
        userEntity.setActive(false);
        UserEntity saveUser = userRepo.save(userEntity);

        sendVerificationEmail(saveUser.getEmail(), saveUser.getName(), verificationToken);
        return userMap.toResponseDto(saveUser);
    }

    //ACTIVE ACCOUNT
    public void sendVerificationEmail(String email, String name, String code) {}

    // LOGIN USER
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto login(UserLoginDto loginDto) {
        UserEntity user = userRepo.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Correo incorrecto."));

        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }
        if (!user.getActive()) {
            throw new IllegalStateException("Esta cuenta esta inactica.");
        }
        return userMap.toResponseDto(user);
    }


    // U: UPDATE USERS
    @Override
    @Transactional
    public UserResponseDto updateProfile(Long id, UserChangeEmailDto updateDto) {
        UserEntity user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        // Lógica de validación "Ignore Self"
        if (userRepo.existsByEmailAndIdNot(updateDto.getEmail(), id)) {
            throw new IllegalArgumentException("El correo ingresado ya se encuentra registrado por otro usuario.");
        }
        if (userRepo.existsByPhoneNumberAndIdNot(updateDto.getPhoneNumber(), id)) {
            throw new IllegalArgumentException("El número de teléfono ya se encuentra registrado por otro usuario.");
        }

        user.setEmail(updateDto.getEmail());
        user.setPhoneNumber(updateDto.getPhoneNumber());

        UserEntity updatedUser = userRepo.save(user);
        return userMap.toResponseDto(updatedUser);
    }

    // UPDATE PASSWORD
    @Override
    @Transactional
    public void changePassword(Long id, UserChangePasswordDto changePasswordDto) {
        UserEntity user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        if (!user.getPassword().equals(changePasswordDto.getCurrentPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta.");
        }
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword())) {
            throw new IllegalArgumentException("La nueva contraseña y su confirmación no coinciden.");
        }

        user.setPassword(changePasswordDto.getNewPassword());
        userRepo.save(user);
    }

    // R: LIST USERS AND FILTERS
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> searchUsers(String documentNumber, String email, String phoneNumber, Boolean active) {
        List<UserEntity> filteredUsers = userRepo.findByAllFilters(documentNumber, email, phoneNumber, active);

        return filteredUsers.stream()
                .map(userMap::toResponseDto)
                .collect(Collectors.toList());
    }

    // D: DELETE USERS (Borrado Lógico)
    @Override
    @Transactional
    public void deleteUser(Long id) {
        UserEntity user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        user.setActive(false);
        userRepo.save(user);
    }
}