package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {
	private Long id;
	private Long clientId;
	private Long vehicleId;
	private LocalDate debut;
	private LocalDate fin;

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public LocalDate getDebut() {
		return debut;
	}

	public void setDebut(LocalDate debut) {
		this.debut = debut;
	}

	public LocalDate getFin() {
		return fin;
	}

	public void setFin(LocalDate fin) {
		this.fin = fin;
	}

	public Reservation(long id, long clientId, long vehicleId, LocalDate debut, LocalDate fin) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.vehicleId = vehicleId;
		this.debut = debut;
		this.fin = fin;
	}
	
	public Reservation(long clientId, long vehicleId, LocalDate debut, LocalDate fin) {
		super();
		this.clientId = clientId;
		this.vehicleId = vehicleId;
		this.debut = debut;
		this.fin = fin;
	}


	@Override
	public String toString() {
		return "Reservation [id=" + id + ", client=" + clientId + ", vehicle=" + vehicleId + ", debut=" + debut + ", fin="
				+ fin + "]";
	}
}