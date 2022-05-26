package antifraud.dtos;

import antifraud.model.TransactionStatus;

public class TransactionResponse {
    private final TransactionStatus result;
    private final String info;

    public TransactionResponse(TransactionStatus result, String info) {
        this.result = result;
        this.info = info;
    }

    public TransactionStatus getResult() {
        return result;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "result=" + result +
                ", info='" + info + '\'' +
                '}';
    }
}
