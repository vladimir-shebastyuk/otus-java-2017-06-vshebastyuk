package ru.otus.l131.web.servlets;

import ru.otus.l131.web.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервлет главной страницы
 */
public class MainServlet extends HttpServlet {
    private final static String MAIN_TEMPLATE = "main.ftl";

    private final static String PAR_PAGE_TITLE = "pageTitle";

    private final static String PAGE_TITLE = "Main Page";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put(PAR_PAGE_TITLE,PAGE_TITLE);

        resp.getWriter().println(TemplateProcessor.instance().getPage(MAIN_TEMPLATE, pageVariables));

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
