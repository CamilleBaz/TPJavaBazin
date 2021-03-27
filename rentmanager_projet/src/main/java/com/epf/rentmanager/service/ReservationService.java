package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;
//import java.util.ListIterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
//import com.epf.rentmanager.utils.IOUtils;
//import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;

@Service
public class ReservationService {

	private ReservationDao reservationDao;

	@Autowired
	private ReservationService(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}

	
	 /* private ClientDao clientDao; 
	  public static ReservationService instance;
	  
	  private ReservationService() { this.reservationDao =
	  ReservationDao.getInstance(); this.clientDao = ClientDao.getInstance(); }
	 
	  public static ReservationService getInstance() { if (instance == null) {
	  instance = new ReservationService(); }
	  
	 return instance; }*/
	 

	public long create(Reservation reservation) throws ServiceException {
		// TODO: créer un reservation
		long id;
		try {
			id = reservationDao.create(reservation);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la création de la réservation");
		}
		return id;
	}

	public long delete(long id) throws ServiceException {
		try {
			reservationDao.delete(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Une erreur a eu lieu lors de la suppression de la réservation");
		}
		return id;
	}

	public Reservation findById(long id) throws ServiceException {
		Reservation reservation = null;
		try {
			Optional<Reservation> optreservation = reservationDao.findById(id);
			if (optreservation != null) {
				reservation = optreservation.get();
			} else {
				throw new ServiceException("La réservation n°" + id + " n'a pas été trouvée dans la base de données");
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return reservation;
	}

	public List<Reservation> findResaByClient(Client client) throws ServiceException {
		List<Reservation> listeReservation = new ArrayList<>();
		try {
			listeReservation = reservationDao.findResaByClient(client);
		} catch (DaoException e) {
			throw new ServiceException("Erreur au niveau de la recherche dans la BDD");
		}
		return listeReservation;
	}

	public List<Reservation> findResaByVehicle(Vehicle vehicle) throws ServiceException {
		List<Reservation> listeReservation = new ArrayList<>();
		try {
			listeReservation = reservationDao.findResaByVehicle(vehicle);
			if (listeReservation.size() == 0) {
				throw new ServiceException("Le véhicule n'a pas été réservé");
			}
		} catch (DaoException e) {
			throw new ServiceException("Erreur au niveau de la recherche dans la BDD");
		}
		return listeReservation;
	}

	public List<Reservation> findAll() throws ServiceException {
		List<Reservation> listeReservation = new ArrayList<>();
		try {
			listeReservation = reservationDao.findAll();
			if (listeReservation.size() == 0) {
				throw new ServiceException("Aucune réservation n'a été trouvée dans la base de données.");
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
		return listeReservation;
	}

	public int count() throws ServiceException {
		int nbrReservation;
		try {
			nbrReservation = reservationDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Erreur lors de la modification de la BDD");
		}
		return nbrReservation;
	}

	public void update(Reservation reservation) throws ServiceException {
		try {
			reservationDao.update(reservation);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Erreur lors de la modification de la BDD");
		}
	}

}