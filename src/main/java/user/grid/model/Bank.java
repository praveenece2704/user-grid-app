package user.grid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
@Data
@Embeddable
public class Bank {

	@Column(insertable = false, updatable = false)
    private String cardExpire;
	@Column(insertable = false, updatable = false)
    private String cardNumber;
	@Column(insertable = false, updatable = false)
    private String cardType;
	@Column(insertable = false, updatable = false)
    private String currency;
	@Column(insertable = false, updatable = false)
    private String iban;

}
