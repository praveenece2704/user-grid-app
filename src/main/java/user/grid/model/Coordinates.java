package user.grid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Coordinates {

	@Column(insertable = false, updatable = false) // Avoid duplication of lat and lng columns
    private Double lat;

    @Column(insertable = false, updatable = false) // Avoid duplication of lat and lng columns
    private Double lng;
}
