package com.upc.finanzasbackend.services;

import com.upc.finanzasbackend.Interfaces.IEmailService;
import com.upc.finanzasbackend.dtos.EmailDTO;
import com.upc.finanzasbackend.repositories.UserAppRepository;
import com.upc.finanzasbackend.util.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService implements IEmailService {
    @Autowired
    private JavaMailSender mailSender;
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
        validationService.verifyExistsID(userAppRepository.existsByEmail(emailDTO.getEmail()),"Email");
        SimpleMailMessage email = new SimpleMailMessage();
        String subject = "OE TE LLEGO EL CODIGO CONCHUDO";
        String message = "CODIGO:" + otpGenerator().toString();
        email.setTo(emailDTO.getEmail());
        email.setSubject(subject);
        email.setText(message);
        email.setFrom("finapp137@gmail.com");
        mailSender.send(email);
    }
}