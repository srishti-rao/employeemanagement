package com.wowfinstack.backend.service;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String body);
}
