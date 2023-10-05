package dininghall.spring;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class CustomUserDetailsService implements UserDetailsService {
    private Object userServiceImpl;


    public CustomUserDetailsService() {
    }

    public UserDetails loadUserByUsername(String username) {
        try {
            Method userMethod = userServiceImpl.getClass().getMethod("getUser", String.class);
            Object userFromDB = userMethod.invoke(userServiceImpl, username);
            Field field = userFromDB.getClass().getDeclaredField("password");
            field.setAccessible(true);
            String password = (String) field.get(userFromDB);
            return getUserDetails(username, password, userFromDB);
        } catch (Exception e) {
            throw new RuntimeException("Kullanıcı adınız veya şifreniz yanlış");
        }
    }

    private UserDetails getUserDetails(String username, String password, Object userFromDB) throws Exception {
        return new User(
                username,
                password,
                true,
                true,
                true,
                true,
                getGrantedAuthorities(getRoleListAsStringArray(userFromDB))
        );
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    private List<String> getRoleListAsStringArray(Object userFromDB) throws Exception {
        List<String> roleList = new ArrayList<String>();
        Method userRoleListMethod = userFromDB.getClass().getMethod("getUserRoleList");
        for (Object role : (List) userRoleListMethod.invoke(userFromDB)) {
            Field roleField = role.getClass().getDeclaredField("role");
            roleField.setAccessible(true);
            String roleValue = (String) roleField.get(role);
            roleList.add(roleValue);
        }
        return roleList;
    }

    public void setUserServiceImpl(Object userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

}