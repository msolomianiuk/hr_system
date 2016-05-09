package ua.netcracker.model.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Alex on 09.05.2016.
 */
public interface UserService {

    boolean saveUserPhoto(MultipartFile image);
}
