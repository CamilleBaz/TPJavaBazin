package com.epf.rentmanager.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

@Repository
public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {
	}

	public static ReservationDao getInstance() {
		if (instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(clientId, vehicleId, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicleId, debut, fin FROM Reservation WHERE clientId=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, clientId, debut, fin FROM Reservation WHERE vehicleId=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, clientId, vehicleId, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(id) AS count FROM Reservation;";
	private static final String FIND_RESERVATION_QUERY = "SELECT Reservation.clientId, Client.nom, Client.prenom, Client.email, Client.naissance, " +
			 "Reservation.vehicleId, Vehicle.constructeur, Vehicle.nbplaces, Reservation.debut, Reservation.fin FROM Reservation " +
			 "INNER JOIN Client ON Reservation.clientId = Client.id " + 
			 "INNER JOIN Vehicle ON Reservation.vehicleId = Vehicle.id " +
			 "WHERE Reservation.id=?;";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET clientId=?, vehicleId=?, debut=?, fin=? WHERE id =?;"; 
	
	public long create(Reservation reservation) throws DaoException {
		long id = 0;
		try {
			Connection connection = ConnectionManager.getConnection(); // création objet connection
			PreparedStatement ps = connection.prepareStatement(CREATE_RESERVATION_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			// génère un ID
			// insersion des variables
			ps.setLong(1, reservation.getClientId());
			ps.setLong(2, reservation.getVehicleId());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));
			ps.executeUpdate();
			ResultSet resulset = ps.getGeneratedKeys(); // prepared statement retourne l'ID généré

			if (resulset.next()) // si le lien n'est pas nul
			{
				id = resulset.getLong(1);
			} 

		} catch (SQLException e) {
			throw new DaoException();
		}
		return id;
	}

	public long delete(long id) throws DaoException {
		long result;
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY);) {
			ps.setLong(1, id);
			result = ps.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return result;
	}

	public List<Reservation> findResaByClient(Client client) throws DaoException {
		List<Reservation> listeReservation = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);) {
			ps.setLong(1, client.getId());
			ResultSet resulset = ps.executeQuery();

			while (resulset.next()) {
				Vehicle vehicle = new Vehicle(resulset.getString("Vehicle.constructeur"), resulset.getLong("Vehicle.nbplaces"));
				vehicle.setId(resulset.getLong("vehicleId"));

				Reservation reservation = new Reservation(client.getId(), vehicle.getId(), resulset.getDate("debut").toLocalDate(), resulset.getDate("fin").toLocalDate());
				reservation.setId(resulset.getLong("id"));
				listeReservation.add(reservation);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return listeReservation;
	}

	public List<Reservation> findResaByVehicle(Vehicle vehicle) throws DaoException {
		List<Reservation> listeReservation = new ArrayList<>();
		try (
			Connection connection = ConnectionManager.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			){
	        ps.setLong(1, vehicle.getId());
			ResultSet resulset = ps.executeQuery(); 
			while (resulset.next()) {
	        	Client client = new Client(resulset.getString("Client.nom"), resulset.getString("Client.prenom"), resulset.getString("Client.email"), resulset.getDate("Client.naissance").toLocalDate());
	        	client.setId(resulset.getLong("clientId"));
	        	
				Reservation reservation = new Reservation(client.getId(), vehicle.getId(), resulset.getDate("debut").toLocalDate(), resulset.getDate("fin").toLocalDate());
				reservation.setId(resulset.getLong("id"));
				listeReservation.add(reservation);
			} 
			
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return listeReservation;
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> listeReservation = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_QUERY);
			ResultSet resulset = ps.executeQuery();
			while (resulset.next()) {
				//Client client = new Client(resulset.getString("nom"), resulset.getString("prenom"), resulset.getString("email"), resulset.getDate("naissance").toLocalDate());
	        	//client.setId(resulset.getLong("clientId"));
	        	
	        	//Vehicle vehicle = new Vehicle(resulset.getLong("id");
	        	//vehicle.setId(resulset.getLong("id"));
				
				
				Reservation reservation = new Reservation(resulset.getLong("clientId"), resulset.getLong("vehicleId"), resulset.getDate("debut").toLocalDate(), resulset.getDate("fin").toLocalDate());
				reservation.setId(resulset.getLong("id"));
				listeReservation.add(reservation);
			}
			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException();
		}
		return listeReservation;
	}

	public int count() throws DaoException {
		int nbrReservation = 0;
		try (
				Connection connection = ConnectionManager.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(COUNT_RESERVATIONS_QUERY);
			){
			ResultSet resulset = ps.executeQuery();
			if (resulset.next()){
				nbrReservation = resulset.getInt("count");
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		
		return nbrReservation;
	}
	
	public Optional<Reservation> findById(long id) throws DaoException {
		Optional<Reservation> optreservation;
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATION_QUERY);
		){
	        ps.setLong(1,id);
			ResultSet resultSet = ps.executeQuery(); 
			
			if (resultSet.next()) {
	        	Client client = new Client(resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("email"), resultSet.getDate("naissance").toLocalDate());
	        	client.setId(resultSet.getLong("clientId"));
	        	
	        	Vehicle vehicle = new Vehicle(resultSet.getString("constructeur"), resultSet.getLong("nbplaces"));
	        	vehicle.setId(resultSet.getLong("vehicleId"));
	        	
				Reservation reservation = new Reservation(client.getId(), vehicle.getId(), resultSet.getDate("debut").toLocalDate(), resultSet.getDate("fin").toLocalDate());
				reservation.setId(id);
				
				optreservation = Optional.of(reservation);
			} else {
				optreservation = Optional.empty();
			}
	        
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return optreservation;
	}
	public void update(Reservation reservation) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(UPDATE_RESERVATION_QUERY);
			){
			ps.setLong(1, reservation.getClientId());
			ps.setLong(2, reservation.getVehicleId());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));
			ps.setLong(5,  reservation.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

}
