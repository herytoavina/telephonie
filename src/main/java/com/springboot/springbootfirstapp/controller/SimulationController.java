package com.springboot.springbootfirstapp.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.springbootfirstapp.model.Forfait;
import com.springboot.springbootfirstapp.model.Simulation;

import util.BuilderResponse;
import util.Meta;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/simulation")
public class SimulationController {
	
	@PostMapping("/addSimulation")
	public BuilderResponse addSimulation(@RequestBody Simulation simulation) {
		BuilderResponse response;
		try {
			simulation.insert();
			response = new BuilderResponse(new Meta("200","valider"), null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response = new BuilderResponse(new Meta("500","error"), null);
		}
		return response;
	}
	
	@GetMapping("/getCout")
	public BuilderResponse getCout(@RequestParam(value="idUtilisateur")String idUtilisateur) {
		Simulation simulation = new Simulation();
		BuilderResponse response;
		List<Forfait> forfaits = new ArrayList<Forfait>();
		try {
			 forfaits = simulation.getCout(idUtilisateur);
			response = new BuilderResponse(new Meta("200","valider"), forfaits);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response = new BuilderResponse(new Meta("500","error"), null);
		}
		return response;
	}
	
	@GetMapping("/getHistorique")
	public BuilderResponse getHistoriqueAppel(@RequestParam(value="idUtilisateur")String idUtilisateur) {
		Simulation simulation = new Simulation();
		BuilderResponse response;
		List<Simulation> historique = simulation.getSimulationById(idUtilisateur, "appel");
		try {
			response = new BuilderResponse(new Meta("200","valider"), historique);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response = new BuilderResponse(new Meta("500","error"), null);
		}
		return response;
	}
}
