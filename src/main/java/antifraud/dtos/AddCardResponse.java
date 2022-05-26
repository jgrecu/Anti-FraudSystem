package antifraud.dtos;

import antifraud.model.Card;

public class AddCardResponse {

    private final Long id;
    private final String number;

    public AddCardResponse(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public AddCardResponse(Card card) {
        this.id = card.getId();
        this.number = card.getNumber();
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "AddCardResponse{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
