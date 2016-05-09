package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.EmailTemplateDAO;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.*;
import ua.netcracker.model.service.InterviewDaysDetailsService;
import ua.netcracker.model.service.SendEmailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service("SendEmail Service")
public class SendEmailServiceImpl implements SendEmailService {

    private static final Logger LOGGER = Logger.getLogger(SendEmailServiceImpl.class);

    private String email;
    private String password;

    private String auth;
    private String starttls;
    private String host;
    private String port;

    @Autowired
    private EmailTemplateDAO emailTemplateDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private InterviewDaysDetailsService daysDetailsService;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public void setStarttls(String starttls) {
        this.starttls = starttls;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public void sendLetterToEmails(String[] toEmails, String subject, String text) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            InternetAddress[] addressTo = new InternetAddress[toEmails.length];
            for (int i = 0; i < toEmails.length; i++) {
                addressTo[i] = new InternetAddress(toEmails[i]);
            }
            message.setRecipients(Message.RecipientType.BCC, addressTo);
            message.setSubject(subject);
            message.setContent(text, "text/html; charset=UTF-8");
            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.debug(e.getStackTrace());
            LOGGER.info(e.getMessage());
        }
    }

    @Override
    public void sendEmailAboutSuccessfulRegistration(String[] toEmails) {
        sendLetterToEmails(toEmails, "You successfully registered", "You successfully registered on site");
//        //This is will be when BD has emailTemplates
//        int idTemplateAboutSuccessfulRegistration = 1; //not good (better to use ENUM)
//        EmailTemplate emailTemplate = emailTemplateDAO.find(idTemplateAboutSuccessfulRegistration);
//        sendLetterToEmails(toEmails, emailTemplate.getDescription(), emailTemplate.getTemplate());
    }

    @Override
    public void sendEmailAboutComingInterview(String[] toEmails, Role role, InterviewDaysDetails daysDetails) {
        //TODO:
        //Think about template to dev/hr/ba (are they similar???)
        int idTemplateAboutComingInterview = 2;
        String dayAndTime = daysDetailsService.getDateofInterview(daysDetails.getId())
                + " " + daysDetailsService.getStartTimeofInterview(daysDetails.getId());

        switch (role) {
            case ROLE_ADMIN:
                break;
            case ROLE_STUDENT:
                EmailTemplate emailTemplate = emailTemplateDAO.find(idTemplateAboutComingInterview);
                sendLetterToEmails(toEmails, emailTemplate.getDescription(),
                        emailTemplate.getTemplate().replaceAll("\\{time\\}", dayAndTime));
                break;
            case ROLE_HR:
                break;
            default:
                break;
        }
    }

    //TODO
    //Refactor this (not final version)
    @Override
    public void sendEmailToStudentsByStatus(String[] toEmails, Status status, InterviewDaysDetails daysDetails) {
        String dayAndTime = daysDetailsService.getDateofInterview(daysDetails.getId())
                + " " + daysDetailsService.getStartTimeofInterview(daysDetails.getId());
        EmailTemplate emailTemplate;

        //idTemplates based on status:
        int idTemplateOnInterview = 3;
        int idTemplateInterviewPassed = 4;

        switch (status) {
            case Interview:
                emailTemplate = emailTemplateDAO.find(idTemplateOnInterview);
                sendLetterToEmails(toEmails, emailTemplate.getDescription(), emailTemplate.getTemplate()
                        .replaceAll("\\{time\\}", dayAndTime)
                        //TODO
                        //get address (not Id)
                        .replaceAll("\\{address\\}", String.valueOf(daysDetails.getAddressId())));
                break;
            case Interviews_passed:
                emailTemplate = emailTemplateDAO.find(idTemplateInterviewPassed);
                sendLetterToEmails(toEmails, emailTemplate.getDescription(), emailTemplate.getTemplate());
                break;
            case Rejected:
                //TODO
                //get template about courses rejected
                break;
            default:
                break;
        }
    }

    @Override
    public void sendEmailAboutCriticalError(String textError) {
        List<User> administrators = userDAO.getAllPersonalById(1);
        String[] administratorsEmails = new String[administrators.size()];
        for (int i = 0; i < administrators.size(); i++) {
            administratorsEmails[i] = administrators.get(i).getEmail();
        }
        sendLetterToEmails(administratorsEmails, "CRITICAL ERROR ON SITE HRSYSTEM!!!", textError);
    }
}
