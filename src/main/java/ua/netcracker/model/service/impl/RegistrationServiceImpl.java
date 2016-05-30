package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.Role;
import ua.netcracker.model.entity.User;
import ua.netcracker.model.securiry.SHA256PasswordEncoder;
import ua.netcracker.model.service.RegistrationService;
import ua.netcracker.model.service.ValidationService;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private static final Logger LOGGER = Logger.getLogger(RegistrationServiceImpl.class);

    @Autowired
    private UserDAO userDao;

    @Autowired
    private SendEmailServiceImpl sendEmailServiceImpl;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private SHA256PasswordEncoder passwordEncoder;

    @Override
    public boolean registrationStudent(String email, String name, String surname, String patronymic, String password) {

        if (validationService.emailValidation(email) &&
                validationService.nameValidation(name) &&
                validationService.nameValidation(surname) &&
                validationService.nameValidation(patronymic) &&
                validationService.passwordValidation(password)) {

            User user = new User(email, passwordEncoder.encode(password), name, surname, patronymic,
                    new ArrayList<>(Arrays.asList(Role.ROLE_STUDENT)));
            if (userDao.insert(user)) {
                sendEmailServiceImpl.sendEmailAboutSuccessfulRegistration(user, password);
                return true;
            }
            sendEmailServiceImpl.sendEmailAboutCriticalError("ERROR in registrationStudent with email: " + email);
        }
        return false;
    }

    @Override
    public boolean isFreeEmail(String email) {
        return userDao.findByEmail(email) == null;
    }

}
