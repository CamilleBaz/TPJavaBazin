package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
@Repository
public class VehicleDao {

	private static VehicleDao instance = null;
	private VehicleDao() {
		
	}

	public static VehicleDao getInstance() {
		if (instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}

	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur,  nbplaces) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur,  nbplaces FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur,  nbplaces FROM Vehicle;";
	private static final String COUNT_VEHICLE_QUERY = "SELECT COUNT(id) AS count FROM Vehicle ";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur=?,  nbplaces=? WHERE id =?;"; 
	private static final String FIND_VEHICLES_RESA_BY_CLIENT_QUERY = "SELECT DISTINCT Vehicle.id, Vehicle.constructeur,  Vehicle.nbplaces " + 
			  "FROM Reservation " + 
			  "INNER JOIN Client ON Reservation.clientId= Client.id " + 
			  "INNER JOIN Vehicle ON Reservation.vehicleId = Vehicle.id " + 
			  "WHERE Reservation.clientId = ?";
	
	public long create(Vehicle vehicle) throws DaoException {
		long id = 0;
		try {
			Connection connection = ConnectionManager.getConnection(); // création objet connection
			PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			// génère un ID
			// insersion des variables
			ps.setString(1, vehicle.getConstructeur());
			ps.setLong(2, vehicle.getNbplaces());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys(); // prepared statement retourne l'ID généré
			if (resultSet.next()) // si le lien n'est pas nul
			{
				id = resultSet.getInt(1);
			} else {
				throw new DaoException();
			}
			ps.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DaoException();
		}
		return id;

	}

	public long delete(Vehicle vehicle) throws DaoException {
		long result ;
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY);
		){
			ps.setLong(1, vehicle.getId());  
			result = ps.executeUpdate();
			
			} catch (SQLException e) { 
				throw new DaoException(e.getMessage()); 
			}
		return result;
	}

	public Optional<Vehicle> findById(long id) throws DaoException {
		Optional<Vehicle> optvehicle;
		try (
			Connection connection = ConnectionManager.getConnection(); //création objet connection, lien avec la basse de données
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY);
		){		
			ps.setLong(1,id);
			ResultSet resultSet = ps.executeQuery();
			
			if (resultSet.next()) {
				// attribue le id, constructeur, nbplaces du véhicule trouvé à un nouveau véhicule pour stocker celui trouvé dans la bdd :
				Vehicle vehicle = new Vehicle( resultSet.getString("constructeur"), resultSet.getLong("nbplaces"));
				vehicle.setId(resultSet.getLong("id"));
				optvehicle = Optional.of(vehicle);
			} else {
				optvehicle = Optional.empty();
			}
			
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return optvehicle;
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> listeVehicle = new ArrayList<>();
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY);
		){		
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()){
			Vehicle vehicle = new Vehicle(resultSet.getString("constructeur"), resultSet.getLong("nbplaces"));	
			vehicle.setId(resultSet.getInt("id"));
			listeVehicle.add(vehicle); 
		}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return listeVehicle;
	}

	public static int count() throws DaoException{
	int nbrVehicle =0;
	try {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement ps = connection.prepareStatement(COUNT_VEHICLE_QUERY);
		ResultSet resultSet = ps.executeQuery();
		if(resultSet.next()){
			nbrVehicle = resultSet.getInt("count");
		}
		resultSet.close();
		ps.close();
		connection.close();

	} catch (SQLException e) {
		System.out.println(e.getMessage());
		throw new DaoException();
	}
	return nbrVehicle;
	}

	public static void update(Vehicle vehicle) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(UPDATE_VEHICLE_QUERY);
			){
			ps.setString(1, vehicle.getConstructeur());
			ps.setLong(2,  vehicle.getNbplaces());
			ps.setLong(3,  vehicle.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		}
	
	public List<Vehicle> findVehicleByReservClient(long Clientid) throws DaoException {
		List<Vehicle> listeVehicle = new ArrayList<>();
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_RESA_BY_CLIENT_QUERY);
		){		
			ps.setLong(1, Clientid);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()){
				Vehicle vehicle = new Vehicle();
				vehicle.setConstructeur(resultSet.getString("constructeur"));
				vehicle.setNbplaces(resultSet.getInt("nbplaces"));
				listeVehicle.add(vehicle);
			}
			} catch (SQLException e) {
				throw new DaoException(e.getMessage());
			}
		return listeVehicle;
	}
}
