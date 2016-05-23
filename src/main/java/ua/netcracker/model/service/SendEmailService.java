package ua.netcracker.model.service;

import ua.netcracker.model.entity.*;

import java.util.Collection;

public interface SendEmailService {
    void sendLetterToEmails(String[] toEmails, String subject, String text);
    void sendLetterToEmails(String toEmail, String subject, String text);
    void sendLetterToEmails(Collection<String> toEmails, String subject, String text);

    void sendEmailAboutSuccessfulRegistration(User user, String password);

    void sendEmailToStudentsByStatus(Status status);
    void sendEmailToStudentByStatus(int idCandidate, Status status);
    void sendEmailAboutCriticalError(String textError);

    void sendEmailRestorePassword(String email,String url);

}
