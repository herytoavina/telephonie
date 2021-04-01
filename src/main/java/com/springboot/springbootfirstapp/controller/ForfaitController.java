package com.springboot.springbootfirstapp.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.springbootfirstapp.model.AchatForfait;
import com.springboot.springbootfirstapp.model.ConsommationAppel;
import com.springboot.springbootfirstapp.model.Forfait;

import util.BuilderResponse;
import util.Meta;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/forfait")
public class ForfaitController {

	
	@PostMapping("/addForfait")
	public BuilderResponse addForfait(@RequestBody Forfait forfait) {
		BuilderResponse response;
		try {
			forfait.insert();
			response = new BuilderResponse(new Meta("200","valider"), null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response = new BuilderResponse(new Meta("500","error"), null);
		}
		return response;
	}
	
	@PostMapping("/addConsommation")
	public BuilderResponse addConsommation(@RequestBody ConsommationAppel appel) {
		BuilderResponse response;
		try {
			appel.insert();
			response = new BuilderResponse(new Meta("200","valider"), null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response = new BuilderResponse(new Meta("500","error"), null);
		}
		return response;
	}
	
	@GetMapping("/listeForfait")
	public BuilderResponse listeForfait(){
		BuilderResponse response;
		try {
		Forfait f = new Forfait();
		List<Forfait> result = f.listeForfait();
        response = new BuilderResponse(new Meta("200","valider"), result);
		}
		catch (Exception e) {
			// TODO: handle exception
            response= new BuilderResponse(new Meta("500","error"),null);
		}
		return response;
		
	}
	
	@PostMapping("/achatForfait")
	public BuilderResponse achatForfait(@RequestBody AchatForfait achat) {
		BuilderResponse response;
		try {
			achat.insert();
			response = new BuilderResponse(new Meta("200","valider"), null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response = new BuilderResponse(new Meta("500","error"), null);
		}
		return response;
	}
	
	@GetMapping("/getVenteForfait")
	public BuilderResponse getVenteForfait(){
		BuilderResponse response;
		try {
		AchatForfait f = new AchatForfait();
		List<AchatForfait> result = f.getVenteForfait();
        response = new BuilderResponse(new Meta("200","valider"), result);
		}
		catch (Exception e) {
			// TODO: handle exception
            response= new BuilderResponse(new Meta("500","error"),null);
		}
		return response;
		
	}
	
	@GetMapping("/listeForfaitUser")
	public BuilderResponse listeForfaitUser(@RequestParam(value = "idUtilisateur")String idUtilisateur){
		BuilderResponse response;
		try {
		Forfait f = new Forfait();
		List<Forfait> result = f.listeForfaitUser(idUtilisateur);
        response = new BuilderResponse(new Meta("200","valider"), result);
		}
		catch (Exception e) {
			// TODO: handle exception
            response= new BuilderResponse(new Meta("500","error"),null);
		}
		return response;
		
	}
	
}
