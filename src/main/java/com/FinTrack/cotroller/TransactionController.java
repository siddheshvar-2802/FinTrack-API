package com.FinTrack.cotroller;

import com.FinTrack.requests.TransactionRequest;
import com.FinTrack.response.TransactionResponse;
import com.FinTrack.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        try {
            logger.info("Creating Transaction: {}", transactionRequest);
            String created = transactionService.createTransaction(transactionRequest);
            return ResponseEntity.status(201).body(created);
        } catch (Exception e) {
            logger.error("Error creating Transaction", e);
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable Long id, @RequestBody TransactionRequest transactionRequest) {
        try {
            logger.info("Updating transaction with id: {}", id);
            String updated = transactionService.updateTransaction(id, transactionRequest);
            if (updated == null) {
                logger.warn("Transaction not found for update: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logger.error("Error updating transaction", e);
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        try {
            logger.info("Deleting transaction with id: {}", id);
            boolean deleted = transactionService.deleteExpense(id);
            if (!deleted) {
                logger.warn("Expense not found for delete: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting transaction", e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/allTransactions/{userId}")
    public ResponseEntity<List<?>> getAllTransactions(@PathVariable Long userId) {
        try {
            logger.info("Fetching all transactions");
            List<TransactionResponse> transactions = transactionService.getAllTransactionsByUserId(userId);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Error fetching all transactions", e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long transactionId) {
        try {
            logger.info("Fetching transaction with transactionId: {}", transactionId);
            TransactionResponse expense = transactionService.getTransactionById(transactionId);
            if (expense == null) {
                logger.warn("Transaction not found: {}", transactionId);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(expense);
        } catch (Exception e) {
            logger.error("Error fetching transaction by transactionId", e);
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
}
