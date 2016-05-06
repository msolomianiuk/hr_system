package ua.netcracker.model.utils.regex;

/**
 * Class for check validity of email
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
public class EmailValidator extends AbstractValidator {
    @Override
    protected String getPattern() {
        return "^[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*(\\.[a-zA-Z]{2,})$";
    }

    @Override
    protected int getMinLength() {
        return 4;
    }

    @Override
    protected int getMaxLength() {
        return 45;
    }


}
