package com.ita.u1.library.controller.listener;

import com.ita.u1.library.controller.SimpleExecutorService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ExecutorServiceListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SimpleExecutorService.getInstance();
    }
}
