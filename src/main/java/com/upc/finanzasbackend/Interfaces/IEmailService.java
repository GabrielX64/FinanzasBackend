package com.upc.finanzasbackend.Interfaces;

import com.upc.finanzasbackend.dtos.EmailDTO;

public interface IEmailService {
    public void sendEmail(EmailDTO emailDTO);
    public boolean verifyOtp(String email, Integer otp);
}
