package ua.netcracker.hr_system.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ua.netcracker.hr_system.model.utils.RolesUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Goal of this class is to provide custom redirect functionality we are looking for.
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /**
     * A strategy for determining if an HTTP request should be redirected to a new location in response to an HTTP
     * response received from the target server.
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * Method simply invokes a redirect using configured RedirectStrategy [default in this case] with the URL returned
     * by the user defined determineTargetUrl method.
     *
     * @param authentication authentication information about user
     */
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
//        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return; //НАЩО???????????????????????
        }

//        redirectStrategy.sendRedirect(request, response, targetUrl);
            redirectStrategy.sendRedirect(request, response,  determineTargetUrl(authentication));//простотак
    }

    /**
     * This method extracts the roles of currently logged-in user and returns
     * appropriate URL according to his/her role.
     */
    /**
     * Oles 24.04.2016
     * */

    protected String determineTargetUrl(Authentication authentication) {
//        String url;
//
//        url = RolesUtils.getUrlByRoles(authentication.getAuthorities());

        return RolesUtils.getUrlByRoles(authentication.getAuthorities());
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}