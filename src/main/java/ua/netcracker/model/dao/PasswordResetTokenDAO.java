package ua.netcracker.model.dao;

import ua.netcracker.model.entity.PasswordResetToken;

/**
 * Created on 13:17 11.05.2016
 *
 * @author Bersik (Serhii Kisilchuk)
 */
public interface PasswordResetTokenDAO extends DAO<PasswordResetToken> {

    boolean remove(int id);

    PasswordResetToken findByUserId(int userId);
}
