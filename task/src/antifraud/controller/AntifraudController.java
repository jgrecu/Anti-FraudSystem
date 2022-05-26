package antifraud.controller;

import antifraud.dtos.*;
import antifraud.exceptions.UserNotFoundException;
import antifraud.model.Transaction;
import antifraud.service.CardService;
import antifraud.service.IpAddressService;
import antifraud.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/antifraud")
public class AntifraudController {

    private final IpAddressService ipAddressService;
    private final CardService cardService;
    private final TransactionService transactionService;

    public AntifraudController(IpAddressService ipAddressService, CardService cardService,
                               TransactionService transactionService) {
        this.ipAddressService = ipAddressService;
        this.cardService = cardService;
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> processTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.processTransaction(transactionRequest);
        return ResponseEntity.ok(transactionResponse);
    }

    @PostMapping("/suspicious-ip")
    public ResponseEntity<AddIpResponse> addSuspiciousIP(@RequestBody @Valid AddIPRequest addIPRequest) {
        Optional<AddIpResponse> addIpResponse = ipAddressService.addIpAddress(addIPRequest);

        return addIpResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/suspicious-ip/{ip}")
    public ResponseEntity<StatusResponse> deleteSuspiciousIP(@PathVariable String ip) {
        Optional<StatusResponse> optionalStatusResponse = ipAddressService.deleteSuspiciousIP(ip);
        return optionalStatusResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/suspicious-ip")
    public List<AddIpResponse> getAllIPs() {
        return ipAddressService.getAllSuspiciousIPs();
    }

    @PostMapping("/stolencard")
    public ResponseEntity<AddCardResponse> addStolenCard(@RequestBody @Valid AddCardRequest addCardRequest) {
        Optional<AddCardResponse> addCard = cardService.addCard(addCardRequest);

        return addCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/stolencard/{number}")
    public ResponseEntity<StatusResponse> deleteStolenCard(@PathVariable String number) {
        Optional<StatusResponse> optionalStatusResponse = cardService.deleteStolenCard(number);
        return optionalStatusResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/stolencard")
    public List<AddCardResponse> getAllStolenCards() {
        return cardService.getStolenCards();
    }

    @PutMapping("/transaction")
    public Transaction giveFeedbackToTransaction(@RequestBody FeedbackRequest feedbackRequest) {
        return transactionService.giveFeedbackToTransaction(feedbackRequest)
                .orElseThrow(() -> new UserNotFoundException("no transaction with id - "
                        + feedbackRequest.getTransactionId()));
    }
    @GetMapping("/history")
    public List<Transaction> showAllTransactions() {
        return transactionService.getTransactionHistory();
    }

    @GetMapping("/history/{number}")
    public List<Transaction> showAllTransactionsByCardNumber(@PathVariable String number) {
        return transactionService.getTransactionHistoryByCardNumber(number);
    }
}
