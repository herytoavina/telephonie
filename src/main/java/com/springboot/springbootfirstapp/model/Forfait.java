package com.springboot.springbootfirstapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Forfait {
	private String idForfait;
	private String nom;
	private int tarif;
	private int appel;
	private int appelAutreOp;
	public String getIdForfait() {
		return idForfait;
	}
	public void setIdForfait(String idForfait) {
		this.idForfait = idForfait;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getTarif() {
		return tarif;
	}
	public void setTarif(int tarif) {
		this.tarif = tarif;
	}
	public int getAppel() {
		return appel;
	}
	public void setAppel(int appel) {
		this.appel = appel;
	}
	public int getAppelAutreOp() {
		return appelAutreOp;
	}
	public void setAppelAutreOp(int appelAutreOp) {
		this.appelAutreOp = appelAutreOp;
	}
	
	public Forfait() {}
	public Forfait(String idForfait, String nom, int tarif, int appel, int appelAutreOp) {
		super();
		this.idForfait = idForfait;
		this.nom = nom;
		this.tarif = tarif;
		this.appel = appel;
		this.appelAutreOp = appelAutreOp;
	}
	
	private static final String INSERT_FORFAIT = "insert into forfait(Nom, tarif, Appel, AppelAutreOp) values (?, ?, ?, ?);";
	public void insert() throws SQLException {
		Connect c = new Connect();
		Connection conn = null;
		PreparedStatement statement = null;
		try {		
			conn = c.getConnection();
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(INSERT_FORFAIT);
			statement.setString(1, this.getNom());
			statement.setInt(2, this.getTarif());
			statement.setInt(3, this.getAppel());
			statement.setInt(4, this.getAppelAutreOp());
			statement.execute();
			conn.commit();
		} catch (Exception ex) {
			conn.rollback();
			System.out.println(ex);
		} finally {
			if(statement != null) statement.close();
			if(conn != null) conn.close();
		}
		
	}
	
	private static final String ALL_FORFAIT = "select * from forfait";
	public List<Forfait> listeForfait(){
		Connect con = new Connect();
		List<Forfait> result = new ArrayList<Forfait>();
		try {
			Connection c = con.getConnection();
			PreparedStatement preparedStatement = c.prepareStatement(ALL_FORFAIT);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				result.add(new Forfait(rs.getString("idForfait"), rs.getString("nom"), rs.getInt("tarif"), rs.getInt("appel"), rs.getInt("appelAutreOp")));
			}
			c.close();
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}
	
	public List<Forfait> listeForfaitUser(String idUtilisateur){
		Connect con = new Connect();
		List<Forfait> result = new ArrayList<Forfait>();
		try {
			String ALL_FORFAIT_USER = "select * from forfait where idForfait in (select idForfait from achatForfait where idUtilisateur="+idUtilisateur+")";
			Connection c = con.getConnection();
			PreparedStatement preparedStatement = c.prepareStatement(ALL_FORFAIT_USER);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				result.add(new Forfait(rs.getString("idForfait"), rs.getString("nom"), rs.getInt("tarif"), rs.getInt("appel"), rs.getInt("appelAutreOp")));
			}
			c.close();
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}
	
}
