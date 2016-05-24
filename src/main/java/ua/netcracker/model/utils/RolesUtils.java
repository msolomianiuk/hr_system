package ua.netcracker.model.utils;

import org.springframework.security.core.GrantedAuthority;
import ua.netcracker.model.entity.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static ua.netcracker.model.entity.Role.*;

/**
 * Class to work with roles (authority granted)
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
public class RolesUtils {

    private static boolean isContains(List<String> roles, Role role) {
        return roles.contains(role.name());
    }

    public static String getUrlByRoles(Collection<? extends GrantedAuthority> authorities) {
        String url;

        List<String> roles = new ArrayList<>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        if (isContains(roles, ROLE_ADMIN)) {
            url = "/admin";
        } else if (isContains(roles, ROLE_HR)) {
            url = "/hr/interview_page";
        } else if (isContains(roles, ROLE_BA)) {
            url = "/ba/interview_page";
        } else if (isContains(roles, ROLE_DEV)) {
            url = "/dev/interview_page";
        } else if (isContains(roles, ROLE_STUDENT)) {
            url = "/student";
        } else {
            url = "/error";
        }
        return url;
    }

}
