package pti.sb_exchangerate_rest.controller;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pti.sb_exchangerate_rest.dto.ExchangeRateDto;
import pti.sb_exchangerate_rest.service.AppService;


@RestController
public class AppController {

	private AppService service;

	@Autowired
	public AppController(AppService service) {
		super();
		this.service = service;
	}
	
	
	@GetMapping("/eur")
	public ExchangeRateDto getEurRate() throws JDOMException, IOException {
		
		ExchangeRateDto exhangeRateDto = service.getEurRate();
		
		return exhangeRateDto;
	}
	
	
	
	
}
