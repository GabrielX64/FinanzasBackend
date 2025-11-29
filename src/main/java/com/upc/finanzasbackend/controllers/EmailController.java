package com.upc.finanzasbackend.controllers;

import com.upc.finanzasbackend.Interfaces.IEmailService;
import com.upc.finanzasbackend.dtos.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
}
