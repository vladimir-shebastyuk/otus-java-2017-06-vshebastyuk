package ru.otus.frontend.web.servlets;

import ru.otus.common.services.cache.CacheService;
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
public class CacheMonitoringServlet extends TemplateServlet {
    private final static String MAIN_TEMPLATE = "cache.ftl";
    private final static String PAGE_TITLE = "Cache Monitoring";

    @Override
    protected String getTemplate() {
        return MAIN_TEMPLATE;
    }

    @Override
    protected String getPageTitle() {
        return PAGE_TITLE;
    }
}
