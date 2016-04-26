package ua.netcracekr.hr_system.model.service.realization;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracekr.hr_system.model.dao.IUserDao;
import ua.netcracekr.hr_system.model.dao.db_dao.CandidateDaoImpl;
import ua.netcracekr.hr_system.model.dao.db_dao.QuestionDaoImpl;
import ua.netcracekr.hr_system.model.entity.Candidate;
import ua.netcracekr.hr_system.model.entity.Question;
import ua.netcracekr.hr_system.model.entity.Role;
import ua.netcracekr.hr_system.model.entity.User;
import ua.netcracekr.hr_system.model.service.IRegistrationService;
import ua.netcracekr.hr_system.model.utils.regex.EmailValidator;
import ua.netcracekr.hr_system.model.utils.regex.NameValidator;
import ua.netcracekr.hr_system.model.utils.regex.PasswordValidator;

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
    @Autowired
    QuestionDaoImpl questionDao;
    @Autowired
    CandidateDaoImpl candidateDao;
    @Autowired
    IUserDao userDAO;

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
        EmailValidator ev = new EmailValidator();
        NameValidator nv = new NameValidator();
        PasswordValidator pv = new PasswordValidator();
        //
          candidateDao.insertAnswerValue("asd",101,8);

        //
        if (ev.validate(email) && nv.validate(name) && nv.validate(surname)
                && nv.validate(patronymic) && pv.validate(password)) {

            User user = new User(email, sha256Password(password), name, surname, patronymic,
                    new ArrayList<>(Arrays.asList(Role.STUDENT)));
            return userDAO.insert(user);
        }
        return false;
    }

    @Override
    public boolean isFreeEmail(String email) {
        return userDAO.findByEmail(email) == null;
    }

}
