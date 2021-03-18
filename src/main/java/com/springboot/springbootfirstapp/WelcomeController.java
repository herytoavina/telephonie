package com.springboot.springbootfirstapp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import util.BuilderResponse;
import util.Meta;


@RestController
@RequestMapping("/welcome")
public class WelcomeController {
	
	//private static final String ALL_DEPOT_ATTENTE = "select * from depot where IdDepot in (select IdDepot from depotAttente ) and IdDepot not in (select IdDepot from depotValider)";
	
	@GetMapping("/depotAttente")
	public BuilderResponse depotAttente(){
		BuilderResponse response;
		try {
		Depot d = new Depot();
		List<Depot> result = d.depotAttente();
        response = new BuilderResponse(new Meta("200","valider"), result);
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
}
