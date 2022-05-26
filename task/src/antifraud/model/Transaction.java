package antifraud.model;

import antifraud.dtos.TransactionRequest;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private Long amount;
    private String ip;
    private String number;
    private Region region;
    private LocalDateTime date;
    private String result;
    private String feedback;

    public Transaction() {
    }

    public Transaction(Long id, Long amount, String ip, String number, Region region, LocalDateTime date) {
        this.transactionId = id;
        this.amount = amount;
        this.ip = ip;
        this.number = number;
        this.region = region;
        this.date = date;
        this.result = "";
        this.feedback = "";
    }

    public Transaction(TransactionRequest transactionRequest) {
        this.amount = transactionRequest.getAmount();
        this.ip = transactionRequest.getIp();
        this.number = transactionRequest.getNumber();
        this.region = Region.valueOf(transactionRequest.getRegion());
        this.date = LocalDateTime.parse(transactionRequest.getDate());
        this.result = "";
        this.feedback = "";
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + transactionId +
                ", amount=" + amount +
                ", ip='" + ip + '\'' +
                ", number='" + number + '\'' +
                ", region=" + region +
                ", date=" + date +
                ", result='" + result + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
