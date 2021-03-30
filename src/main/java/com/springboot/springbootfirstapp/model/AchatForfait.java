package com.springboot.springbootfirstapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AchatForfait {
	private String idForfait;
	private String idUtilisateur;

	public String getIdForfait() {
		return idForfait;
	}
	public void setIdForfait(String idForfait) {
		this.idForfait = idForfait;
	}
	public String getIdUtilisateur() {
		return idUtilisateur;
	}
	public void setIdUtilisateur(String idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}
	public AchatForfait() {
		super();
	}
	
	public AchatForfait(String idForfait, String idUtilisateur) {
		super();
		this.setIdForfait(idForfait);
		this.setIdUtilisateur(idUtilisateur);
	}
	
	private static final String INSERT_ACHAT_FORFAIT = "insert into achatForfait values (?, ?)";
	public void insert() throws SQLException {
		Connect c = new Connect();
		Connection conn = null;
		PreparedStatement statement = null;
		try {		
			conn = c.getConnection();
			conn.setAutoCommit(false);
			
			statement = conn.prepareStatement(INSERT_ACHAT_FORFAIT);
			statement.setString(1, this.getIdForfait());
			statement.setString(2, this.getIdUtilisateur());
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
	
	private static final String VENTE_FORFAIT = "select nomForfait, count(idForfait) as vente as from achatForfait,forfait where achatForfait.idForfait=forfait.idForfait group by nomForfait ";
	public List<AchatForfait> getVenteForfait() {
		Connect con = new Connect();
		List<AchatForfait> result = new ArrayList<AchatForfait>();
		try {
			Connection c = con.getConnection();
			PreparedStatement preparedStatement = c.prepareStatement(VENTE_FORFAIT);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				result.add(new AchatForfait(rs.getString("nomForfait"), rs.getString("vente")));
			}
			c.close();
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}
	
}
