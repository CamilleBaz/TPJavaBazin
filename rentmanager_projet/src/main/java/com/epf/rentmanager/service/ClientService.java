package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.utils.IOUtils;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;

@Service
public class ClientService {

	private ClientDao clientDao;
	private ReservationDao reservationDao;
	
   @Autowired
    private ClientService(ClientDao clientDao, ReservationDao reservationDao) {
        this.clientDao = clientDao;
        this.reservationDao = reservationDao;
    }
    
   /* public static ClientService instance;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		return instance;
	}*/
	
	
	public long create(Client client) throws ServiceException {
		long id;
		if(client.getNom() == null || client.getPrenom()== null ) {
			throw new ServiceException("Veuillez rentrer un nom et un prénom valide.");
		}
		try {
			client.getNom().toUpperCase();
			id = clientDao.create(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Une erreur a eu lieu lors de la création du client");
		}
		return id;
	}
	
	public void delete(Client client) throws ServiceException {
		try {
        List<Reservation> listeReservation = reservationDao.findResaByClient(client);
        if (listeReservation != null) {
	        for (int i = 0; i < listeReservation.size(); i++) {
	        	reservationDao.delete(listeReservation.get(i).getId());
	        	}
        	}
        clientDao.delete(client); 
		} catch (DaoException e) {
		e.printStackTrace();
		throw new ServiceException("Une erreur a eu lieu lors de la suppression d'un client ou d'une réservation");
		}
	}

	public Client findById(long id) throws ServiceException {
		Client client ;
		try {
			Optional<Client> optclient = clientDao.findById(id);
			if (optclient != null) {
				client = optclient.get();
			} else {
                throw new ServiceException("L'utilisateur n°" + id + " n'a pas été trouvé dans la base de données");
            }
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Une erreur a eu lieu lors de la récupération de l'utilisateur");
		}
		return client;	
	}

	public List<Client> findAll() throws ServiceException {
		List<Client> listeClient = new ArrayList<>();
		try {
			listeClient = clientDao.findAll();
        } catch (DaoException e) {
        	e.printStackTrace();
            throw new ServiceException("Erreur au niveau de la recherche dans la BDD");
        }
		return listeClient;
	}
	
	public List<Client> findClientByReservVehicle(long vehicleId) throws ServiceException{
	List<Client> listeClient = new ArrayList<>();
	try {
		listeClient = clientDao.findClientByReservVehicle(vehicleId);
	} catch (DaoException e) {
		e.printStackTrace();
		throw new ServiceException("Erreur au niveau de la recherche dans la BDD");
	}
	return listeClient;
}
	 
	public int count() throws ServiceException{
		int nbrClient;
		try {
			nbrClient = clientDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Erreur lors de la modification de la BDD");
		}
		return nbrClient;
	}
	
	public void update(Client client) throws ServiceException {
		try {
			clientDao.update(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Erreur lors de la modification de la BDD");
		}
	}
	
}
