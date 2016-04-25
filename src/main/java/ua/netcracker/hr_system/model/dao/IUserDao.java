package ua.netcracker.hr_system.model.dao;

import ua.netcracker.hr_system.model.entity.User;

/**
 * Class to work with entity of class <b>User</b>
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
public interface IUserDao extends IDao<Integer, User> {
    /**
     * Get user by him email
     *
     * @param email user email
     * @return information about user
     */
    User findByEmail(String email);
}
