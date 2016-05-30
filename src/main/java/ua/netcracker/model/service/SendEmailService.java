package ua.netcracker.model.service;

import ua.netcracker.model.entity.*;

import java.util.Collection;

public interface SendEmailService {
    void sendEmail(String[] toEmails, String subject, String text);
    void sendEmail(String toEmail, String subject, String text);

    void sendEmailAboutSuccessfulRegistration(User user, String password);
    void sendEmailToStudentsByStatus(Status status);
    void sendEmailRestorePassword(String email,String url);
    void sendEmailAboutCriticalError(String textError);
}
