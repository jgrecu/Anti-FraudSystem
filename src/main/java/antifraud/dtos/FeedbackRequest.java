package antifraud.dtos;

import antifraud.model.TransactionStatus;

public class FeedbackRequest {
    private Long transactionId;
    private TransactionStatus feedback;

    public FeedbackRequest() {
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionStatus getFeedback() {
        return feedback;
    }

    public void setFeedback(TransactionStatus feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "FeedbackRequest{" +
                "transactionId=" + transactionId +
                ", feedback=" + feedback +
                '}';
    }
}
