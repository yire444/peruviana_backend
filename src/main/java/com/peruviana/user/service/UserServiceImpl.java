package com.peruviana.user.service;

import com.peruviana.user.entity.UserEntity;
import com.peruviana.user.dto.*;
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
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepo;
    private final UserMapper userMap;
    private final JavaMailSender mail;


    private UserEntity searchUserForId(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    private void sendVerificationEmail(String email, String name, String code) {
        System.out.println("DEBUG: Sending OTP [" + code + "] to email: " + email);
    }

    // C: REGISTER USER
    @Override
    @Transactional
    public UserResponseDto registerUser(UserRegisterDto dto) {
        // Guard Clauses: Unique data validation
        if (userRepo.existsByDocumentNumber(dto.getDocumentNumber())) {
            throw new IllegalArgumentException("El número de documento ya ha sido registrado.");
        }
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }
        if (userRepo.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new IllegalArgumentException("El número de teléfono ya está registrado.");
        }

        // Security Guard Clause: Ensure inputs match before mapping
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        // DTO -> ENTITY
        UserEntity userEntity = userMap.toEntity(dto);

        // TOKEN GENERATION
        String verificationToken = String.format("%06d", new Random().nextInt(999999));
        userEntity.setVerificationCode(verificationToken);
        userEntity.setActive(false);

        UserEntity saveUser = userRepo.save(userEntity);

        sendVerificationEmail(saveUser.getEmail(), saveUser.getName(), verificationToken);

        return userMap.toResponseDto(saveUser);
    }

    // V: VERIFY USER REGISTRATION
    @Override
    @Transactional
    public void verifyAccount(String email, UserVerificationDto dto) {
        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (user.getVerificationCode() == null) {
            throw new IllegalArgumentException("Esta cuenta ya se encuentra activa.");
        }

        if (!user.getVerificationCode().equals(dto.getCode())) {
            throw new IllegalArgumentException("El código de verificación ingresado es incorrecto.");
        }

        user.setActive(true);
        user.setVerificationCode(null);
        userRepo.save(user);
    }

    // LOGIN USER
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto login(UserLoginDto loginDto) {
        UserEntity user = userRepo.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Correo o contraseña incorrectos."));

        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new IllegalArgumentException("Correo o contraseña incorrectos.");
        }

        if (!user.getActive()) {
            if (user.getVerificationCode() != null) {
                throw new IllegalStateException("PENDING_ACTIVATION");
            } else {
                throw new IllegalStateException("ACCOUNT_SUSPENDED");
            }
        }

        return userMap.toResponseDto(user);
    }

    // R: LIST USERS AND FILTERS
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> searchUsers(UserFilterDto dto) {
        List<UserEntity> filteredUsers = userRepo.findByAllFilters(
                dto.getDocumentNumber(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                dto.getActive()
        );

        return filteredUsers.stream()
                .map(userMap::toResponseDto)
                .collect(Collectors.toList());
    }

    // U: UPDATE EMAIL
    @Override
    @Transactional
    public UserResponseDto changeEmail(Long id, UserChangeEmailDto dto) {
        UserEntity user = searchUserForId(id);

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("La contraseña es incorrecta");
        }
        if (userRepo.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new IllegalArgumentException("El correo ya se encuentra registrado");
        }

        user.setEmail(dto.getEmail());
        UserEntity userUpdate = userRepo.save(user);
        return userMap.toResponseDto(userUpdate);
    }

    // U: UPDATE PHONE NUMBER
    @Override
    @Transactional
    public UserResponseDto changePhone(Long id, UserChangePhoneDto dto) {
        UserEntity user = searchUserForId(id);

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("La contraseña es incorrecta");
        }
        if (userRepo.existsByPhoneNumberAndIdNot(dto.getPhoneNumber(), id)) {
            throw new IllegalArgumentException("El número de teléfono ya se encuentra registrado");
        }

        user.setPhoneNumber(dto.getPhoneNumber());
        UserEntity userUpdate = userRepo.save(user);
        return userMap.toResponseDto(userUpdate);
    }

    // U: UPDATE PASSWORD
    @Override
    @Transactional
    public void changePassword(Long id, UserChangePasswordDto dto) {
        UserEntity user = searchUserForId(id);

        if (!user.getPassword().equals(dto.getCurrentPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        user.setPassword(dto.getNewPassword());
        userRepo.save(user);
    }

    // D: DELETE USERS (Logical Deactivation)
    @Override
    @Transactional
    public void deleteUser(Long id) {
        UserEntity user = searchUserForId(id);
        user.setActive(false);
        user.setVerificationCode(null);
        userRepo.save(user);
    }
}