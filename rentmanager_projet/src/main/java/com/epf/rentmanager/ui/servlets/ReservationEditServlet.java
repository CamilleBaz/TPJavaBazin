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
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;

@WebServlet("/rents/update")

public class ReservationEditServlet extends HttpServlet {
	
private static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
	
	@Autowired
    ClientService clientService;
	@Autowired
	VehicleService vehicleService;
	@Autowired
	ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        }

    	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/update.jsp");
        Long id = Long.parseLong(request.getParameter("id"));
    	
        try {
			request.setAttribute("clients", clientService.findAll());
			request.setAttribute("vehicles", vehicleService.findAll());
			request.setAttribute("reservation", reservationService.findById(id));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
        
        dispatcher.forward(request, response);
    }
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Long id = Long.parseLong(request.getParameter("id"));
		
		try {
		Long vehicleId = Long.parseLong(request.getParameter("car"));
		Vehicle vehicle = vehicleService.findById(vehicleId);
		
		Long clientId = Long.parseLong(request.getParameter("client"));
		Client client = clientService.findById(clientId);
		
        String debutStr = request.getParameter("begin");
        String finStr = request.getParameter("end") ;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate debut = LocalDate.parse(debutStr, formatter);
        LocalDate fin = LocalDate.parse(finStr, formatter);
        
        Reservation reservation = new Reservation(clientId, vehicleId, debut, fin);
        reservation.setId(id);
            
        reservationService.update(reservation);
		
		response.sendRedirect("http://localhost:8080/rentmanager/rents");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
    }
    
}
