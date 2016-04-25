package ua.netcracker.hr_system.model.service.realization;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.hr_system.model.dao.IUserDao;
import ua.netcracker.hr_system.model.entity.Role;
import ua.netcracker.hr_system.model.entity.User;
import ua.netcracker.hr_system.model.service.IRegistrationService;
import ua.netcracker.hr_system.model.utils.regex.EmailValidator;
import ua.netcracker.hr_system.model.utils.regex.NameValidator;
import ua.netcracker.hr_system.model.utils.regex.PasswordValidator;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created on 4:49 17.04.2016
 *
 * @author Bersik (Serhii Kisilchuk)
 */
@Service("registrationService")
public class RegistrationService implements IRegistrationService {

    private static final Logger LOGGER = Logger.getLogger(CustomUserDetailsServiceImpl.class);
    /**
     * Oles 24.04.2016
     * */
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private NameValidator nameValidator;
    @Autowired
    private PasswordValidator passwordValidator;
    @Autowired
    private User user;
    @Autowired
    private IUserDao userDAO;

    @Override
    public boolean registrationStudent(String email, String name, String surname,
                                                String patronymic, String password) {
/**
 * Oles 24.04.2016
 * */
        if (emailValidator.validate(email) &&
                nameValidator.validate(name) &&
                nameValidator.validate(surname) &&
                nameValidator.validate(patronymic) &&
                passwordValidator.validate(password)) {

            user.setEmail(email);
            user.setPassword(sha256Password(password));
            user.setName(name);
            user.setSurname(surname);
            user.setPatronymic(patronymic);
            user.setRoles(new ArrayList<>(Arrays.asList(Role.STUDENT)));

            return userDAO.insert(user);
        }
        return false;
    }

    @Override
    public boolean isFreeEmail(String email) {
        return userDAO.findByEmail(email) == null;
    }

    private static String sha256Password(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            return String.format("%064x", new java.math.BigInteger(1, digest));
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }

}
