package tr.edu.metu.ii.AnyChange.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInformationDTO {
    private long id;
    private String creditCardNumber;
    private String securityCode;
    private String expirationMonth;
    private String expirationYear;
}
