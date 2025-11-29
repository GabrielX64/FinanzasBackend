package com.upc.finanzasbackend.controllers;

import com.upc.finanzasbackend.Interfaces.IEmailService;
import com.upc.finanzasbackend.dtos.EmailDTO;
import com.upc.finanzasbackend.dtos.VerifyOTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmailController {
    @Autowired
    private IEmailService emailService;

    @PostMapping("/enviar-correo")
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String sendEmail(@RequestBody EmailDTO emailDTO) {
        emailService.sendEmail(emailDTO);
        return "Correo enviado con éxito";
    }
    @PostMapping("/verificar-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOTP dto) {
        boolean ok = emailService.verifyOtp(dto.getEmail(), dto.getOtp());
        if (!ok) {
            return new ResponseEntity<>("Código inválido o expirado", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("OTP verificado");
    }
}