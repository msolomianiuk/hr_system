package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;
import ua.netcracker.model.service.SendEmailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
}
