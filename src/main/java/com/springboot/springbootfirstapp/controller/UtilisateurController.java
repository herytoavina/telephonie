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

import com.springboot.springbootfirstapp.model.Utilisateur;

import util.BuilderResponse;
import util.Meta;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {
	
	@GetMapping("/getNumeroById")
	public BuilderResponse getNumeroById(@RequestParam(value = "idUtilisateur")String idUtilisateur){
		BuilderResponse response;
		try {
		Utilisateur d = new Utilisateur();
		List<Utilisateur> result = d.getNumeroById(idUtilisateur);
        response = new BuilderResponse(new Meta("200","valider"), result);
		}
		catch (Exception e) {
			// TODO: handle exception
            response= new BuilderResponse(new Meta("500","error"),null);
		}
		return response;
		
	}
	
	@PostMapping("/postToken")
	public BuilderResponse LoginController(@RequestBody Utilisateur utilisateur) {
		BuilderResponse response;
		Utilisateur utils	= utilisateur.Connection(utilisateur.getNom(),utilisateur.getMdp());
		try {
			if(utils.getNom().equals((utils).getNom())) {
				utils.insert();
				List<String> result= new ArrayList<String>();
				String resultat= utils.getToken(utils.getIdUtilisateur());
				result.add(resultat);
				response = new BuilderResponse(new Meta("200","valider"), result);
			}
			else {
				response = new BuilderResponse(new Meta("502","error"), null);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response = new BuilderResponse(new Meta("500","error"), null);
		}
		return response;
	}
	
	/*@GetMapping("/getToken")
	public BuilderResponse GetTokens(@RequestParam(value = "idUtilisateur")String idUtilisateur){
		BuilderResponse response;
		try {
			List<String> result= new ArrayList<String>();
			Utilisateur d = new Utilisateur();
			String answer=d.getToken(idUtilisateur);
			result.add(answer);
        response = new BuilderResponse(new Meta("200","valider"), result);
		}
		catch (Exception e) {
			// TODO: handle exception
            response= new BuilderResponse(new Meta("500","error"),null);
		}
		return response;
		
	}
	@GetMapping("/getIdUtilisateur")
	public BuilderResponse getIdUtilisateur(@RequestParam(value = "token")String token){
		BuilderResponse response;
		try {
			List<String> result= new ArrayList<String>();
			Utilisateur d = new Utilisateur();
			String answer=d.getId(token);
			result.add(answer);
        response = new BuilderResponse(new Meta("200","valider"), result);
		}
		catch (Exception e) {
			// TODO: handle exception
            response= new BuilderResponse(new Meta("500","error"),null);
		}
		return response;
	}*/
	
	@GetMapping("/Validation")
	public BuilderResponse Validation(@RequestParam(value = "token")String token){
		BuilderResponse response;
		try {
			List<String> result= new ArrayList<String>();
			Utilisateur d = new Utilisateur();
			String answer=d.getId(token);
			result.add(answer);

        response = new BuilderResponse(new Meta("200","valider"), result);
		}
		catch (Exception e) {
			// TODO: handle exception
            response= new BuilderResponse(new Meta("500","error"),null);
		}
		return response;
	}
	
}
