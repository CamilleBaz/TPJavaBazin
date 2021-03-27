package com.epf.rentmanager.service;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
//import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
//import com.epf.rentmanager.persistence.ConnectionManager;
//import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	private ReservationDao reservationDao;
  @Autowired
    private VehicleService(VehicleDao vehicleDao,ReservationDao reservationDao) {
        this.vehicleDao = vehicleDao;
        this.reservationDao = reservationDao;
    }
    
	/*public static VehicleService instance;

	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}*/

    public long create(Vehicle vehicle) throws ServiceException {
    	
		if (vehicle.getConstructeur() == null || vehicle.getNbplaces() < 1) {
            throw new ServiceException("Veuillez Saisir un constructeur. Le nombre de place doit etre supérieur à 1");
        }
        try {
            return vehicleDao.create(vehicle);
        } catch (DaoException e) {
        	e.printStackTrace();
            throw new ServiceException("Erreur lors de la création de l'utilisateur dans la BDD");
        }
	}

	public Vehicle findById(long id) throws ServiceException {
		// TODO: récupérer un véhicule par son id
		Vehicle vehicle;
		try {
			Optional<Vehicle> optvehicle = vehicleDao.findById(id);
			if (optvehicle.isPresent()) {
				vehicle = optvehicle.get();
			} else {
				throw new ServiceException("Le véhicule n'existe pas");
			}
			return vehicle;

		} catch (DaoException e) {
			throw new ServiceException("Erreur au niveau de la recherche dans la BDD");
		}
	}
	public void delete(Vehicle vehicle) throws ServiceException {
		try {
		List<Reservation> listeReservation = reservationDao.findResaByVehicle(vehicle);
        if (listeReservation != null) {
	        for (int i = 0; i < listeReservation.size(); i++) {
	        	reservationDao.delete(listeReservation.get(i).getId());
	        	}
        	}
        vehicleDao.delete(vehicle); 
		} catch (DaoException e) {
			e.printStackTrace();
		throw new ServiceException("Une erreur a eu lieu lors de la suppression d'un véhicule ou d'une réservation");
		}
	}
	
	public List<Vehicle> findAll() throws ServiceException {
		try {
			List<Vehicle> listeVehicle = vehicleDao.findAll();
			if (listeVehicle != null) {
				return listeVehicle;
			} else {
                throw new ServiceException("Aucun véhicule n'a pas été trouvé dans la base de données");
            }
        } catch (DaoException e) {
        	e.printStackTrace();
            throw new ServiceException("Erreur au niveau de la recherche dans la BDD");
        }
	}

	public List<Vehicle> findVehicleByReservClient(long Clientid) throws ServiceException{
		List<Vehicle> listeVehicle = new ArrayList<>();
		try {
			listeVehicle = vehicleDao.findVehicleByReservClient(Clientid);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Erreur au niveau de la recherche dans la BDD");
		}
		return listeVehicle;
	}


	public int count() throws ServiceException {
		int nbrVehicle;
		try {
			nbrVehicle = VehicleDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Erreur au niveau de la recherche dans la BDD");
		}
		return nbrVehicle;
	}

	public void update(Vehicle Vehicle) throws ServiceException {
		try {
			VehicleDao.update(Vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Erreur lors de la modification de la BDD");
		}
	}


	

	

}
