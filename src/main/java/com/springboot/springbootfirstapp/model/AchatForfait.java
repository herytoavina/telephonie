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
	
	private String nomForfait;
	private int vente;
	private int mois;
	
	public String getNomForfait() {
		return nomForfait;
	}
	public void setNomForfait(String nomForfait) {
		this.nomForfait = nomForfait;
	}
	public int getVente() {
		return vente;
	}
	public void setVente(int vente) {
		this.vente = vente;
	}
	public int getMois() {
		return mois;
	}
	public void setMois(int mois) {
		this.mois = mois;
	}
	
	public AchatForfait(String nomForfait, int vente, int mois) {
		super();
		this.nomForfait = nomForfait;
		this.vente = vente;
		this.mois = mois;
	}


	private static final String INSERT_ACHAT_FORFAIT = "insert into achatForfait values (?, ?, CURRENT_TIMESTAMP)";
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
	
	private static final String VENTE_FORFAIT = "select nomForfait, count(achatForfait.idForfait) as vente, EXTRACT(MONTH from achatforfait.dateachat) as mois  from achatForfait,forfait where achatForfait.idForfait=forfait.idForfait group by nomForfait,mois ";
	public List<AchatForfait> getVenteForfait() {
		Connect con = new Connect();
		List<AchatForfait> result = new ArrayList<AchatForfait>();
		try {
			Connection c = con.getConnection();
			PreparedStatement preparedStatement = c.prepareStatement(VENTE_FORFAIT);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				result.add(new AchatForfait(rs.getString("nomForfait"), rs.getInt("vente"), rs.getInt("mois")));
			}
			c.close();
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}
	
}
