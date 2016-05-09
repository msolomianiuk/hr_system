package ua.netcracker.model.service;

import ua.netcracker.model.entity.*;

public interface SendEmailService {
    void sendLetterToEmails(String[] toEmails, String subject, String text);
    void sendEmailAboutSuccessfulRegistration(String[] toEmails);
    void sendEmailAboutComingInterview(String[] toEmails, Role role, InterviewDaysDetails daysDetails);
    void sendEmailToStudentsByStatus(String[] toEmails, Status status, InterviewDaysDetails daysDetails);
    void sendEmailAboutCriticalError(String textError);
}
