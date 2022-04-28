package antifraud.controller;

import antifraud.model.Response;
import antifraud.model.Status;
import antifraud.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/antifraud/transaction")
public class TransactionController {

    @PostMapping
    public ResponseEntity<Response> processTransaction(@RequestBody Transaction transaction) {

        Long amount = transaction.getAmount();

        if (amount <= 0) {
            return ResponseEntity.badRequest().build();
        } else if (amount <= 200) {
            return ResponseEntity.ok(new Response(Status.ALLOWED));
        } else if (amount <= 1500) {
            return ResponseEntity.ok(new Response(Status.MANUAL_PROCESSING));
        } else {
            return ResponseEntity.ok(new Response(Status.PROHIBITED));
        }
    }
}
