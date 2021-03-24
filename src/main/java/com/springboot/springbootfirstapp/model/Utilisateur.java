package com.springboot.springbootfirstapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
	private String idUtilisateur;
	private String nom;
	private String prenom;
	private String numero;
	private String mdp;
	
	public String getIdUtilisateur() {
		return idUtilisateur;
	}
	public void setIdUtilisateur(String idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	public Utilisateur() {
		super();
	}
	
	public Utilisateur(String idUtilisateur, String nom, String prenom, String numero, String mdp) {
		super();
		this.idUtilisateur = idUtilisateur;
		this.nom = nom;
		this.prenom = prenom;
		this.numero = numero;
		this.mdp = mdp;
	}
	
	public Utilisateur(String nom, String prenom, String numero, String mdp) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.numero = numero;
		this.mdp = mdp;
	}
	
	
	public List<Utilisateur> getNumeroById(String idUtilisateur) {
		Connect con = new Connect();
		List<Utilisateur> result = new ArrayList<Utilisateur>();
		try {
			String NUMERO_USER = "select * from utilisateur where idUtilisateur="+idUtilisateur;
			Connection c = con.getConnection();
			PreparedStatement preparedStatement = c.prepareStatement(NUMERO_USER);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				result.add(new Utilisateur(rs.getString("idUtilisateur"), rs.getString("nom"), rs.getString("prenom"), rs.getString("numero"), rs.getString("mdp")));
			}
			c.close();
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}
	
}
