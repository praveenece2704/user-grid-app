package user.grid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;

@Data
@Embeddable
public class Address {

	@Column(insertable = false, updatable = false)
	private String address;
	@Column(insertable = false, updatable = false)
	private String city;
	@Column(insertable = false, updatable = false)
	private String state;
	@Column(insertable = false, updatable = false)
	private String stateCode;
	@Column(insertable = false, updatable = false)
	private String postalCode;

	@Embedded
	private Coordinates coordinates;
	@Column(insertable = false, updatable = false)
	private String country;

}
