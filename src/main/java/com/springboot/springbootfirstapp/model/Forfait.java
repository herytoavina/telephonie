package com.springboot.springbootfirstapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Forfait {
	
	private String idForfait;
	private String typeForfait;
	private	String nomForfait;
	private int prixForfait;
	private int dureeForfait;
	private int valeurAppel;
	private String modeAppel;
	private int volumeMega;
	private int nbSms;

	public String getIdForfait() {
		return idForfait;
	}

	public void setIdForfait(String idForfait) {
		this.idForfait = idForfait;
	}

	public String getNomForfait() {
		return nomForfait;
	}

	public void setNomForfait(String nomForfait) {
		this.nomForfait = nomForfait;
	}

	public int getPrixForfait() {
		return prixForfait;
	}

	public void setPrixForfait(int prixForfait) {
		this.prixForfait = prixForfait;
	}

	public int getDureeForfait() {
		return dureeForfait;
	}

	public void setDureeForfait(int dureeForfait) {
		this.dureeForfait = dureeForfait;
	}

	public int getValeurAppel() {
		return valeurAppel;
	}

	public void setValeurAppel(int valeurAppel) {
		this.valeurAppel = valeurAppel;
	}

	public String getModeAppel() {
		return modeAppel;
	}

	public void setModeAppel(String modeAppel) {
		this.modeAppel = modeAppel;
	}

	public int getVolumeMega() {
		return volumeMega;
	}

	public void setVolumeMega(int volumeMega) {
		this.volumeMega = volumeMega;
	}

	public int getNbSms() {
		return nbSms;
	}

	public String getTypeForfait() {
		return typeForfait;
	}

	public void setTypeForfait(String typeForfait) {
		this.typeForfait = typeForfait;
	}

	public void setNbSms(int nbSms) {
		this.nbSms = nbSms;
	}
	
	public Forfait() {}
	
	public Forfait(String idForfait, String typeForfait, String nomForfait, int prixForfait, int dureeForfait, int valeurAppel,
			String modeAppel, int volumeMega, int nbSms) {
		super();
		this.setIdForfait(idForfait);
		this.setTypeForfait(typeForfait);
		this.setNomForfait(nomForfait);
		this.setPrixForfait(prixForfait);
		this.setDureeForfait(dureeForfait);
		this.setValeurAppel(valeurAppel);
		this.setModeAppel(modeAppel);
		this.setVolumeMega(volumeMega);
		this.setNbSms(nbSms);
	}
	
	public Forfait(String typeForfait, String nomForfait, int prixForfait, int dureeForfait, int valeurAppel,
			String modeAppel, int volumeMega, int nbSms) {
		super();
		this.setTypeForfait(typeForfait);
		this.setNomForfait(nomForfait);
		this.setPrixForfait(prixForfait);
		this.setDureeForfait(dureeForfait);
		this.setValeurAppel(valeurAppel);
		this.setModeAppel(modeAppel);
		this.setVolumeMega(volumeMega);
		this.setNbSms(nbSms);
	}


	private static final String INSERT_FORFAIT = "insert into forfait(typeForfait, nomForfait, prixForfait, dureeForfait, valeur_appel, mode_appel, volume_mega, nb_sms) values (?, ?, ?, ?, ?, ?, ?)";
	public void insert() throws SQLException {
		Connect c = new Connect();
		Connection conn = null;
		PreparedStatement statement = null;
		try {		
			conn = c.getConnection();
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(INSERT_FORFAIT);
			statement.setString(1, this.getTypeForfait());
			statement.setString(2, this.getNomForfait());
			statement.setInt(3, this.getPrixForfait());
			statement.setInt(4, this.getDureeForfait());			
			statement.setInt(5, this.getValeurAppel());
			statement.setString(6, this.getModeAppel());
			statement.setInt(7, this.getVolumeMega());
			statement.setInt(8, this.getNbSms());
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
				result.add(new Forfait(rs.getString("idForfait"), rs.getString("nomForfait"), rs.getInt("prixForfait"), rs.getInt("dureeForfait"), rs.getInt("valeur_appel"), rs.getString("mode_appel"), rs.getInt("volume_mega"), rs.getInt("nb_sms")));
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
			String ALL_FORFAIT_USER = "select idUtilisateur,forfait.* from forfait,achatForfait where forfait.idForfait=achatForfait.idForfait and idUtilisateur="+idUtilisateur;
			Connection c = con.getConnection();
			PreparedStatement preparedStatement = c.prepareStatement(ALL_FORFAIT_USER);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				result.add(new Forfait(rs.getString("idForfait"), rs.getString("nomForfait"), rs.getInt("prixForfait"), rs.getInt("dureeForfait"), rs.getInt("valeur_appel"), rs.getString("mode_appel"), rs.getInt("volume_mega"), rs.getInt("nb_sms")));
			}
			c.close();
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}
	
}
