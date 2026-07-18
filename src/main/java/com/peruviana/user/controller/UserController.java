package com.peruviana.user.controller;

import com.peruviana.user.dto.*;
import com.peruviana.user.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final IUserService userService;

    // POST: REGISTER
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRegisterDto dto) {
        UserResponseDto response = userService.registerUser(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //POST : VERIFY USER
    @PostMapping("/verify")
    public ResponseEntity<Void> verifyAccount(
            @RequestParam String email,
            @Valid @RequestBody UserVerificationDto dto) {

        userService.verifyAccount(email, dto);
        return ResponseEntity.ok().build();
    }

    // POST: LOGIN
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@Valid @RequestBody UserLoginDto dto) {
        UserResponseDto response = userService.login(dto);
        return ResponseEntity.ok(response);
    }

    // GET: LIST
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> searchUsers(@ModelAttribute UserFilterDto dto) {
        List<UserResponseDto> response = userService.searchUsers(dto);
        return ResponseEntity.ok(response);
    }

    //U: UPDATE USER

    //EMAIL
    @PutMapping("/{id}/email")
    public ResponseEntity<UserResponseDto> changeEmail(
            @PathVariable Long id,
            @Valid @RequestBody UserChangeEmailDto dto) {

        UserResponseDto response = userService.changeEmail(id, dto);
        return ResponseEntity.ok(response);
    }

    //PHONE
    @PutMapping("/{id}/phone")
    public ResponseEntity<UserResponseDto> changePhone(
            @PathVariable Long id,
            @Valid @RequestBody UserChangePhoneDto dto) {

        UserResponseDto response = userService.changePhone(id, dto);
        return ResponseEntity.ok(response);
    }

    //PASSWORD
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody UserChangePasswordDto dto) {

        userService.changePassword(id, dto);
        return ResponseEntity.ok().build();
    }

    //D: DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}