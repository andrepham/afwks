package afwks.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class Address {
	
	@Column(nullable=false)
	private Integer number;
	
	@Column(nullable=false)
	private String street;
	
	@Column(nullable=false)
	private String town;
	
	@Column(nullable=false)
	private String country;
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	
}
