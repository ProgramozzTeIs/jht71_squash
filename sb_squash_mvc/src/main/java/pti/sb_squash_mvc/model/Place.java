package pti.sb_squash_mvc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name ="places")
public class Place {

	@Id
	@Column(value ="id")
	private int id;
	 
	@Column(value ="name")
	private String name;
	
	@Column(value ="address")
	private String address;
	
	@Column(value ="rent_fee_huf")
	private int rentFee;

	public Place() {
		super();
	}

	public Place(int id, String name, String address, int rentFee) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.rentFee = rentFee;
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

	public int getRentFee() {
		return rentFee;
	}

	public void setRentFee(int rentFee) {
		this.rentFee = rentFee;
	}

	@Override
	public String toString() {
		return "Place [id=" + id + ", name=" + name + ", address=" + address + ", rentFee=" + rentFee + "]";
	}
	
}
