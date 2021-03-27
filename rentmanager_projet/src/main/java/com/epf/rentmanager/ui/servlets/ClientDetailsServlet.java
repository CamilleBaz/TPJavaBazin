package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

@WebServlet("/users/details")

public class ClientDetailsServlet extends HttpServlet {
	
	private static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
	
	@Autowired
    ClientService clientService;
	@Autowired
	ReservationService reservationService;
	@Autowired
	VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/details.jsp");
        
        Long clientId = Long.parseLong(request.getParameter("id"));
        
        try {        	
        	Client client = clientService.findById(clientId);
			request.setAttribute("client", client);
			
			List<Reservation> listeReservation = reservationService.findResaByClient(client);
			request.setAttribute("nbrReservation", listeReservation.size());
			
			request.setAttribute("reservations", listeReservation);
			
			List<Vehicle> listeVehicle = vehicleService.findVehicleByReservClient(clientId);
			request.setAttribute("nbrvehicle", listeVehicle.size());
			
			request.setAttribute("vehicles", listeVehicle);
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
        dispatcher.forward(request, response);
       
    }
}