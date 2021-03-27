package com.epf.rentmanager.model;

public class Vehicle {
	 private long id;
	 private String constructeur;
	// private String modele;
	 private long nbplaces;
	 
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getConstructeur() {
		return constructeur;
	}
	public void setConstructeur(String constructeur) {
		this.constructeur = constructeur;
	}
/*	public String getModele() {
		return modele;
	}
	public void setModele(String modele) {
		this.modele = modele;
	}*/

	public long getNbplaces() {
		return nbplaces;
	}
	public void setNbplaces(long nbplaces) {
		this.nbplaces = nbplaces;
	}

	public Vehicle() {
		super();
		/*this.id = id;
		this.constructeur = constructeur;
		this.modele = modele;
		this.nbplaces = nbplaces;*/
			
	}


	public Vehicle(String constructeur,  long nbplaces) {
		super();
		this.constructeur = constructeur;
		this.nbplaces = nbplaces;
			
	}


	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", constructeur=" + constructeur +  ", nbplaces=" + nbplaces
				+ "]";
	}

}