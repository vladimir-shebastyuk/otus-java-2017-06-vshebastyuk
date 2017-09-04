package ru.otus.l131.web.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.l131.cache.CacheMonitoring;
import ru.otus.l131.web.TemplateProcessor;

import javax.servlet.ServletConfig;
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
@Configurable
public class CacheMonitoringServlet extends HttpServlet {
    private final static String MAIN_TEMPLATE = "cache.ftl";

    private final static String PAR_PAGE_TITLE = "pageTitle";
    private final static String PAR_CACHE_HIT_COUNT = "cacheHitCount";
    private final static String PAR_CACHE_MISS_COUNT = "cacheMissCount";
    private final static String PAR_CACHE_HIT_RATIO = "cacheHitRatio";

    private final static String PAGE_TITLE = "Cache Monitoring";

    private CacheMonitoring cacheMonitoring;

    @Autowired
    public void setCacheMonitoring(CacheMonitoring cacheMonitoring) {
        this.cacheMonitoring = cacheMonitoring;
    }

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        int hitCount = cacheMonitoring.getHitCount();
        int missCount = cacheMonitoring.getMissCount();
        double hitRatio = ((double)hitCount)/((double)(hitCount + missCount)) * 100.0;

        pageVariables.put(PAR_PAGE_TITLE,PAGE_TITLE);
        pageVariables.put(PAR_CACHE_HIT_COUNT,hitCount);
        pageVariables.put(PAR_CACHE_MISS_COUNT,missCount);
        pageVariables.put(PAR_CACHE_HIT_RATIO,hitRatio);

        resp.getWriter().println(TemplateProcessor.instance().getPage(MAIN_TEMPLATE, pageVariables));

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
