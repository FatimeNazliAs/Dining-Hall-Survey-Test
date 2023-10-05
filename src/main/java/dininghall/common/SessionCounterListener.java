/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.common;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "sessionCounterView")
@ApplicationScoped
public class SessionCounterListener implements HttpSessionListener {

    public static List<String> activeUserList = new ArrayList<>();

    public static int totalActiveSessions;

    public int getTotalActiveSession() {
        return totalActiveSessions;
    }

    @Override
    public void sessionCreated(HttpSessionEvent arg) {
        totalActiveSessions++;
        System.out.println("sessionCreated - add one session into counter");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        if (totalActiveSessions > 0) {
            totalActiveSessions--;
        }

        System.out.println("sessionDestroyed - deduct one session from counter");
    }
}
