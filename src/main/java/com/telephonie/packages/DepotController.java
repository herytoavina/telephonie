package com.telephonie.packages;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/depot")
public class DepotController {
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Hello World!";
	}
	
	@GetMapping("/depotAttente")
	public List<Depot> depotAttente(){
		Depot d = new Depot();
		try {
			return d.getDepotAttente();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/addDepot")
	public void addDepot(@RequestParam Depot depot) {
		try {
			depot.insert();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
