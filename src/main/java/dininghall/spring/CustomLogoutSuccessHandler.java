package dininghall.spring;

import dininghall.common.SessionCounterListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomLogoutSuccessHandler extends
        SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    public SessionRegistry sessionRegistry;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
        if (authentication != null && authentication.getDetails() != null) {
            try {
                httpServletRequest.getSession().invalidate();

                SessionCounterListener.activeUserList = getActiveUserList();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        //redirect to login
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/");
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
}