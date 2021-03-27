package com.epf.rentmanager.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//import com.epf.rentmanager.service.VehicleService;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));
    }



}