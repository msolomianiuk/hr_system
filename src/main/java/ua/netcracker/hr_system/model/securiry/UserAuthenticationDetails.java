package ua.netcracker.hr_system.model.securiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ua.netcracker.hr_system.model.entity.Role;
import ua.netcracker.hr_system.model.entity.User;

import java.util.Collection;
import java.util.HashSet;

/**
 * Class with detailed information about the user needed to identify it
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 * @see {@link org.springframework.security.core.userdetails.UserDetails}
 * @see {@link org.springframework.security.core.userdetails.User}
 */
@Service("userAuthentication")
public class UserAuthenticationDetails implements UserDetails {
    private static final long serialVersionUID = 4000L;

    @Autowired
    private User user;

    private Collection<SimpleGrantedAuthority> authorities;
    /**
     * Oles 24.04.2016
     * */
    public void setUserDetails(User user){
        this.user=user;
    }

    public UserAuthenticationDetails(User user) {
        this.user = user;

        authorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getUserId() {
        return user.getId();
    }

    public String getName() {
        return user.getName();
    }

    public String getSurname() {
        return user.getSurname();
    }

    @Override
    public String toString() {
        return "UserAuthenticationDetails{" +
                "user=" + user +
                ", authorities=" + authorities +
                '}';
    }
}
