package ru.otus.frontend.web.servlets;

import ru.otus.frontend.web.utils.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class TemplateServlet extends HttpServlet {
    private final static String PAR_PAGE_TITLE = "pageTitle";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put(PAR_PAGE_TITLE, getPageTitle());

        resp.getWriter().println(TemplateProcessor.instance().getPage(getTemplate(), pageVariables));

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    protected String getTemplate() {
        return "";
    }

    protected String getPageTitle() {
        return "";
    }
}
