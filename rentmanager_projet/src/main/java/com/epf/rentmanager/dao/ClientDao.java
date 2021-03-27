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
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

@Repository
public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	

	/*private static ClientDao instance = null;

	private ClientDao() {
	}*/

	public static ClientDao getInstance() {
		if (instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(id) AS count FROM Client;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom=?,  prenom=?, email=?, naissance=? WHERE id =?;"; 
	private static final String FIND_CLIENTS_BY_RESA_VEHICLE_QUERY = "SELECT DISTINCT Client.id, Client.nom, Client.prenom, Client.email, Client.naissance " + 
			"FROM Reservation " + 
			"INNER JOIN Client ON Reservation.client_id= Client.id " + 
			"INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id " + 
			"WHERE Reservation.vehicle_id = ?";

	public long create(Client client) throws DaoException {
		long id = 0;
		try (Connection connection = ConnectionManager.getConnection(); // création objet connection
				PreparedStatement ps = connection.prepareStatement(CREATE_CLIENT_QUERY,
						Statement.RETURN_GENERATED_KEYS);) {// génère un ID // insersion des variables
			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys(); // prepared statement retourne l'ID généré

			if (resultSet.next()) // si le lien n'est pas nul
			{
				id = resultSet.getInt(1);
			} else {
				throw new DaoException();
			}

		} catch (SQLException e) {
			throw new DaoException();
		}
		return id;
	}

	public long delete(Client client) throws DaoException {
		long result;
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE_CLIENT_QUERY);) {
			ps.setLong(1, client.getId());
			result = ps.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return result;
	}

	public Optional<Client> findById(long id) throws DaoException {
		Optional<Client> optclient;
		try {
			Connection connection = ConnectionManager.getConnection(); // création objet connection
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENT_QUERY);

			ps.setLong(1, id);
			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				Client client = new Client(resultSet.getString("nom"), resultSet.getString("prenom"),
						resultSet.getString("email"), resultSet.getDate("naissance").toLocalDate());
				client.setId(id);
				optclient = Optional.of(client);

			} else {
				optclient = Optional.empty();
			}
			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException();
		}
		return optclient;
	}

	public List<Client> findAll() throws DaoException {
		List<Client> listeClient = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_QUERY);
			ResultSet resulset = ps.executeQuery();
			while (resulset.next()) {
				Client client = new Client(resulset.getString("nom"), resulset.getString("prenom"),
						resulset.getString("email"), resulset.getDate("naissance").toLocalDate());
				client.setId(resulset.getLong("id"));
				listeClient.add(client);
			}
			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException();
		}
		return listeClient;
	}

	public int count() throws DaoException {
		int nbrClient = 0;
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(COUNT_CLIENTS_QUERY);) {
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				nbrClient = resultSet.getInt("count");
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}

		return nbrClient;
	}

	public void update(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_CLIENT_QUERY);) {
			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));
			ps.setLong(5, client.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	public List<Client> findClientByReservVehicle(long vehicleId) throws DaoException {
		List<Client> listeClient = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_BY_RESA_VEHICLE_QUERY);) {
			ps.setLong(1, vehicleId);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Client client = new Client(resultSet.getString("nom"), resultSet.getString("prenom"),
						resultSet.getString("email"), resultSet.getDate("naissance").toLocalDate());
				client.setId(resultSet.getLong("id"));
				listeClient.add(client);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return listeClient;
	}

}
