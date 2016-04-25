package ua.netcracker.hr_system.model.utils.regex;

import org.springframework.stereotype.Service;

/**
 * Class for check validity of name/surname/patronymic
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
@Service
public class NameValidator extends AbstractValidator {

    @Override
    protected String getPattern() {
        return "^[а-яА-ЯёЁІіЇїЙйєЄ'a-zA-Z0-9]+$";
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
