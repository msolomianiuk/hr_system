package ua.netcracker.model.service;

/**
 * Created on 13:29 11.05.2016
 *
 * @author Bersik (Serhii Kisilchuk)
 */
public interface PasswordResetService {

    boolean sendToken(String email, String sitePath);

    boolean checkToken(String email, String token);

    boolean changePassword(String email, String token, String password);
}
