package ru.otus.l121.web.filters;

import ru.otus.l121.web.SessionUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Фильтр для редиректа неаутентифицированных пользователей на страницу логина
 */
public class AuthenticationFilter implements Filter {
    private final static String LOGIN_URL = "/login";
    private final static String RESOURCES_URL = "/css";

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        HttpSession session = req.getSession(false);

        //если запос на ресурсы, то сразу передаем следующему обработчику
        if(req.getRequestURI().substring(req.getContextPath().length()).startsWith(RESOURCES_URL)){
            chain.doFilter(request,response);
        }

        //проверяем, что у пользователя есть сессия и он аутентифицирован как админ
        if((session == null || (!SessionUtils.isLoggedIn(session)))&& !uri.endsWith(LOGIN_URL)){
            res.sendRedirect(LOGIN_URL);
        }else{
            //если пользователь аутентифицирован, то пускаем обработку дальше по цепочке
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
    }

}