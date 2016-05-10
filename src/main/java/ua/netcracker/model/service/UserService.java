package ua.netcracker.model.service;

import org.springframework.web.multipart.MultipartFile;
import ua.netcracker.model.entity.Role;
import ua.netcracker.model.entity.User;

/**
 * Created by Alex on 09.05.2016.
 */
public interface UserService {

    boolean saveUserPhoto(MultipartFile image);

    boolean addUserRole(User user, Role role);

    User get(int id);
}
