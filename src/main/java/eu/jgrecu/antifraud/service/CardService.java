package eu.jgrecu.antifraud.service;

import eu.jgrecu.antifraud.dtos.AddCardRequest;
import eu.jgrecu.antifraud.dtos.AddCardResponse;
import eu.jgrecu.antifraud.dtos.StatusResponse;
import eu.jgrecu.antifraud.exceptions.BadRequestException;
import eu.jgrecu.antifraud.exceptions.HttpConflictException;
import eu.jgrecu.antifraud.model.Card;
import eu.jgrecu.antifraud.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardService {

    private final CardRepository cardRepository;


    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Optional<AddCardResponse> addCard(AddCardRequest addCardRequest) {
        Optional<Card> optionalCard = cardRepository.findByNumber(addCardRequest.getNumber());
        if (optionalCard.isPresent()) {
            throw new HttpConflictException("Stolen card already in the database");
        }

        Card card = new Card();
        card.setNumber(addCardRequest.getNumber());
        Card savedCard = cardRepository.save(card);
        return Optional.of(new AddCardResponse(savedCard));
    }

    public Optional<StatusResponse> deleteStolenCard(String number) {

        boolean validCreditCard = validateCreditCard(number);

        if (!validCreditCard) {
            throw new BadRequestException("Invalid Card format");
        }

        Optional<Card> optionalCard = cardRepository.findByNumber(number);

        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            cardRepository.delete(card);
            return Optional.of(new StatusResponse("Card %s successfully removed!".formatted(card.getNumber())));
        } else {
            return Optional.empty();
        }
    }

    public List<AddCardResponse> getStolenCards() {
        return cardRepository.findAll().stream().map(AddCardResponse::new).collect(Collectors.toList());
    }

    private boolean validateCreditCard(String cardNum) {
        // using Luhn algorithm
        int nDigits = cardNum.length();
        int nSum = 0;
        for (int i = 0; i < nDigits; i++) {
            int d = cardNum.charAt(i) - '0';
            if (i % 2 == 0) {
                d *= 2;
                nSum += d / 10;
                nSum += d % 10;
            } else {
                nSum += d;
            }
        }
        return nSum % 10 == 0;
    }
}
