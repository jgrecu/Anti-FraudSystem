package antifraud.service;

import antifraud.dtos.TransactionRequest;
import antifraud.dtos.TransactionResponse;
import antifraud.model.Card;
import antifraud.model.IpAddress;
import antifraud.model.TransactionStatus;
import antifraud.repository.CardRepository;
import antifraud.repository.IpAddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private final IpAddressRepository ipAddressRepository;
    private final CardRepository cardRepository;

    public TransactionService(IpAddressRepository ipAddressRepository, CardRepository cardRepository) {
        this.ipAddressRepository = ipAddressRepository;
        this.cardRepository = cardRepository;
    }

    public TransactionResponse processTransaction(TransactionRequest transactionRequest) {

        Long amount = transactionRequest.getAmount();
        String ip = transactionRequest.getIp();
        String number = transactionRequest.getNumber();

        int validateAmount = validateAmount(amount);
        boolean validateIP = validateIP(ip);
        boolean validateCard = validateCard(number);

        if (validateAmount == 1 && validateIP && validateCard) {
            return new TransactionResponse(TransactionStatus.ALLOWED, "none");
        } else if (validateAmount == 2 && validateIP && validateCard) {
            return new TransactionResponse(TransactionStatus.MANUAL_PROCESSING, "amount");
        } else if (validateAmount == 3 && validateIP && validateCard) {
            return new TransactionResponse(TransactionStatus.PROHIBITED, "amount");
        } else if (validateAmount == 3 && validateCard) {
            return new TransactionResponse(TransactionStatus.PROHIBITED, "amount, ip");
        } else if (validateAmount == 3 && validateIP) {
            return new TransactionResponse(TransactionStatus.PROHIBITED, "amount, card-number");
        } else if (validateAmount == 3) {
            return new TransactionResponse(TransactionStatus.PROHIBITED, "amount, card-number, ip");
        } else if (validateCard) {
            return new TransactionResponse(TransactionStatus.PROHIBITED, "ip");
        } else if (validateIP) {
            return new TransactionResponse(TransactionStatus.PROHIBITED, "card-number");
        } else {
            return new TransactionResponse(TransactionStatus.PROHIBITED, "card-number, ip");
        }

    }

    private int validateAmount(Long amount) {
        if (amount <= 200) {
            return 1;
        } else if (amount <= 1500) {
            return 2;
        } else {
            return 3;
        }
    }

    private boolean validateIP(String ip) {
        Optional<IpAddress> optionalIpAddress = ipAddressRepository.findByIp(ip);
        return optionalIpAddress.isEmpty();
    }

    private boolean validateCard(String number) {
        Optional<Card> optionalCard = cardRepository.findByNumber(number);
        return optionalCard.isEmpty();
    }

}
