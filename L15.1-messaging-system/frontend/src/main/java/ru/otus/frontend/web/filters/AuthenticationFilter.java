package ru.otus.frontend.web.filters;

import ru.otus.frontend.web.utils.SessionUtils;

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
    private final static String CSS_URL = "/css";
    public static final String JS_URL = "/js";

    public static final String WS_URL = "/ws";


    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        HttpSession session = req.getSession(false);

        //если запос на ресурсы, то сразу передаем следующему обработчику
        if(req.getRequestURI().substring(req.getContextPath().length()).startsWith(CSS_URL)){
            chain.doFilter(request,response);
        }

        //проверяем, что у пользователя есть сессия и он аутентифицирован как админ
        if((session == null || (!SessionUtils.isLoggedIn(session)))){
            if(uri.endsWith(WS_URL)){
                res.sendError(401);
            }else if(this.isAllowedUrls(uri)) {
                chain.doFilter(request, response);
            }else{
                res.sendRedirect(LOGIN_URL);
            }
        }else{
            //если пользователь аутентифицирован, то пускаем обработку дальше по цепочке
            chain.doFilter(request, response);
        }
    }

    protected boolean isAllowedUrls(String uri){
        return uri.startsWith(LOGIN_URL) || uri.startsWith(CSS_URL) || uri.startsWith(JS_URL);
    }

    public void destroy() {
    }

}