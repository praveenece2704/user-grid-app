package user.grid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Crypto {

	@Column(insertable = false, updatable = false)
    private String coin;
	@Column(insertable = false, updatable = false)
    private String wallet;
	@Column(insertable = false, updatable = false)
    private String network;
}

