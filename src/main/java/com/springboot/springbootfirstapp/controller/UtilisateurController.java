package com.springboot.springbootfirstapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	
}
