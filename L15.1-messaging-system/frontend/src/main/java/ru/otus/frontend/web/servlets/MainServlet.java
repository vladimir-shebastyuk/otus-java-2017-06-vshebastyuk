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
 * Сервлет главной страницы
 */
public class MainServlet extends TemplateServlet {
    private final static String MAIN_TEMPLATE = "main.ftl";
    private final static String PAGE_TITLE = "Main Page";

    @Override
    protected String getTemplate() {
        return MAIN_TEMPLATE;
    }

    @Override
    protected String getPageTitle() {
        return PAGE_TITLE;
    }
}
