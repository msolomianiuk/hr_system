package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void sendEmail(String[] toEmails, String subject, String text) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessage.setContent(text, "text/html; charset=UTF-8");
            helper.setSubject(subject);
            helper.setBcc(toEmails);
            mailSender.send(mimeMessage);
        } catch (MessagingException messagingException) {
            LOGGER.debug(messagingException.getStackTrace());
            LOGGER.info(messagingException.getMessage());
        }
    }

    @Override
    public void sendEmail(String toEmail, String subject, String text) {
        sendEmail(new String[]{toEmail}, subject, text);
    }

    @Override
    public void sendEmailAboutSuccessfulRegistration(User user, String password) {
        EmailTemplate emailTemplate = emailTemplateDAO.find(Template.TEMPLATE_SUCCESS_REGISTRATION.getId());
        String email = replacePatterns(emailTemplate.getTemplate(), user, password);
        sendEmail(user.getEmail(), emailTemplate.getDescription(), email.replaceAll("\\{url\\}",
                "http://31.131.25.206:8080/hr_system-1.0-SNAPSHOT"));//!!!!!
    }

    @Override
    public void sendEmailToStudentsByStatus(Status status) {
        //LOGGER.debug("status === " + status);
        Collection<Candidate> candidates = candidateDAO.findCandidateByStatus(status.getStatus());
        EmailTemplate emailTemplate = getTemplateByCandidateStatus(status);
        for (Candidate candidate : candidates) {
            String email = getEmailByCandidateStatus(emailTemplate.getTemplate(), candidate, status);
            sendEmail(candidate.getUser().getEmail(), emailTemplate.getDescription(), email);
        }
    }

    @Override
    public void sendEmailAboutCriticalError(String textError) {
        String[] administratorsEmails = getEmailsByRole(Role.ROLE_ADMIN);
        sendEmail(administratorsEmails, "CRITICAL ERROR ON SITE HRSYSTEM!!!", textError);
    }

    @Override
    public void sendEmailRestorePassword(String email, String url) {
        EmailTemplate emailTemplate = emailTemplateDAO.find(Template.TEMPLATE_RESTORE_PASSWORD.getId());
        String emailText = replacePatterns(emailTemplate.getTemplate(), url);
        sendEmail(email, emailTemplate.getDescription(), emailText);
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
        return template.replaceAll("\\{name\\}", user.getEmail())
                .replaceAll("\\{password\\}", password);
    }

    private String replacePatterns(String template, String url) {
        return template.replaceAll("\\{url\\}", url);
    }

    private String replacePatterns(String template, InterviewDaysDetails interviewDaysDetails, Address address) {
        return template.replaceAll("\\{address\\}", address.getAddress()).replaceAll("\\{time\\}",
                interviewDaysDetails.getInterviewDate() + ", " + interviewDaysDetails.getStartTime());
    }

    private EmailTemplate getTemplateByCandidateStatus(Status status) {
        switch (status) {
            case Interview_dated:
                return emailTemplateDAO.find(Template.TEMPLATE_INVITE_ON_INTERVIEW.getId());
            case Interview_passed:
                return emailTemplateDAO.find(Template.TEMPLATE_INTERVIEW_PASSED.getId());
            case Job_accepted:
                return emailTemplateDAO.find(Template.TEMPLATE_JOB_ACCEPTED.getId());
            case No_interview:
                return emailTemplateDAO.find(Template.TEMPLATE_NO_INTERVIEW.getId());
            case Rejected:
                return emailTemplateDAO.find(Template.TEMPLATE_REJECTED.getId());
            default:
                LOGGER.info("No templates");
                throw new IllegalArgumentException();
        }
    }

    private String getEmailByCandidateStatus(String template, Candidate candidate, Status status) {
        switch (status) {
            case Interview_dated:
                try {
                    InterviewDaysDetails interviewDaysDetails = interviewDaysDetailsDAO.find(candidate.getInterviewDaysDetailsId());
                    Address address = addressDAO.find(interviewDaysDetails.getAddressId());
                    /**
                     * Remind about coming interview a day before
                     */
                    sendReminderInterview(candidate, interviewDaysDetails, address);
                    return replacePatterns(template, interviewDaysDetails, address);
                } catch (Exception e) {
                    LOGGER.info("ERROR while sending email to Status.Interview_dated!!! " + e.getMessage());
                }
            case Interview_passed:
            case Job_accepted:
            case No_interview:
            case Rejected:
                return template;
            default:
                LOGGER.info("No templates");
                throw new IllegalArgumentException();
        }
    }

    private void sendReminderInterview(Candidate candidate, InterviewDaysDetails interviewDaysDetails, Address address) {
        EmailTemplate emailTemplate = emailTemplateDAO.find(Template.TEMPLATE_COMING_INTERVIEW.getId());
        String email = replacePatterns(emailTemplate.getTemplate(), interviewDaysDetails, address);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessage.setContent(email, "text/html; charset=UTF-8");
            helper.setSubject(emailTemplate.getDescription());
            helper.setBcc(candidate.getUser().getEmail());
            String[] date = interviewDaysDetails.getInterviewDate().split(" ");
            helper.setSentDate(new Date(new GregorianCalendar(Integer.valueOf(date[0]), Integer.valueOf(date[1]),
                    Integer.valueOf(date[2]) - 1).getTimeInMillis()));
            mailSender.send(mimeMessage);
        } catch (MessagingException messagingException) {
            LOGGER.debug(messagingException.getStackTrace());
            LOGGER.info(messagingException.getMessage());
        }
    }
}
