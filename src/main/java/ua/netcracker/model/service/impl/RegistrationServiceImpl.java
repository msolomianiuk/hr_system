package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.Role;
import ua.netcracker.model.entity.Status;
import ua.netcracker.model.entity.User;
import ua.netcracker.model.service.CandidateService;
import ua.netcracker.model.service.RegistrationService;
import ua.netcracker.model.service.ValidationService;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger LOGGER = Logger.getLogger(RegistrationServiceImpl.class);
    @Autowired
    private UserDAO userDao;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private SendEmailServiceImpl sendEmailServiceImpl;

    @Autowired
    private ValidationService validationService;

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

    @Override
    public boolean registrationStudent(String email, String name, String surname, String patronymic, String password) {

        if (validationService.emailValidation(email) &&
                validationService.nameValidation(name) &&
                validationService.nameValidation(surname) &&
                validationService.nameValidation(patronymic) &&
                validationService.passwordValidation(password)) {

            User user = new User(email, sha256Password(password), name, surname, patronymic,
                    new ArrayList<>(Arrays.asList(Role.ROLE_STUDENT)));
            if (userDao.insert(user)) {
                sendEmailServiceImpl.sendEmailAboutSuccessfulRegistration(user, password);
                Candidate candidate = new Candidate();
                candidate.setUserId(userDao.findByEmail(email).getId());
                candidate.setStatusId(Status.New.getId());
                candidate.setCourseId(1);
                return candidateService.saveCandidate(candidate);
            }
        }
        return false;
    }

    @Override
    public boolean isFreeEmail(String email) {
        return userDao.findByEmail(email) == null;
    }

}
