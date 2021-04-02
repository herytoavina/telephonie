package com.springboot.springbootfirstapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsommationAppel {
	
	private String idForfait;
	private int valeurAppel;
	private int valeurAutre;
	
	public String getIdForfait() {
		return idForfait;
	}
	public void setIdForfait(String idForfait) {
		this.idForfait = idForfait;
	}
	public int getValeurAppel() {
		return valeurAppel;
	}
	public void setValeurAppel(int valeurAppel) {
		this.valeurAppel = valeurAppel;
	}
	public int getValeurAutre() {
		return valeurAutre;
	}
	public void setValeurAutre(int valeurAutre) {
		this.valeurAutre = valeurAutre;
	}
	
	public ConsommationAppel() {
		super();
	}
	
	public ConsommationAppel(String idForfait, int valeurAppel, int valeurAutre) {
		super();
		this.idForfait = idForfait;
		this.valeurAppel = valeurAppel;
		this.valeurAutre = valeurAutre;
	}
	
	public ConsommationAppel( int valeurAppel, int valeurAutre) {
		super();
		this.valeurAppel = valeurAppel;
		this.valeurAutre = valeurAutre;
	}
	
	private static final String INSERT_CONSOMMATION = "insert into consommationAppel (idForfait, valeur_appel, valeur_autre) values (?, ?, ?)";
	public void insert() throws SQLException {
		Connect c = new Connect();
		Connection conn = null;
		PreparedStatement statement = null;
		try {		
			conn = c.getConnection();
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(INSERT_CONSOMMATION);
			statement.setString(1, this.getIdForfait());
			statement.setInt(2, this.getValeurAppel());
			statement.setInt(3, this.getValeurAutre());
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
	
	public ConsommationAppel getConsommationAppel(String idForfait){
		final String CONSOMMATION_FORFAITS = "select * from consommationAppel where idForfait='"+idForfait+"'";
		Connect con = new Connect();
		ConsommationAppel result = new ConsommationAppel();
		try {
			Connection c = con.getConnection();
			PreparedStatement preparedStatement = c.prepareStatement(CONSOMMATION_FORFAITS);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				result = new ConsommationAppel(rs.getString("idForfait"), rs.getInt("valeur_appel"), rs.getInt("valeur_autre"));
			}
			c.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}
	
}
