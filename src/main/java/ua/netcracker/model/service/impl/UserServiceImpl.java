package ua.netcracker.model.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.User;
import ua.netcracker.model.securiry.UserAuthenticationDetails;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Legion on 05.05.2016.
 * Modified by Bersik
 */
@Service("user service")
public class UserServiceImpl {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private static final String PHOTO_PATH = "static/images/photo/";

    @Autowired
    private UserDAO userDao;

    private String absolutePath;

    @Autowired
    public void setAbsolutePath(ServletContext servletContext) {
        this.absolutePath = servletContext.getRealPath("/") + PHOTO_PATH;
    }

    public List<User> getAllPersonal(Integer id) {
        return userDao.getAllPersonalById(id);
    }

    public boolean saveUserPhoto(MultipartFile image) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserAuthenticationDetails userDetails =
                    (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User user = userDao.findByEmail(userDetails.getUsername());

            String fileName = user.getId() + ".jpg";
            String fullPath = absolutePath + fileName;
            try {
                File file = new File(fullPath);
                if (file.exists())
                    file.delete();

                FileUtils.writeByteArrayToFile(file, image.getBytes());
                user.setImage(fileName);
                userDao.update(user);
                userDetails.getUser().setImage(fileName);
                LOGGER.info("Upload user photo with location =" + fullPath);
                return true;
            } catch (IOException e) {
                LOGGER.error("Error upload user photo with location " + fullPath);
            }
        }
        LOGGER.error("Error load UserAuthenticationDetails");
        return false;
    }
}
