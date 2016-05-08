package ua.netcracker.model.service;

/**
 * Created on 1:51 07.05.2016
 *
 * @author Bersik (Serhii Kisilchuk)
 */
public interface ValidationService {

    boolean emailValidation(String email);

    boolean nameValidation(String name);

    boolean passwordValidation(String password);
}
