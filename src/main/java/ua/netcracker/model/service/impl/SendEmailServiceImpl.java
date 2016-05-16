package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.*;
import ua.netcracker.model.entity.*;
import ua.netcracker.model.entity.Address;
import ua.netcracker.model.service.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Service("SendEmail Service")
public class SendEmailServiceImpl implements SendEmailService {

    private static final Logger LOGGER = Logger.getLogger(SendEmailServiceImpl.class);

    private static final int TEMPLATE_SUCCESS_REGISTRATION = 1;
    private static final int TEMPLATE_COMING_INTERVIEW = 2;
    private static final int TEMPLATE_INVITE_ON_INTERVIEW = 3;
    private static final int TEMPLATE_INTERVIEW_PASSED = 4;
    private static final int TEMPLATE_JOB_ACCEPTED = 5;
    private static final int TEMPLATE_NO_INTERVIEW = 6;
    private static final int TEMPLATE_REJECTED = 7;


    @Autowired
    private EmailTemplateDAO emailTemplateDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CandidateDAO candidateDAO;
    @Autowired
    private InterviewDaysDetailsDAO interviewDaysDetailsDAO;
    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;

    @Override
    public void sendLetterToEmails(String[] toEmails, String subject, String text) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            mimeMessage.setContent(text, "text/html");
            helper.setSubject(subject);
            helper.setTo(templateMessage.getTo());
            helper.setFrom(templateMessage.getFrom());
            helper.setBcc(toEmails);
            mailSender.send(mimeMessage);
        } catch (MessagingException messagingException) {
            LOGGER.debug(messagingException.getStackTrace());
            LOGGER.info(messagingException.getMessage());
        }
    }

    @Override
    public void sendLetterToEmails(String toEmail, String subject, String text) {
        sendLetterToEmails(new String[]{toEmail}, subject, text);
    }

    @Override
    public void sendLetterToEmails(Collection<String> toEmails, String subject, String text) {
        sendLetterToEmails(toEmails.toArray(new String[0]), subject, text);
    }

    @Override
    public void sendEmailAboutSuccessfulRegistration(User user, String password) {
        EmailTemplate emailTemplate = emailTemplateDAO.find(TEMPLATE_SUCCESS_REGISTRATION);
        String email = replacePatterns(emailTemplate.getTemplate(), user, password);
        sendLetterToEmails(user.getEmail(), emailTemplate.getDescription(), email);
    }

    @Override
    public void sendEmailToStudentsByStatus(Status status) {
        Collection<Candidate> candidates = candidateDAO.findCandidateByStatus(status.getStatus());
        EmailTemplate emailTemplate = getTemplateByCandidateStatus(status);
        for (Candidate candidate : candidates) {
            User user = userDAO.find(candidate.getUserId());
            String email = getEmailByCandidateStatus(emailTemplate.getTemplate(), user, candidate, status);
            sendLetterToEmails(user.getEmail(), emailTemplate.getDescription(), email);
        }
    }

    @Override
    public void sendEmailAboutCriticalError(String textError) {
        String[] administratorsEmails = getEmailsByRole(Role.ROLE_ADMIN);
        sendLetterToEmails(administratorsEmails, "CRITICAL ERROR ON SITE HRSYSTEM!!!", textError);
    }

    @Override
    public void sendEmailRestorePassword(String email, String url) {
        sendLetterToEmails(email, "Restore password", "Your url: " + url);
    }

    private String[] getEmailsByRole(Role role) {
        List<User> users = userDAO.getAllPersonalById(role.getId());
        String[] usersEmails = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            usersEmails[i] = users.get(i).getEmail();
        }
        return usersEmails;
    }

    private String replacePatterns(String template, User user, String password) {
        return replacePatterns(template, user).replaceAll("\\{password\\}", password);
    }

    private String replacePatterns(String template, User user) {
        return template.replaceAll("\\{name\\}", user.getName());
    }

    private String replacePatterns(String template, User user, InterviewDaysDetails interviewDaysDetails, Address address) {
        return replacePatterns(template, user).replaceAll("\\{address\\}", address.getAddress() + " " + address.getRoomCapacity())
                .replaceAll("\\{time}\\}", interviewDaysDetails.getInterviewDate() + " " + interviewDaysDetails.getStartTime());
    }

    private EmailTemplate getTemplateByCandidateStatus(Status status) {
        switch (status) {
            case Interview:
                return emailTemplateDAO.find(TEMPLATE_INVITE_ON_INTERVIEW);
            case Interview_passed:
                return emailTemplateDAO.find(TEMPLATE_INTERVIEW_PASSED);
            case Job_accepted:
                return emailTemplateDAO.find(TEMPLATE_JOB_ACCEPTED);
            case No_interview:
                return emailTemplateDAO.find(TEMPLATE_NO_INTERVIEW);
            case Rejected:
                return emailTemplateDAO.find(TEMPLATE_REJECTED);
            default:
                LOGGER.info("No templates");
                throw new IllegalArgumentException();
        }
    }

    private String getEmailByCandidateStatus(String template, User user, Candidate candidate, Status status) {
        switch (status) {
            case Interview:
                InterviewDaysDetails interviewDaysDetails = interviewDaysDetailsDAO.find(candidate.getInterviewDaysDetailsId());
                Address address = addressDAO.find(interviewDaysDetails.getAddressId());
                sendReminderInterview(user, interviewDaysDetails, address);
                return replacePatterns(template, user, interviewDaysDetails, address);
            case Interview_passed:
            case Job_accepted:
            case No_interview:
            case Rejected:
                return replacePatterns(template, user);
            default:
                LOGGER.info("No templates");
                throw new IllegalArgumentException();
        }
    }

    private void sendReminderInterview(User user, InterviewDaysDetails interviewDaysDetails, Address address) {
        EmailTemplate emailTemplate = emailTemplateDAO.find(TEMPLATE_COMING_INTERVIEW);
        String email = replacePatterns(emailTemplate.getTemplate(), user, interviewDaysDetails, address);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            mimeMessage.setContent(email, "text/html");
            helper.setSubject(emailTemplate.getDescription());
            helper.setTo(templateMessage.getTo());
            helper.setFrom(templateMessage.getFrom());
            helper.setBcc(user.getEmail());
            mailSender.send(mimeMessage);
            String[] date = interviewDaysDetails.getInterviewDate().split(" ");
            helper.setSentDate(new Date(new GregorianCalendar(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2]) - 1).getTimeInMillis()));
            mailSender.send(mimeMessage);
        } catch (MessagingException messagingException) {
            LOGGER.debug(messagingException.getStackTrace());
            LOGGER.info(messagingException.getMessage());
        }
    }
}
