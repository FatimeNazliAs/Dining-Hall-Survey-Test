/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.common;

import org.springframework.security.core.context.SecurityContextHolder;
import dininghall.domain.user.LocalUserSession;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * @author Tolga
 */
@ManagedBean(name = "appUtil")
@ApplicationScoped
public class AppUtil {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    @PostConstruct
    public void init() {

    }

    public static LocalUserSession getLocalUserSession() {
        LocalUserSession localUserSession = null;
        try {
            localUserSession = (LocalUserSession) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (Exception e) {
        }

        return localUserSession;
    }

    public static String getContextPath(FacesContext request) {
        HttpServletRequest origRequest = (HttpServletRequest) request.getCurrentInstance().getExternalContext().getRequest();

        String requestContextPath = origRequest.getServletPath();

        String requestURI = String.valueOf(origRequest);

        String pathValue = requestURI.replaceFirst(requestContextPath, "");

        return pathValue;
    }

    public static void validateLocalUserSession() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // If user already logged ing and try to go login page, redirect user to main page
        if (principal instanceof LocalUserSession) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            String path = context.getRequestContextPath() + "/";

            try {
                context.redirect(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");

        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);

        String slug = NONLATIN.matcher(normalized).replaceAll("");

        return slug.toLowerCase(Locale.ENGLISH);
    }

    /**
     * Gets the remote address from a HttpServletRequest object. It prefers the
     * `X-Forwarded-For` header, as this is the recommended way to do it (user
     * may be behind one or more proxies).
     * <p>
     * Taken from https://stackoverflow.com/a/38468051/778272
     *
     * @param request - the request object where to get the remote address from
     * @return a string corresponding to the IP address of the remote machine
     */
    public static String getRemoteAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");

        if (ipAddress != null) {
            // cares only about the first IP if there is a list
            ipAddress = ipAddress.replaceFirst(",.*", "");
        } else {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }

    public static BigDecimal getStripTrailingZeros(double value) {
        BigDecimal b1 = new BigDecimal(value);

        // Assigning the result of stripTrailingZeros method
        b1 = b1.stripTrailingZeros();
        if (b1.precision() >= 2) {
            b1 = b1.setScale(2, RoundingMode.HALF_EVEN);
        }


        return b1;
    }

}
