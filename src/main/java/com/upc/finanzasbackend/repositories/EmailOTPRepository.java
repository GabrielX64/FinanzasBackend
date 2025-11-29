package com.upc.finanzasbackend.repositories;

import com.upc.finanzasbackend.entities.EmailOTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailOTPRepository extends JpaRepository<EmailOTP, Long> {
    Optional<EmailOTP> findTopByEmailAndOtpAndUsedFalseOrderByIdDesc(String email, Integer otp);
}
