package com.springboot.springbootfirstapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
	private String idUtilisateur;
	private String idSimulation;
	private String monNumero;
	private String numero;
	private int duree;
	private String typeSimulation;
	
	public String getIdUtilisateur() {
		return idUtilisateur;
	}
	public void setIdUtilisateur(String idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}
	public String getIdSimulation() {
		return idSimulation;
	}
	public void setIdSimulation(String idSimulation) {
		this.idSimulation = idSimulation;
	}
	public String getMonNumero() {
		return monNumero;
	}
	public void setMonNumero(String monNumero) {
		this.monNumero = monNumero;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public int getDuree() {
		return duree;
	}
	public void setDuree(int duree) {
		this.duree = duree;
	}
	
	public String getTypeSimulation() {
		return typeSimulation;
	}
	public void setTypeSimulation(String typeSimulation) {
		this.typeSimulation = typeSimulation;
	}
	public Simulation() {
		super();
	}
	
	public Simulation(String idSimulation, String idUtilisateur, String monNumero, String numero, int duree, String typeSimulation) {
		super();
		this.setIdUtilisateur(idUtilisateur);
		this.idSimulation = idSimulation;
		this.monNumero = monNumero;
		this.numero = numero;
		this.duree = duree;
		this.setTypeSimulation(typeSimulation);
	}
	
	public Simulation(String idUtilisateur, String monNumero, String numero, int duree, String typeSimulation) {
		super();
		this.setIdUtilisateur(idUtilisateur);
		this.monNumero = monNumero;
		this.numero = numero;
		this.duree = duree;
		this.setTypeSimulation(typeSimulation);
	}
	
	private static final String INSERT_SIMULATION = "insert into simulation (idUtilisateur, monNumero, numero, duree, typeSimulation) values (?, ?, ?, ?, ?)";
	public void insert() throws SQLException {
		Connect c = new Connect();
		Connection conn = null;
		PreparedStatement statement = null;
		try {		
			conn = c.getConnection();
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(INSERT_SIMULATION);
			statement.setString(1, this.getIdUtilisateur());
			statement.setString(2, this.getMonNumero());
			statement.setString(3, this.getNumero());
			statement.setInt(4, this.getDuree());
			statement.setString(5, this.getTypeSimulation());
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
	
	public List<Simulation> getSimulationById(String idUtilisateur, String typeSimulation) {
		Connect con = new Connect();
		List<Simulation> results = new ArrayList<Simulation>();
		Simulation result = new Simulation();
		try {
			String NUMERO_USER = "select * from simulation where idUtilisateur='"+idUtilisateur+"' and typeSimulation='"+typeSimulation+"'";
			Connection c = con.getConnection();
			PreparedStatement preparedStatement = c.prepareStatement(NUMERO_USER);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				result = new Simulation(rs.getString("idSimulation"), rs.getString("monNumero"), rs.getString("numero"), rs.getInt("duree"), rs.getString("typeSimulation"));
				results.add(result);
			}
			c.close();
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return results;
	}

	public boolean isAutreOperateur(String monNumero, String numero) {
		
		String monOp = monNumero.substring(3);
		String AutreOp = numero.substring(3);

		if(monOp.equals(AutreOp)) return false;
		
		return true;
	}
	
	public List<Forfait> getCout(String idUtilisateur) {
		List<Simulation> simulations = new ArrayList<Simulation>();
		simulations = new Simulation().getSimulationById(idUtilisateur, "appel");
		
		List<Simulation> simulationsSms = new ArrayList<Simulation>();
		simulations = new Simulation().getSimulationById(idUtilisateur, "sms");
		
		List<Simulation> simulationsInternet = new ArrayList<Simulation>();
		simulations = new Simulation().getSimulationById(idUtilisateur, "internet");
		
		List<Forfait> forfaits = new Forfait().listeForfaitUser(idUtilisateur);
	
		/**************************SIMULATION APPEL*********************************/
		if(simulations != null) {
			for(int j=0; j<simulations.size(); j++) {
				Simulation simulation = simulations.get(j);
				for(int i=0;i<forfaits.size();i++) {
					int appel = forfaits.get(i).getAppel();
					boolean isAutreOperateur = simulation.isAutreOperateur(simulation.getMonNumero(), simulation.getNumero());
					if(isAutreOperateur) appel = forfaits.get(i).getAppelAutreOp();
					int valeurSimulation = simulation.getDuree()*appel;
					int valeurOffre = forfaits.get(i).getValeurOffre();
					int reste = valeurOffre - valeurSimulation;
					
					if(reste > 0) {
						forfaits.get(i).setValeurOffre(reste);
						//result = forfaits.get(i).getValeurOffre();
						break;
					} 
					if(reste == 0) {
						forfaits.get(i).setValeurOffre(0);
						break;
					}
					if(reste < 0) {
						forfaits.get(i).setValeurOffre(0);
						simulation.setDuree(simulation.getDuree()-(valeurOffre*appel));
					}
				}
		
			}
		}
		
		
		/**************************SIMULATION SMS*********************************/
		if(simulationsSms != null) {
			for(int j=0; j<simulationsSms.size(); j++) {
				Simulation simulation = simulationsSms.get(j);
				for(int i=0;i<forfaits.size();i++) {
					int reste = forfaits.get(i).getNbSms()-simulation.getDuree();
					
					if(reste > 0) {
						forfaits.get(i).setNbSms(reste);
						//result = forfaits.get(i).getValeurOffre();
						break;
					} 
					if(reste == 0) {
						forfaits.get(i).setNbSms(0);
						break;
					}
					if(reste < 0) {
						forfaits.get(i).setNbSms(0);
						simulation.setDuree((-1)*reste);
					}
				}
		
			}
		}
		
		/**************************SIMULATION MEGA*********************************/
		if(simulationsInternet != null) {
			for(int j=0; j<simulationsInternet.size(); j++) {
				Simulation simulation = simulationsInternet.get(j);
				for(int i=0;i<forfaits.size();i++) {
					int reste = forfaits.get(i).getVolumeMega()-simulation.getDuree();
					
					if(reste > 0) {
						forfaits.get(i).setVolumeMega(reste);
						//result = forfaits.get(i).getValeurOffre();
						break;
					} 
					if(reste == 0) {
						forfaits.get(i).setVolumeMega(0);
						break;
					}
					if(reste < 0) {
						forfaits.get(i).setVolumeMega(0);
						simulation.setDuree((-1)*reste);
					}
				}
		}
		
	
		}

		return forfaits;
	}
}

