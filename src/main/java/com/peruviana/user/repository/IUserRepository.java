package com.peruviana.user.repository;

import com.peruviana.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    //LIST ALL USERS
    Optional<UserEntity> findByEmail(String email);

    //VOIDS FOR NOT REPEAT DATA FOR USER REGISTER
    boolean existsByDocumentNumber(String documentNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    //VOIDS FOR NOT REPEAT FOR USER UPDATE
    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    //VERIFICATION CODE
    Optional<UserEntity> findByEmailAndVerificationCode(String email, String verificationCode);

    @Query("""
        SELECT u
        FROM UserEntity u
        WHERE
              (:documentNumber IS NULL OR u.documentNumber = :documentNumber)
              AND (:email IS NULL OR u.email = :email)
              AND (:phoneNumber IS NULL OR u.phoneNumber = :phoneNumber)
              AND (:active IS NULL OR u.active = :active)
        """)
    List<UserEntity> findByAllFilters(
            @Param("documentNumber") String documentNumber,
            @Param("email") String email,
            @Param("phoneNumber") String phoneNumber,
            @Param("active") Boolean active
    );
}