package com.upc.finanzasbackend.services;

import com.upc.finanzasbackend.Interfaces.IEmailService;
import com.upc.finanzasbackend.dtos.EmailDTO;
import com.upc.finanzasbackend.entities.EmailOTP;
import com.upc.finanzasbackend.repositories.EmailOTPRepository;
import com.upc.finanzasbackend.repositories.UserAppRepository;
import com.upc.finanzasbackend.util.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class EmailService implements IEmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailOTPRepository emailOTPRepository;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private UserAppRepository userAppRepository;

    public Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100000,999999);
    }

    @Override
    public void sendEmail(EmailDTO emailDTO) {
        validationService.verifyExistsID(
                userAppRepository.existsByEmail(emailDTO.getEmail()),
                "Email"
        );

        Integer otp = otpGenerator();

        // Guardar OTP en BD con expiración (por ej. 5 min)
        EmailOTP emailOtp = new EmailOTP();
        emailOtp.setEmail(emailDTO.getEmail());
        emailOtp.setOtp(otp);
        emailOtp.setExpirationTime(LocalDateTime.now().plusMinutes(5));
        emailOTPRepository.save(emailOtp);

        // Enviar correo
        SimpleMailMessage email = new SimpleMailMessage();
        String subject = "Código de verificación FinApp";
        String message = "Tu código de verificación es: " + otp;
        email.setTo(emailDTO.getEmail());
        email.setSubject(subject);
        email.setText(message);
        email.setFrom("finapp137@gmail.com");
        mailSender.send(email);
    }
    public boolean verifyOtp(String email, Integer otp) {
        EmailOTP emailOtp = emailOTPRepository
                .findTopByEmailAndOtpAndUsedFalseOrderByIdDesc(email, otp)
                .orElse(null);

        if (emailOtp == null) return false;
        if (emailOtp.getExpirationTime().isBefore(LocalDateTime.now())) return false;

        emailOtp.setUsed(true);
        emailOTPRepository.save(emailOtp);
        return true;
    }
}