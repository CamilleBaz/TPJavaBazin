package com.epf.rentmanager.ui;

import com.epf.rentmanager.ui.cli.CLI;
import com.epf.rentmanager.utils.IOUtils;

public class UI {


	public static void main(String[] args) {
		
		choixCLI() ;
	}
	
	private static void choixCLI() {
		System.out.println("Bonjour.\nQue voulez-vous faire ?\n\n");
		System.out.println("1 : Créer un client");
		System.out.println("2 : Chercher un client");
		System.out.println("3 : Supprimer un client");
		System.out.println("4 : Afficher tous les clients");
		System.out.println("5 : Créer un véhicule");
		System.out.println("6 : Chercher un véhicule");
		System.out.println("7 : Supprimer un véhicule");
		System.out.println("8 : Afficher tous les véhicules");
		System.out.println("9 : Creer une reservation");
		System.out.println("10 : Supprimer une reservation");
		System.out.println("11 : Afficher toutes les reservations");
		System.out.println("12 : Afficher les reservations d'un client");
		System.out.println("13 : Afficher les reservations d'un véhicule");
		System.out.println("14 : Compter les véhicules");
		System.out.println("15 : Lister les véhicules réservés par un client");

		int choix = IOUtils.readInt("") ;
		
		switch(choix) {
		case 1:
			CLI.createClient() ;
		  break; 
		case 3:
			CLI.deleteClient();
		case 4:
			CLI.getAllClients() ;
		  break; 
		case 5:
			CLI.createVehicle() ;
		  break; 
		case 7:
			CLI.deleteVehicle();
		  break;
		case 8:
			CLI.getAllVehicle();
		  break;
		case 9:
			CLI.createReservation();
		  break;
		case 10:
			CLI.deleteReservation();
		  break;
		case 11:
			CLI.getAllReservation();
		  break;
		case 12:
			CLI.getReservationByClient();
		  break;
		case 13:
			CLI.getReservationByVehicle();
		  break;
		case 14 :
			CLI.countVehicle();
		  break;
		case 15 :
			CLI.findVehicleByReservClient();
			break;
		default:
			System.out.println("Veuillez rentrer un nombre valide.\n");

		}
	}

}