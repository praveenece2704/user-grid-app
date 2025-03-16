package user.grid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;

@Data
@Embeddable
public class Company {

	@Column(insertable = false, updatable = false)
    private String department;
	@Column(insertable = false, updatable = false)
    private String name;
	@Column(insertable = false, updatable = false)
    private String title;

    @Embedded
    private Address address;
}
