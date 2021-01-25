package com.bjpowernode.crm.web.listener;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicValueService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext application=event.getServletContext(); //获取application对象
        DicValueService dicValueService= WebApplicationContextUtils.getWebApplicationContext(application)
                .getBean(DicValueService.class);
        Map<String, List<DicValue>> map= dicValueService.getAll();
        Set<String> set=map.keySet(); //code  appellation  get("cpplicatioN");
        for(String key:set){
            application.setAttribute(key,map.get(key));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
