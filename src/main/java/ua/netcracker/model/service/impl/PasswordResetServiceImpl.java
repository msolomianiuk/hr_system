package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.PasswordResetTokenDAO;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.PasswordResetToken;
import ua.netcracker.model.entity.User;
import ua.netcracker.model.service.PasswordResetService;
import ua.netcracker.model.service.RegistrationService;
import ua.netcracker.model.service.SendEmailService;
import ua.netcracker.model.service.ValidationService;

import java.util.UUID;

/**
 * Created on 13:29 11.05.2016
 *
 * @author Bersik (Serhii Kisilchuk)
 */
@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private static final Logger LOGGER = Logger.getLogger(PasswordResetServiceImpl.class);

    @Autowired
    SendEmailService sendEmailService;

    @Autowired
    UserDAO userDAO;

    @Autowired
    PasswordResetTokenDAO passwordResetTokenDAO;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    ValidationService validationService;

    @Override
    public boolean sendToken(String email, String sitePath) {
        User user = userDAO.findByEmail(email);

        if (user != null) {
            LOGGER.debug("PasswordResetServiceImpl, sendToken, user=" + user.toString());
            PasswordResetToken prt = passwordResetTokenDAO.findByUserId(user.getId());
            LOGGER.debug("PasswordResetServiceImpl, sendToken, prt=" + prt);
            if (prt == null) {
                prt = new PasswordResetToken(user.getId(), UUID.randomUUID().toString());
                if (!passwordResetTokenDAO.insert(prt))
                    return false;
            }
            sendEmailService.sendEmailRestorePassword(email, constructResetTokenEmail(sitePath, prt.getToken(), user));
            return true;
        }
        LOGGER.debug("PasswordResetServiceImpl, sendToken, user=null");
        return false;
    }

    @Override
    public boolean checkToken(String email, String token) {
        if (validationService.emailValidation(email)) {
            User user = userDAO.findByEmail(email);
            if (user != null) {
                PasswordResetToken prt = passwordResetTokenDAO.findByUserId(user.getId());
                return prt != null && prt.getToken().equals(token);
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean changePassword(String email, String token, String password) {
        if (validationService.emailValidation(email) && validationService.passwordValidation(password)) {
            User user = userDAO.findByEmail(email);
            if (user != null) {
                PasswordResetToken prt = passwordResetTokenDAO.findByUserId(user.getId());
                if (prt != null && prt.getToken().equals(token)) {
                    user.setPassword(registrationService.sha256Password(password));
                    if (userDAO.update(user) && passwordResetTokenDAO.remove(prt.getId())) {
                        LOGGER.info("success update password for email=" + email);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String constructResetTokenEmail(String contextPath, String token, User user) {
        return contextPath + "/password/resetToken?email=" + user.getEmail() + "&token=" + token;
    }
}
