package com.telephonie.packages;

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
		super();
		this.idDepot = idDepot;
		this.idUtilisateur = idUtilisateur;
		this.montant = montant;
		this.dateDepot = dateDepot;
	}
	
	private static final String ALL_DEPOT_ATTENTE = "select * from depot where IdDepot in (select IdDepot from depotAttente ) and IdDepot not in (select IdDepot from depotValider)";
	private static final String INSERT_DEPOT = "insert into depot (IdUtilisateur, Montant, DateDepot) values (?, ?, CURRENT_TIMESTAMP)";
	
	public List<Depot> getDepotAttente() throws SQLException{
		List<Depot> result = new ArrayList<Depot>();
		
		DbConnection con = new DbConnection();
		Connection c = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			c = con.getConnection();
			preparedStatement = c.prepareStatement(ALL_DEPOT_ATTENTE);
			rs = preparedStatement.executeQuery();
			rs.next();
			//while(rs.next()) {
				result.add(new Depot(rs.getString("idDepot"), rs.getString("idUtilisateur"), rs.getInt("montant"), rs.getTimestamp("dateDepot")));
			//}
			
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			if(c!=null) c.close();
			if(preparedStatement!=null) preparedStatement.close();
			if(rs!=null) rs.close();
		}
			
		
		return result;
	}
	
	public void insert() throws SQLException {
		DbConnection con = new DbConnection();
		Connection conn = null;
		PreparedStatement statement = null;
		try {		
			conn = con.getConnection();
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
}
