package com.springboot.springbootfirstapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Depot {
	private String idDepot;
	private String idUtilisateur;
	private int montant;
	private Timestamp dateDepot;
	
	public String getIdDepot() {
		return idDepot;
	}
	public void setIdDepot(String idDepot) {
		this.idDepot = idDepot;
	}
	public String getIdUtilisateur() {
		return idUtilisateur;
	}
	public void setIdUtilisateur(String idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}
	public int getMontant() {
		return montant;
	}
	public void setMontant(int montant) {
		this.montant = montant;
	}
	public Timestamp getDateDepot() {
		return dateDepot;
	}
	public void setDateDepot(Timestamp dateDepot) {
		this.dateDepot = dateDepot;
	}
	
	public Depot() {}
	public Depot(String idDepot, String idUtilisateur, int montant, Timestamp dateDepot) {
		this.idDepot = idDepot;
		this.idUtilisateur = idUtilisateur;
		this.montant = montant;
		this.dateDepot = dateDepot;
	}
	
	public Depot(String idUtilisateur, int montant) {
		this.idUtilisateur = idUtilisateur;
		this.montant = montant;
	}
	
	public Depot(String idDepot) {
		this.setIdDepot(idDepot);
	}
	
	private static final String INSERT_DEPOT = "insert into depot (IdUtilisateur, Montant, DateDepot) values (?, ?, CURRENT_TIMESTAMP)";
	@SuppressWarnings("resource")
	public void insert() throws SQLException {
		
		Connect c = new Connect();
		Connection conn = null;
		PreparedStatement statement = null;
		try {		
			conn = c.getConnection();
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(INSERT_DEPOT);
			statement.setString(1, this.getIdUtilisateur());
			statement.setInt(2, this.getMontant());
			
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
	
	private static final String INSERT_CONFIRMATION = "insert into depotvalider values (?)";
	@SuppressWarnings("resource")
	public void confirmation() throws SQLException {
		Connect c = new Connect();
		Connection conn = null;
		PreparedStatement statement = null;
		try {		
			conn = c.getConnection();
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(INSERT_CONFIRMATION);
			statement.setString(1, this.getIdDepot());
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
	
	private static final String ALL_DEPOT_ATTENTE = "select * from depot where IdDepot in (select IdDepot from depotAttente ) and IdDepot not in (select IdDepot from depotValider)";
	public List<Depot> depotAttente(){
		Connect con = new Connect();
		List<Depot> result = new ArrayList<Depot>();
		try {
			Connection c = con.getConnection();
			PreparedStatement preparedStatement = c.prepareStatement(ALL_DEPOT_ATTENTE);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				result.add(new Depot(rs.getString("idDepot"), rs.getString("idUtilisateur"), rs.getInt("montant"), rs.getTimestamp("dateDepot")));
			}
			c.close();
			
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			
		}
		return result;
	}
	public int getSolde(String idUtilisateur) {
		int result = 1000;
		Connect con = new Connect();
		try {
			Connection c = con.getConnection();
			String query = "select idUtilisateur, sum(Montant) as solde from depot where IdDepot in (select IdDepot from depotValider) and idUtilisateur ="+idUtilisateur+" group by idUtilisateur";
			PreparedStatement preparedStatement = c.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next())result = rs.getInt("solde");
			c.close();
			System.out.println(query);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}
	
}
