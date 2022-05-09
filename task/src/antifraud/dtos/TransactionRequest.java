package antifraud.dtos;

import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class TransactionRequest {
    @Min(value = 1)
    private Long amount;
    @Pattern(regexp = "^((\\d|[1-9]\\d|1\\d{2}|2[0-5]{2})\\.){3}(\\d|[1-9]\\d|1\\d{2}|2[0-5]{2})$")
    private String ip;
    @CreditCardNumber
    private String number;


    public TransactionRequest() {
    }

    public TransactionRequest(Long amount, String ip, String number) {
        this.amount = amount;
        this.ip = ip;
        this.number = number;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "amount=" + amount +
                ", ip='" + ip + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
