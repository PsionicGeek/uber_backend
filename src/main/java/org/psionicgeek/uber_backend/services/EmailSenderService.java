package org.psionicgeek.uber_backend.services;

public interface EmailSenderService {

    public void sendEmail(String toEmail, String subject, String body);

    public void sendBulkEmail(String[] toEmail, String subject, String body);
}
