package ua.netcracker.hr_system.model.dao.daoInterface;

import ua.netcracker.hr_system.model.entity.User;

import java.util.List;

/**
 * Class to work with entity of class <b>User</b>
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
public interface UserDao extends Dao<Integer, User> {

    User findByName(String name);

    User findBySurname(String surname);

    List<User> getAllPersonalById(int idRole);

    User findByEmail(String email);
}
