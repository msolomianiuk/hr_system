package ua.netcracker.model.service;

public interface SendEmailService {
    void sendLetterToEmails(String[] toEmails, String subject, String text);
}
