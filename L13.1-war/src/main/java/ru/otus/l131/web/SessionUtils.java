package ru.otus.l131.web;

import javax.servlet.http.HttpSession;

/**
 *
 */
public class SessionUtils {
    private final static String LOGGEDIN_ATTRIBUTE = "isLoggedIn";

    public static boolean isLoggedIn(HttpSession session){
       Boolean isLoggedIn = (Boolean) session.getAttribute(LOGGEDIN_ATTRIBUTE);
            if(isLoggedIn == null){
                return false;
            }else{
                return isLoggedIn;
            }
    }

    public static void setLoggedIn(HttpSession session){
        session.setAttribute(LOGGEDIN_ATTRIBUTE,true);
    }
}
