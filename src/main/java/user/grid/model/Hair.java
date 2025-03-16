package user.grid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Hair {

	@Column(insertable = false, updatable = false)
    private String color;
	@Column(insertable = false, updatable = false)
    private String type;

}

