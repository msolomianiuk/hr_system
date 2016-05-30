package ua.netcracker.model.service;

/**
 * Service for registration users
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
public interface RegistrationService {

    /**
     * Register student in system (insert student to DB)
     *
     * @param email      student email
     * @param name       student name
     * @param surname    student surname
     * @param patronymic student patronymic
     * @param password   student password (String, not sha-256)
     * @return if insert was successful - true; else - false
     */
    boolean registrationStudent(String email, String name, String surname, String patronymic, String password);

    /**
     * Check whether a user already exists with that Email
     *
     * @param email user email
     * @return true - email is free, false - email busy
     */
    boolean isFreeEmail(String email);

}
