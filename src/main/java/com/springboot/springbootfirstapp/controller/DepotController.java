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

import com.springboot.springbootfirstapp.model.Depot;

import util.BuilderResponse;
import util.Meta;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/depot")
public class DepotController {
	
	//private static final String ALL_DEPOT_ATTENTE = "select * from depot where IdDepot in (select IdDepot from depotAttente ) and IdDepot not in (select IdDepot from depotValider)";
	
	@GetMapping("/depotAttente")
	public BuilderResponse depotAttente(){
		BuilderResponse response;
		try {
		Depot d = new Depot();
		List<Depot> result = d.depotAttente();
        response = new BuilderResponse(new Meta("200","error"), result);
		}
		catch (Exception e) {
			// TODO: handle exception
            response= new BuilderResponse(new Meta("500","error"),null);
		}
		return response;
		
	}
	
	@PostMapping("/addDepot")
	public BuilderResponse addDepot(@RequestBody Depot depot) {
		BuilderResponse response;
		try {
			depot.insert();
			response = new BuilderResponse(new Meta("200","valider"), null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response = new BuilderResponse(new Meta("500","error"), null);
		}
		return response;
	}
	
	@GetMapping("/getSolde")
	public BuilderResponse getSolde(@RequestParam(value="idUtilisateur")String idUtilisateur) {
		Depot depot = new Depot();
		BuilderResponse response;
		List<Integer> result= new ArrayList<Integer>();
		try {
			 result.add(depot.getSolde(idUtilisateur));
			response = new BuilderResponse(new Meta("200","valider"), result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response = new BuilderResponse(new Meta("500","error"), null);
		}
		return response;
	}
}
