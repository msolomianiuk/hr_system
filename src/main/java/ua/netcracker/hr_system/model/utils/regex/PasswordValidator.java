package ua.netcracker.hr_system.model.utils.regex;

import org.springframework.stereotype.Service;

/**
 * Class for check validity of password
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
@Service
public class PasswordValidator extends AbstractValidator{

    @Override
    protected String getPattern() {
        return "^[a-zA-Z0-9_-]{6,40}$";
    }

    @Override
    protected int getMinLength() {
        return 1;
    }

    @Override
    protected int getMaxLength() {
        return 45;
    }
}
