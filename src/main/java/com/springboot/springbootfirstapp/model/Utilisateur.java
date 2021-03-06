package com.springboot.springbootfirstapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
	public Utilisateur(String idUtilisateur, String token) {
		super();
		this.idUtilisateur = idUtilisateur;
		Token = token;
	}
	private String idUtilisateur;
	private String nom;
	private String prenom;
	private String numero;
	private String mdp;
	private String Token;
	
	public String getToken() {
		return Token;
	}
	public void setToken(String token) {
		Token = token;
	}
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
	
	public Utilisateur Connection(String nom,String mdp) {
		Connect con = new Connect();
		Utilisateur result=new Utilisateur();
		try {
			String NUMERO_USER = "select * from utilisateur where nom='"+nom+"' and mdp=md5('"+mdp+"')";
			System.out.println(NUMERO_USER);
			Connection c = con.getConnection();
			PreparedStatement preparedStatement = c.prepareStatement(NUMERO_USER);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				result = new Utilisateur(rs.getString("idutilisateur"), rs.getString("nom"), rs.getString("prenom"), rs.getString("numero"),rs.getString("mdp"));
            }
			c.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	/*public String generateToken(String nom,String mdp) {
		String IdUtilisateur=null;
		try {
		Utilisateur utilisateur = Connection(prenom, mdp);
		if(utilisateur.numero !=null) {
			utilisateur.insert();
			IdUtilisateur =utilisateur.idUtilisateur;
		}
		}catch (Exception e) {
			System.out.println(e);
		}
		return IdUtilisateur;
	}*/
	
	public String  getToken(String IdUtilisateur) {
		Connect con = new Connect();
		Utilisateur result=new Utilisateur();
		try {
		String NUMERO_USER = "select * from UserToken where IdUtilisateur='"+IdUtilisateur +"'";
				Connection c = con.getConnection();
				PreparedStatement preparedStatement = c.prepareStatement(NUMERO_USER);
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()) {
					result = new Utilisateur(rs.getString(1), rs.getString(2));
	            }
				c.close();
			}
			catch (Exception e) {
				System.out.println(e);
			}
			return result.Token;
		}
	public String   getId(String token) {
		Connect con = new Connect();
		Utilisateur result=new Utilisateur();
		try {
		String NUMERO_USER = "select * from UserToken where token='"+ token +"'";
				Connection c = con.getConnection();
				PreparedStatement preparedStatement = c.prepareStatement(NUMERO_USER);
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()) {
					result = new Utilisateur(rs.getString(1), rs.getString(2));
	            }
				c.close();
			}
			catch (Exception e) {
				System.out.println(e);
			}
			return result.idUtilisateur;
		}
	 
	
		public void delete(String id) throws SQLException {
			Connect c = new Connect();
			Connection conn = null;
			PreparedStatement statement = null;
			try {		
				conn = c.getConnection();
				conn.setAutoCommit(false);
				String res= "delete from UserToken WHERE IdUtilisateur ='" + id +"'";
				statement = conn.prepareStatement(res);
				System.out.print(res);
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
	private static final String INSERT_SIMULATION = "insert into UserToken(IdUtilisateur) VALUES (?);";
	public void insert() throws SQLException {
		Connect c = new Connect();
		Connection conn = null;
		PreparedStatement statement = null;
		try {		
			conn = c.getConnection();
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(INSERT_SIMULATION);
			statement.setString(1, this.getIdUtilisateur());
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
