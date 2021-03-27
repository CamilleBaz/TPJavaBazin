package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.model.Vehicle;

@WebServlet("/cars/create")

public class VehicleCreateServlet extends HttpServlet {
	
	private static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
	
	@Autowired
	VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp");
        
        dispatcher.forward(request, response);
    }
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	try {
        String constructeur = request.getParameter("constructeur");
        long nbplaces = Long.parseLong(request.getParameter("nbplaces"));
       
        
        Vehicle vehicle = new Vehicle(constructeur, nbplaces);
        
		vehicleService.create(vehicle);
		
		response.sendRedirect("http://localhost:8080/rentmanager/cars");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
    }
    
}
