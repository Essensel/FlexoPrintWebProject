package ru.vishnyakov.flexoPrint.controllers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSendlerService {
    @Autowired
    public JavaMailSender emailSender;
}
