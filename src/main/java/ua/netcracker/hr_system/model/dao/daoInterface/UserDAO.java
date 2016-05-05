package ua.netcracker.hr_system.model.dao.daoInterface;

import ua.netcracker.hr_system.model.entity.Role;
import ua.netcracker.hr_system.model.entity.User;

import java.util.List;

/**
 * Class to work with entity of class <b>User</b>
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
public interface UserDAO extends DAO<Integer, User> {
    boolean insert(User user);
    List<User> findByName(String name);

    List<User> findBySurname(String surname);

    List<User> getAllPersonalById(int idRole);

    User findByEmail(String email);

    List<Role> getUserRolesById(int userId);

    boolean removeUserRoles(int userId);

    boolean insertUserRoles(User user);
}
