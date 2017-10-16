package ru.otus.frontend.web.servlets;

import ru.otus.frontend.web.utils.SessionUtils;
import ru.otus.frontend.web.utils.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Сервлет авторизации
 */
public class LoginServlet extends HttpServlet {
    private final static String LOGIN_TEMPLATE = "login.ftl";

    private final static String PAR_PAGE_TITLE = "pageTitle";
    private final static String PAR_LOGIN = "login";
    private final static String PAR_VALIDATION_STATUS = "validationStatus";
    private final static String PAR_VALIDATION_MESSAGE = "validationMessage";

    private final static String PAGE_TITLE = "Login";
    private final static String VALIDATION_MESSAGE_LOGIN_OR_PASSWORD_ARE_INCORRECT = "Login or password is incorrect";

    private String adminUserName;
    private String adminPassword;

    public LoginServlet(String adminUserName, String adminPassword) {
        this.adminUserName = adminUserName;
        this.adminPassword = adminPassword;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        renderLogin(resp,"",true);
    }

    private void renderLogin(HttpServletResponse resp, String login, boolean validated) throws IOException {
        HashMap<String,Object> pageVariables = new HashMap<>();

        pageVariables.put(PAR_PAGE_TITLE,PAGE_TITLE);
        pageVariables.put(PAR_LOGIN,login);
        pageVariables.put(PAR_VALIDATION_STATUS,validated);
        pageVariables.put(PAR_VALIDATION_MESSAGE,VALIDATION_MESSAGE_LOGIN_OR_PASSWORD_ARE_INCORRECT);

        resp.getWriter().println(TemplateProcessor.instance().getPage(LOGIN_TEMPLATE, pageVariables));

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if(login != null && login.equals(adminUserName) && password != null && password.equals(adminPassword)){
            SessionUtils.setLoggedIn(req.getSession());

            resp.sendRedirect("/");
        }else{
            renderLogin(resp,login,false);
        }
    }


}
