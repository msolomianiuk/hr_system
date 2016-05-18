package ua.netcracker.model.dao;

import ua.netcracker.model.entity.Role;
import ua.netcracker.model.entity.User;

import java.util.List;

/**
 * Class to work with entity of class <b>User</b>
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
public interface UserDAO extends DAO<User> {
    List<User> findByName(String name);

    List<User> findBySurname(String surname);

    List<User> getAllPersonalById(int idRole);

    User findByEmail(String email);

    List<Role> getUserRolesById(int userId);

    boolean removeUserRoles(int userId);

    boolean insertUserRoles(User user);

    boolean remove(User user);

    Integer findAllWorkers();
}
