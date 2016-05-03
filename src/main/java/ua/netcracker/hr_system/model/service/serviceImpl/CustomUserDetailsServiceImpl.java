package ua.netcracker.hr_system.model.service.serviceImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ua.netcracker.hr_system.model.dao.daoImpl.UserDAOImpl;
import ua.netcracker.hr_system.model.entity.User;
import ua.netcracker.hr_system.model.securiry.UserAuthenticationDetails;

/**
 * Class for load user-specific data
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 * @see {@link org.springframework.security.core.userdetails.UserDetails}
 */
@Service("userDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(CustomUserDetailsServiceImpl.class);

    @Autowired
    UserDAOImpl userDAO;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param email the email identifying the user whose data is required.
     *
     * @return a fully populated user record (never <code>null</code>)
     *
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     * GrantedAuthority
     */
    @Override
    public UserAuthenticationDetails loadUserByUsername(final String email)
            throws UsernameNotFoundException {
        //load user in DB
        User user = userDAO.findByEmail(email);


        if (user == null)
            //user not found in DB
            throw new UsernameNotFoundException(email + " not found");

        LOGGER.debug("load user in database: " + user.toString());
        /**
         * Create object of class UserAuthenticationDetails based on entity User
         */
        UserAuthenticationDetails userAuthenticationDetails = new UserAuthenticationDetails(user);

        LOGGER.info("User log in: " + userAuthenticationDetails.toString());
        return userAuthenticationDetails;
    }

}
