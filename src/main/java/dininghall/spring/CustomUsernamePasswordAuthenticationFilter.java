package dininghall.spring;


import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomUsernamePasswordAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        HttpSession session = request.getSession();
        if (session.getAttribute("rand1") == null || session.getAttribute("rand2") == null)
            throw new IllegalArgumentException("Please type captcha result");
        int rand1 = Integer.parseInt((String) session.getAttribute("rand1"));
        int rand2 = Integer.parseInt((String) session.getAttribute("rand2"));
        if (Integer.valueOf(request.getParameter("captcha")) != rand1 + rand2) {
            throw new AuthenticationServiceException("What is " + rand1 + "+" + rand2 + "?");
        }
        try {
            return super.attemptAuthentication(request, response);
        } catch (Exception e) {
            throw new AuthenticationServiceException("Wrong username or password");
        }

    }
}