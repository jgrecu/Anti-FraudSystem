package antifraud.service;

import antifraud.dtos.TransactionRequest;
import antifraud.dtos.TransactionResponse;
import antifraud.model.*;
import antifraud.repository.CardRepository;
import antifraud.repository.IpAddressRepository;
import antifraud.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final IpAddressRepository ipAddressRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(IpAddressRepository ipAddressRepository,
                              CardRepository cardRepository,
                              TransactionRepository transactionRepository) {
        this.ipAddressRepository = ipAddressRepository;
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
    }

    public TransactionResponse processTransaction(TransactionRequest transactionRequest) {

        Long amount = transactionRequest.getAmount();
        String ip = transactionRequest.getIp();
        String number = transactionRequest.getNumber();
        Region region = Region.valueOf(transactionRequest.getRegion());
        LocalDateTime dateTime = LocalDateTime.parse(transactionRequest.getDate());

        List<String> prohibitedReasons = new ArrayList<>();
        List<String> manualProcessingReasons = new ArrayList<>();

        int validateAmount = validateAmount(amount);
        boolean validateIP = validateIP(ip);
        boolean validateCard = validateCard(number);
        long numberOfTransactionsForCardPerRegionInLastHour =
                getNumberOfTransactionRegionLastHour(number, region, dateTime);
        long numberOfTransactionsForCardPerIpInLastHour =
                getNumberOfTransactionIpLastHour(number, ip, dateTime);

        Transaction newTransaction = new Transaction(transactionRequest);
        transactionRepository.save(newTransaction);

        if (validateAmount == 2) {
            manualProcessingReasons.add("amount");
        }

        if (validateAmount == 3) {
            prohibitedReasons.add("amount");
        }

        if (!validateCard) {
            prohibitedReasons.add("card-number");
        }

        if (!validateIP) {
            prohibitedReasons.add("ip");
        }

        if (numberOfTransactionsForCardPerIpInLastHour == 2) {
            manualProcessingReasons.add("ip-correlation");
        }

        if (numberOfTransactionsForCardPerIpInLastHour > 2) {
            prohibitedReasons.add("ip-correlation");
        }

        if (numberOfTransactionsForCardPerRegionInLastHour == 2) {
            manualProcessingReasons.add("region-correlation");
        }

        if (numberOfTransactionsForCardPerRegionInLastHour > 2) {
            prohibitedReasons.add("region-correlation");
        }

        String infoManual = String.join(", ", manualProcessingReasons);
        String infoProhibited = String.join(", ", prohibitedReasons);

        if (validateAmount == 1 && prohibitedReasons.isEmpty() && manualProcessingReasons.isEmpty()) {
            return new TransactionResponse(TransactionStatus.ALLOWED, "none");
        } else if ( validateIP && validateCard && (validateAmount == 2 ||
                numberOfTransactionsForCardPerRegionInLastHour == 2 || numberOfTransactionsForCardPerIpInLastHour == 2)) {
            return new TransactionResponse(TransactionStatus.MANUAL_PROCESSING, infoManual);
        } else {
            return new TransactionResponse(TransactionStatus.PROHIBITED, infoProhibited);
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

    private long getNumberOfTransactionRegionLastHour(String cardNumber, Region region, LocalDateTime time) {
        List<Transaction> transactionList =
                transactionRepository.findByNumberAndDateBetween(cardNumber, time.minusHours(1), time);
//        System.out.println(transactionList);
//        System.out.println(transactionList.stream()
//                .map(Transaction::getRegion)
//                .filter(transactionRegion -> !transactionRegion.equals(region))
//                .distinct()
//                .collect(Collectors.toList()));
        return transactionList.stream()
                .map(Transaction::getRegion)
                .filter(transactionRegion -> !transactionRegion.equals(region))
                .distinct()
                .count();
    }

    private long getNumberOfTransactionIpLastHour(String cardNumber, String ip, LocalDateTime time) {
        List<Transaction> transactionList =
                transactionRepository.findByNumberAndDateBetween(cardNumber, time.minusHours(1), time);
//        System.out.println(transactionList);
//        System.out.println(transactionList.stream()
//                .map(Transaction::getIp)
//                .filter(transactionIp -> !transactionIp.equals(ip))
//                .distinct()
//                .collect(Collectors.toList()));
        return transactionList.stream()
                .map(Transaction::getIp)
                .filter(transactionIp -> !transactionIp.equals(ip))
                .distinct()
                .count();
    }
}
