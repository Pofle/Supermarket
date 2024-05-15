package fr.miage.supermarket.models;


/**
 * Object class for the Shopping List
 * @author PaulineF
 */
public class Utilisateur {
	
	//Attributs
	private int id;
	
	private String nom;
	
	private String prenom;
	
	private String mail;

	//Getters
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public String getMail() {
		return mail;
	}
	
	//Setters
	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	


}
