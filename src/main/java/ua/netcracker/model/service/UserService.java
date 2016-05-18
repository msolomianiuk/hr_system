package ua.netcracker.model.service;

import org.springframework.web.multipart.MultipartFile;
import ua.netcracker.model.entity.Role;
import ua.netcracker.model.entity.User;


public interface UserService {

    boolean saveUserPhoto(MultipartFile image, int x, int y, int width, int height);

    boolean addUserRole(User user, Role role);

    User get(int id);

    User getAuthorizedUser();

    Integer getAllWorkers();
}
