package ua.netcracker.hr_system.model.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmailsUtils {
    /**
     * Send letters to emails
     *
     * @param toEmails Emails where letters will be send
     * @param subject  Subject of the letter
     * @param text     Text of the letter
     */
    public static void sendLettersToEmails(String[] toEmails, String subject, String text) {

        //you can choose your email
        final String username = "netcracker.company";
        final String password = "netcracker.company1";

        /**
         * Properties for gmail: https://support.google.com/mail/answer/13287?hl=en
         */
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);

            /**
             * From which address(Email) letters will be sending
             * Must be turn on here: https://www.google.com/settings/security/lesssecureapps
             */
            message.setFrom(new InternetAddress(username));

            InternetAddress[] addressTo = new InternetAddress[toEmails.length];
            for (int i = 0; i < toEmails.length; i++) {
                addressTo[i] = new InternetAddress(toEmails[i]);
            }

            message.setRecipients(Message.RecipientType.BCC, addressTo);

            message.setSubject(subject);

            message.setContent(text, "text/html");

            Transport.send(message);

            System.out.println("Letter with subject " + subject + " was sent");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
