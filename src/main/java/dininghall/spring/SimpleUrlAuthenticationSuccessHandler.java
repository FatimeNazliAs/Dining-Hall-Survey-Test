/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.spring;

import dininghall.common.SessionCounterListener;
import dininghall.view.user.LocalUserUrlSessionView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SessionScoped
public class SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private SessionRegistry sessionRegistry;

    protected Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication) throws IOException {

        String targetUrl = null;
        try {
            targetUrl = (String) request.getSession().getAttribute("previousUrl");
        } catch (Exception e) {

        }

        if (targetUrl != null && targetUrl.isEmpty()) {
            targetUrl = determineTargetUrl(authentication);
        } else {
            targetUrl = determineTargetUrl(authentication);
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        LocalUserUrlSessionView u = (LocalUserUrlSessionView) request.getSession().getAttribute("userUrlSession");
        if (u != null && u.getOriginalURL() != null && !u.getOriginalURL().equals("")) {
            targetUrl = u.getOriginalURL().replace("!", "&");
            targetUrl = targetUrl.replace("_", "#");
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }

    }

    /**
     * Builds the target URL according to the logic defined in the main class
     * Javadoc.
     *
     * @param authentication
     * @return
     */
    protected String determineTargetUrl(Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        OUTER:
        for (GrantedAuthority grantedAuthority : authorities) {
            switch (grantedAuthority.getAuthority()) {
                case "ROLE_USER":
                    isUser = true;
                    break OUTER;

                case "ROLE_ADMIN":
                    isAdmin = true;
                    break OUTER;

                case "ROLE_SUPER":
                    isAdmin = true;
                    break OUTER;

            }
        }

        SessionCounterListener.activeUserList = getActiveUserList();

        if (isUser) {
            return "/";

        } else if (isAdmin) {
            return "/SiteAdmin/dashboard.xhtml";

        } else {
            throw new IllegalStateException();
        }


    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public List<String> getActiveUserList() {
        List<Object> principals = sessionRegistry.getAllPrincipals();

        List<String> userNameList = new ArrayList<String>();


        for (Object principal : principals) {
            if (principal instanceof User) {
                userNameList.add(((User) principal).getUsername());
            }
        }

        return userNameList;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
