package com.peruviana.user.service;

import com.peruviana.user.dto.*;

import java.util.List;

public interface IUserService {

    // C: REGISTER USER
    UserResponseDto registerUser(UserRegisterDto dto);

    // V: VERIFY USER REGISTRATION
    void verifyAccount(String email, UserVerificationDto dto);

    // LOGIN USER
    UserResponseDto login(UserLoginDto dto);

    // R: LIST USERS AND FILTERS
    List<UserResponseDto> searchUsers(UserFilterDto dto);

    // U: UPDATE EMAIL
    UserResponseDto changeEmail(Long id, UserChangeEmailDto dto);

    // U: UPDATE PHONE
    UserResponseDto changePhone(Long id, UserChangePhoneDto dto);

    // U: UPDATE PASSWORD
    void changePassword(Long id, UserChangePasswordDto dto);

    // D: DELETE USER
    void deleteUser(Long id);
}
