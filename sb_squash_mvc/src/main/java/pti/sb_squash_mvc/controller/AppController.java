package pti.sb_squash_mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pti.sb_squash_mvc.util.LoginValidator;

@Controller
public class AppController {
	
	private AppService service;
	private LoginValidator loginValidator;
	

	@Autowired
	public AppController(AppService service, LoginValidator loginValidator) {
		super();
		this.service = service;
		this.loginValidator = loginValidator;
	}




		

}
