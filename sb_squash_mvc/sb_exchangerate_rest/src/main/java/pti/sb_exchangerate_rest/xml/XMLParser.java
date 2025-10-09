package pti.sb_exchangerate_rest.xml;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Component;

import pti.sb_exchangerate_rest.model.ExchangeRate;

@Component
public class XMLParser {

	public List<ExchangeRate> getExchangeRates(String xml) throws JDOMException, IOException{
		
		List<ExchangeRate> rates = new ArrayList<>();
		
		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(new StringReader(xml));
		
		Element rootElement = doc.getRootElement();
		
		Element devizaElement = rootElement.getChild("deviza");
		
		List<Element> itemElements = devizaElement.getChildren("item");
		
		for(Element itemElement : itemElements) {
			
			String currency = itemElement.getChildText("penznem");
			String rateText = itemElement.getChildText("kozep");
			String dateText = itemElement.getChildText("datum");
			
			double rate = Double.parseDouble(rateText);
			
			rates.add(new ExchangeRate(currency, rate, dateText));
		}
	
		return rates;
	}
}
