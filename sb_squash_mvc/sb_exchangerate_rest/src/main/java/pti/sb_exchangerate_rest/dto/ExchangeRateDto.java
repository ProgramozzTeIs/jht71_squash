package pti.sb_exchangerate_rest.dto;

public class ExchangeRateDto {

	private String currency;
	private double rate;
	private String date;
	
	
	public ExchangeRateDto(String currency, double rate, String date) {
		super();
		this.currency = currency;
		this.rate = rate;
		this.date = date;
	}
	
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public double getRate() {
		return rate;
	}
	
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
