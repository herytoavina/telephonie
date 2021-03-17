package com.springboot.springbootfirstapp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/welcome")
public class WelcomeController {
	
	//private static final String ALL_DEPOT_ATTENTE = "select * from depot where IdDepot in (select IdDepot from depotAttente ) and IdDepot not in (select IdDepot from depotValider)";
	
	@GetMapping("/depotAttente")
	public List<Depot> depotAttente(){
		Depot d = new Depot();
		List<Depot> result = d.depotAttente();
		return result;
		
	}
	
	@PostMapping("/addDepot")
	public void addDepot(@RequestBody Depot depot) {
		try {
			depot.insert();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
