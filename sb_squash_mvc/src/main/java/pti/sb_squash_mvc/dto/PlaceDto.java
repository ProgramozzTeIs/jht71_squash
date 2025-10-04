package pti.sb_squash_mvc.dto;

public class PlaceDto {

	
	private int id;
	private String name;
	private String address;
	private int rentFeeHuf;
	
	
	public PlaceDto(int id, String name, String address, int rentFeeHuf) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.rentFeeHuf = rentFeeHuf;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRentFeeHuf() {
		return rentFeeHuf;
	}

	public void setRentFeeHuf(int rentFeeHuf) {
		this.rentFeeHuf = rentFeeHuf;
	}
	
	
	
}
