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
		
	private int duree;
	
	private int valeurOffre;
	
	private int appel;
	
	private int appelAutreOp;
	
	private int volumeMega;
	
	private int NbSms;
	
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

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public int getValeurOffre() {
		return valeurOffre;
	}

	public void setValeurOffre(int valeurOffre) {
		this.valeurOffre = valeurOffre;
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

	public int getVolumeMega() {
		return volumeMega;
	}

	public void setVolumeMega(int volumeMega) {
		this.volumeMega = volumeMega;
	}

	public int getNbSms() {
		return NbSms;
	}

	public void setNbSms(int nbSms) {
		NbSms = nbSms;
	}

	public static String getInsertForfait() {
		return INSERT_FORFAIT;
	}

	public static String getAllForfait() {
		return ALL_FORFAIT;
	}

	public Forfait() {super();}
	
	

	public Forfait(String nom, int duree, int valeurOffre, int appel, int appelAutreOp,
			int volumeMega, int nbSms) {
		super();
		this.nom = nom;
		this.duree = duree;
		this.valeurOffre = valeurOffre;
		this.appel = appel;
		this.appelAutreOp = appelAutreOp;
		this.volumeMega = volumeMega;
		this.NbSms = nbSms;
	}
	
	public Forfait(String idForfait, String nom, int duree, int valeurOffre, int appel, int appelAutreOp,
			int volumeMega, int nbSms) {
		super();
		this.idForfait = idForfait;
		this.nom = nom;
		this.duree = duree;
		this.valeurOffre = valeurOffre;
		this.appel = appel;
		this.appelAutreOp = appelAutreOp;
		this.volumeMega = volumeMega;
		this.NbSms = nbSms;
	}


	private static final String INSERT_FORFAIT = "insert into forfait(Nom, duree, valeur_offre, appel, appelAutreOp, volume_mega, nb_sms) (?, ?, ?, ?, ?, ?, ?)";
	public void insert() throws SQLException {
		Connect c = new Connect();
		Connection conn = null;
		PreparedStatement statement = null;
		try {		
			conn = c.getConnection();
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(INSERT_FORFAIT);
			statement.setString(1, this.getNom());
			statement.setInt(2, this.getDuree());
			statement.setInt(3, this.getValeurOffre());
			statement.setInt(4, this.getAppel());
			statement.setInt(5, this.getAppelAutreOp());
			statement.setInt(6, this.getVolumeMega());
			statement.setInt(7, this.getNbSms());
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
				result.add(new Forfait(rs.getString("idForfait"), rs.getString("nom"), rs.getInt("duree"), rs.getInt("valeur_offre"), rs.getInt("appel"), rs.getInt("appelAutreOp"), rs.getInt("volume_mega"), rs.getInt("nb_sms")));
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
				result.add(new Forfait(rs.getString("idForfait"), rs.getString("nom"), rs.getInt("duree"), rs.getInt("valeur_offre"), rs.getInt("appel"), rs.getInt("appelAutreOp"), rs.getInt("volume_mega"), rs.getInt("nb_sms")));
			}
			c.close();
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return result;
	}
	
}
