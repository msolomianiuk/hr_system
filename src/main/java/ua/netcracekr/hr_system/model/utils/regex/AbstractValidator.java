package ua.netcracekr.hr_system.model.utils.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
public abstract class AbstractValidator {
    protected Pattern pattern = Pattern.compile(getPattern());
    protected Matcher matcher;

    /**
     * Validate parameter with regular expression
     *
     * @param parameter string for validation
     * @return if parameter is valid - true, else - false
     */
    public boolean validate(final String parameter) {
        matcher = pattern.matcher(parameter);
        return matcher.matches() && parameter.length() >= getMinLength() && parameter.length() <= getMaxLength();
    }

    protected abstract String getPattern();

    protected abstract int getMinLength();

    protected abstract int getMaxLength();
}
