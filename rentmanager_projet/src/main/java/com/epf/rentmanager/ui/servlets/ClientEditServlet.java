
package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
import com.epf.rentmanager.model.Client;

@WebServlet("/users/update")

public class ClientEditServlet extends HttpServlet {
	
	private static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
	
	@Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        }
		
    	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/update.jsp");
        Long id = Long.parseLong(request.getParameter("id"));
        
        try {
			request.setAttribute("client", clientService.findById(id));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
        
        dispatcher.forward(request, response);
    	}
	
		@Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			Long id = Long.parseLong(request.getParameter("id"));
	        
	    	try {
	            String nom = request.getParameter("last_name");
	            String prenom = request.getParameter("first_name");
	            String email = request.getParameter("email");
	            String naissanceStr = request.getParameter("date") ;
	            
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            LocalDate naissance = LocalDate.parse(naissanceStr, formatter);
	        
	            Client client = new Client(nom, prenom, email, naissance);
	            client.setId(id);
	        
	            clientService.update(client);
			
	            response.sendRedirect("http://localhost:8080/rentmanager/users");
				} catch (ServiceException e) {
					e.printStackTrace();
				}
	    	}
    
}