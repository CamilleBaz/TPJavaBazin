package com.epf.rentmanager.ui.cli;

import java.time.LocalDate;
//import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

import com.epf.rentmanager.configuration.AppConfiguration;
//import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.ServiceException;
//import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

@Repository
public class CLI {
	
	private static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
	
	private static ClientService clientService = context.getBean(ClientService.class);
	private static VehicleService vehicleService = context.getBean(VehicleService.class);
	private static ReservationService reservationService = context.getBean(ReservationService.class);
	
	//CLIENT
	
    public static void createClient() { //créer un client
        IOUtils.print("Pour créer un nouveau client, remplissez les champs :\n");
        String nom = IOUtils.readString("Nom:", true);
        String prenom = IOUtils.readString("Prenom:", true);
        String email = IOUtils.readString("Email:", true);
        LocalDate naissance = IOUtils.readDate("Date de naissance:", true);
        try {
            Client client = new Client(nom, prenom, email, naissance);
            clientService.create(client);
            IOUtils.print("Opération réussie");
        } catch (ServiceException e) {
            IOUtils.print("Une erreur est survenue\n" + e.getMessage());
            e.printStackTrace();
       }
    }
    
    public static void getAllClients() { //lister tous les clients
        IOUtils.print(" Voici la liste de tous les clients :");
        try {
        	List<Client> listeClient = clientService.findAll() ; //récupération de l'arrayliste retournée par la méthode findAll
        	ListIterator<Client> iteratorClient = listeClient.listIterator(); // on créée un intérateur de cette liste pour pouvoir la parcourir
        	while(iteratorClient.hasNext())
                IOUtils.print(iteratorClient.next().toString());
        } catch (ServiceException e) {
            IOUtils.print("Une erreur est survenue\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deleteClient() { //supprimer un client
        IOUtils.print(" Quel client voulez-vous supprimer parmis tous ces derniers ?\n");
        getAllClients();
        Long id = (long) IOUtils.readInt("Veuillez rentrer son id :");
        try {
        	Client client = clientService.findById(id);
        	clientService.delete(client);
            IOUtils.print("Opération réussie, le client d'id n°" + id + " a été supprimé");
        } catch (ServiceException e) {
            IOUtils.print("Une erreur est survenue\n" + e.getMessage());
            e.printStackTrace();
       }
    }
    
    //véhicule
	
    public static void createVehicle() { //créer un véhicule
        IOUtils.print("Pour créer un nouveau véhicule, remplissez les champs :\n");
        String constructeur = IOUtils.readString("Constructeur :", true);
        int nbplaces = IOUtils.readInt("Nombre de places :") ;
        try {
        	Vehicle vehicle = new Vehicle(constructeur, nbplaces);
            vehicleService.create(vehicle); 
            IOUtils.print("Opération réussie");
        } catch (ServiceException e) {
            IOUtils.print("Une erreur est survenue\n" + e.getMessage());
            e.printStackTrace();
       }
    }
    
    public static void getAllVehicle() { //lister tous les véhicules
        IOUtils.print(" Voici la liste de tous les véhicules :");
        try {
        	List<Vehicle> listeVehicle = vehicleService.findAll() ; //récupération de l'arrayliste retournée par la méthode findAll
        	ListIterator<Vehicle> iteratorVehicle = listeVehicle.listIterator(); // on créée un intérateur de cette liste pour pouvoir la parcourir
        	while(iteratorVehicle.hasNext())
                IOUtils.print(iteratorVehicle.next().toString());
        } catch (ServiceException e) {
            IOUtils.print("Une erreur est survenue\n" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void deleteVehicle() { //supprimer un véhicule
        IOUtils.print(" Quel véhicule voulez-vous supprimer parmis tous ces derniers ?\n");
        getAllVehicle();
        Long id = (long) IOUtils.readInt("Veuillez rentrer son id :");
        try {
            Vehicle vehicle = vehicleService.findById(id);
            vehicleService.delete(vehicle);
            IOUtils.print("Opération réussie, le client d'id n°" + id + " a été supprimé");
        } catch (ServiceException e) {
            IOUtils.print("Une erreur est survenue\n" + e.getMessage());
            e.printStackTrace();
       }
    }
    
    public static void countVehicle() { //compter les véhicules
        try {
        	int nbrVehicle;
        	nbrVehicle = vehicleService.count();
            IOUtils.print(" Il y a " + nbrVehicle + " véhicules");
        } catch (ServiceException e) {
            IOUtils.print("Une erreur est survenue\n" + e.getMessage());
            e.printStackTrace();
       }
    }
    
    public static void findVehicleByReservClient() { //compter les véhicules
        try {
        	getAllClients();
        	long Clientid = (long) IOUtils.readInt("Veuillez rentrer l'id du client :");
        	List<Vehicle> listeVehicle = vehicleService.findVehicleByReservClient(Clientid) ;
        	IOUtils.print(" Voici la liste de tous les véhicules :");
        	ListIterator<Vehicle> iteratorVehicle = listeVehicle.listIterator(); 
        	while(iteratorVehicle.hasNext())
                IOUtils.print(iteratorVehicle.next().toString());
        } catch (ServiceException e) {
            IOUtils.print("Une erreur est survenue\n" + e.getMessage());
            e.printStackTrace();
       }
    }
    
	//RESERVATION
	
    public static void createReservation() { //créer une réservation
        getAllClients();
        Long clientId = (long) IOUtils.readInt("Qui fait la réservation parmis les clients ci-dessus ? (rentrer son id) :");
        getAllVehicle();
        Long vehicleId = (long) IOUtils.readInt("Quel est le véhicule réservé ? (rentrer son id)");
        LocalDate debut = IOUtils.readDate("Date de début de réservation :", true);
        LocalDate fin = IOUtils.readDate("Date de fin de réservation :", true);
        try {
        	Client client = clientService.findById(clientId);
        	Vehicle vehicle = vehicleService.findById(vehicleId);
        	
            Reservation reservation = new Reservation(clientId, vehicleId, debut, fin);
            reservationService.create(reservation);
            
            IOUtils.print("La réservation est créée");
        } catch (ServiceException e) {
            IOUtils.print("Une erreur est survenue\n" + e.getMessage());
            e.printStackTrace();
       }
    }
    
    public static void deleteReservation() { //supprimer une réservation
        IOUtils.print(" Quelle réservation voulez-vous supprimer parmis toutes ces dernières ?\n");
        getAllReservation();
        Long id = (long) IOUtils.readInt("Veuillez rentrer son id :");
        try {
        	reservationService.delete(id);
            IOUtils.print("Opération réussie, la réservation d'id n°" + id + " a été supprimée");
        } catch (ServiceException e) {
            IOUtils.print("Une erreur est survenue\n" + e.getMessage());
       }
    }
    
    public static void getReservationByClient() { //lister toutes les réservations d'un client
        try {
        	getAllClients();
        	Long clientId = (long) IOUtils.readInt(" De quel client voulez vous voir les réservations ? (rentrer son id)\n");
        	Client client = clientService.findById(clientId);
        	List<Reservation> listeReservation = reservationService.findResaByClient(client) ; 
        	ListIterator<Reservation> iteratorReservation = listeReservation.listIterator(); 
        	IOUtils.print(" Voici les réservations faites par le client n°" + clientId);
        	while(iteratorReservation.hasNext())
                IOUtils.print(iteratorReservation.next().toString());
        } catch (ServiceException e) {
            IOUtils.print("Une erreur est survenue\n" + e.getMessage());
        }
    }
    
    public static void getReservationByVehicle() { //lister toutes les réservations d'un véhicule
        try {
        	getAllVehicle();
        	Long vehicleId = (long) IOUtils.readInt(" De quel véhicule voulez vous voir les réservations ? (rentrer son id)\n");
        	Vehicle vehicle = vehicleService.findById(vehicleId);
        	List<Reservation> listeReservation = reservationService.findResaByVehicle(vehicle) ; 
        	ListIterator<Reservation> iteratorReservation = listeReservation.listIterator(); 
        	IOUtils.print(" Voici les réservations du véhicule n°:" + vehicle);
        	while(iteratorReservation.hasNext())
                IOUtils.print(iteratorReservation.next().toString());
        } catch (ServiceException e) {
            IOUtils.print("Une erreur est survenue\n" + e.getMessage());
        }
    }
    
    public static void getAllReservation() { //lister toutes les réservations
        try {
        	List<Reservation> listeReservation = reservationService.findAll() ; 
        	ListIterator<Reservation> iteratorReservation = listeReservation.listIterator(); 
        	IOUtils.print(" Voici la liste de toutes les réservations :");
        	while(iteratorReservation.hasNext())
                IOUtils.print(iteratorReservation.next().toString());
        } catch (ServiceException e) {
            IOUtils.print("Une erreur est survenue\n" + e.getMessage());
        }
    }

}