package antifraud.dtos;

import org.hibernate.validator.constraints.CreditCardNumber;

public class AddCardRequest {
    @CreditCardNumber
    private String number;

    public AddCardRequest() {
    }

    public AddCardRequest(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "AddCardRequest{" +
                "number='" + number + '\'' +
                '}';
    }
}
