package com.peruviana.user.service;

import com.peruviana.user.dto.UserRegisterDto;
import com.peruviana.user.dto.UserResponseDto;
import com.peruviana.user.entity.UserEntity;
import com.peruviana.user.mapper.UserMapper;
import com.peruviana.user.repository.IUserRepository;
import com.peruviana.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepo;
    private final UserMapper userMap;
    private final JavaMailSender mail;


    private void sendVerificationEmail(String email, String name, String code) {

    }

    @Override
    @Transactional
    public UserResponseDto registerUser(UserRegisterDto dto) {

        //VALIDATE DATA
        if (userRepo.existsByDocumentNumber(dto.getDocumentNumber())) {
            throw new IllegalArgumentException("El número de documento ya se encuentra registrado");
        }
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El correo ya se encuentra registrado");
        }
        if (userRepo.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new IllegalArgumentException("El número telefónico ya se encuentra registrado");
        }

        // 2. Security Validation: Ensure passwords match
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        UserEntity user = userMap.toEntity(dto);

        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        user.setVerificationCode(verificationCode);
        user.setActive(false);

        UserEntity userSave = userRepo.save(user);

        sendVerificationEmail(userSave.getEmail(), userSave.getName(), verificationCode);

        return userMap.toResponseDto(userSave);
    }


}