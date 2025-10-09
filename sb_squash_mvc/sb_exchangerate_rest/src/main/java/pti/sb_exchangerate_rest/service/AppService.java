package pti.sb_exchangerate_rest.service;

import java.io.IOException;
import java.util.List;

import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import pti.sb_exchangerate_rest.dto.ExchangeRateDto;
import pti.sb_exchangerate_rest.model.ExchangeRate;
import pti.sb_exchangerate_rest.xml.XMLParser;



@Service
public class AppService {

	private XMLParser xmlParser;
	
	
	@Autowired
	public AppService(XMLParser xmlParser) {
		super();
		this.xmlParser = xmlParser;
	}
	
	
	
	public ExchangeRateDto getEurRate() throws JDOMException, IOException {
		
		ExchangeRateDto exchangeRateDto = null;
		
		RestClient restClient = RestClient.create();
		
		String xml = restClient.get()
                .uri("http://api.napiarfolyam.hu/?bank=mnb")
                .retrieve()
                .body(String.class);
		
		List<ExchangeRate> rates = xmlParser.getExchangeRates(xml);		
		
		for(ExchangeRate rate : rates) {
            
			if (rate.getCurrency().equals("EUR")) {
                
                exchangeRateDto = new ExchangeRateDto(
                        rate.getCurrency(),
                        rate.getRate(),
                        rate.getDate());
                
                break;
			}
		}				
		return exchangeRateDto;
	}

}
