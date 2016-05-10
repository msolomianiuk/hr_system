package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.EmailTemplateDAO;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.*;
import ua.netcracker.model.service.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Collection;
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
    @Autowired
    private AddressService addressService;
    @Autowired
    private CandidateService candidateService;

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
        //This is will be when BD has emailTemplates
//        int idTemplateAboutSuccessfulRegistration = 1; //not good (better to use ENUM)
//        String dayAndTime = daysDetailsService.getDateofInterview(1)
//                + " " + daysDetailsService.getStartTimeofInterview(1);
//        EmailTemplate emailTemplate = emailTemplateDAO.find(idTemplateAboutSuccessfulRegistration);
//        sendLetterToEmails(toEmails, emailTemplate.getDescription(), emailTemplate.getTemplate().replaceAll("\\{time\\}", dayAndTime));
    }

    @Override
    public void sendEmailAboutComingInterview(Role role, InterviewDaysDetails daysDetails) {
        //TODO:
        //Think about template to dev/hr/ba (are they similar???)
        int idTemplateAboutComingInterview = 2;
        String dayAndTime = daysDetailsService.getDateofInterview(daysDetails.getId())
                + " " + daysDetailsService.getStartTimeofInterview(daysDetails.getId());
        EmailTemplate emailTemplate;
        String[] usersEmailsBasedOnRole = getEmailsByRole(role);
        switch (role) {
            case ROLE_ADMIN:
                break;
            case ROLE_STUDENT:
                emailTemplate = emailTemplateDAO.find(idTemplateAboutComingInterview);
                sendLetterToEmails(usersEmailsBasedOnRole, emailTemplate.getDescription(),
                        emailTemplate.getTemplate().replaceAll("\\{time\\}", dayAndTime));
                break;
            case ROLE_BA:
            case ROLE_DEV:
            case ROLE_HR:
                emailTemplate = emailTemplateDAO.find(idTemplateAboutComingInterview);
                sendLetterToEmails(usersEmailsBasedOnRole, emailTemplate.getDescription(),
                        emailTemplate.getTemplate().replaceAll("\\{time\\}", dayAndTime));
                break;
            default:
                LOGGER.info("No more roles!!!");
                break;
        }
    }

    @Override
    public void sendEmailToStudentsByStatus(Status status, InterviewDaysDetails daysDetails) {
        EmailTemplate emailTemplate;
        String[] toEmails = getEmailsByCandidateStatus(status);

        //idTemplates based on status:
        int idTemplateOnInterview = 3;
        int idTemplateInterviewPassed = 4;
        int idTemplateJobAccepted = 5;
        int idTemplateNoInterview = 6;
        int idTemplateRejected = 7;
        switch (status) {
            case Interview:
                String dayAndTime = daysDetailsService.getDateofInterview(daysDetails.getId())
                        + " " + daysDetailsService.getStartTimeofInterview(daysDetails.getId());
                String address = addressService.findById(daysDetails.getAddressId()).getAddress();
                emailTemplate = emailTemplateDAO.find(idTemplateOnInterview);
                sendLetterToEmails(toEmails, emailTemplate.getDescription(), emailTemplate.getTemplate()
                        .replaceAll("\\{time\\}", dayAndTime)
                        .replaceAll("\\{address\\}", address));
                break;
            case Interviews_passed:
                emailTemplate = emailTemplateDAO.find(idTemplateInterviewPassed);
                sendLetterToEmails(toEmails, emailTemplate.getDescription(), emailTemplate.getTemplate());
                break;
            case Job_accepted:
                emailTemplate = emailTemplateDAO.find(idTemplateJobAccepted);
                sendLetterToEmails(toEmails, emailTemplate.getDescription(), emailTemplate.getTemplate());
                break;
            case No_interview:
                emailTemplate = emailTemplateDAO.find(idTemplateNoInterview);
                sendLetterToEmails(toEmails, emailTemplate.getDescription(), emailTemplate.getTemplate());
                break;
            case Rejected:
                emailTemplate = emailTemplateDAO.find(idTemplateRejected);
                sendLetterToEmails(toEmails, emailTemplate.getDescription(), emailTemplate.getTemplate());
                break;
            default:
                LOGGER.info("No templates");
                break;
        }
    }

    @Override
    public void sendEmailAboutCriticalError(String textError) {
        String[] administratorsEmails = getEmailsByRole(Role.ROLE_ADMIN);
        sendLetterToEmails(administratorsEmails, "CRITICAL ERROR ON SITE HRSYSTEM!!!", textError);
    }

    private String[] getEmailsByRole(Role role){
        List<User> users = userDAO.getAllPersonalById(role.getId());
        String[] usersEmails = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            usersEmails[i] = users.get(i).getEmail();
        }
        return usersEmails;
    }

    private String[] getEmailsByCandidateStatus(Status status){
        List<Candidate> candidates = (List<Candidate>) candidateService.getCandidateByStatus(status.toString());
        String[] candidatesEmails = new String[candidates.size()];
        for (int i = 0; i < candidates.size(); i++) {
            candidatesEmails[i] = candidates.get(i).getUser().getEmail();
        }
        return candidatesEmails;
    }
}
