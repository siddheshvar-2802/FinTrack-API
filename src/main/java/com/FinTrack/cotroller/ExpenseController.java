package com.FinTrack.cotroller;

import com.FinTrack.requests.ExpenseRequest;
import com.FinTrack.response.ExpenseResponse;
import com.FinTrack.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/create")
    public ResponseEntity<?> createExpense(@RequestBody ExpenseRequest expenseDto) {
        try {
            logger.info("Creating expense: {}", expenseDto);
            String created = expenseService.createExpense(expenseDto);
            return ResponseEntity.status(201).body(created);
        } catch (Exception e) {
            logger.error("Error creating expense", e);
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Long id, @RequestBody ExpenseRequest expenseRequest) {
        try {
            logger.info("Updating expense with id: {}", id);
            String updated = expenseService.updateExpense(id, expenseRequest);
            if (updated == null) {
                logger.warn("Expense not found for update: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logger.error("Error updating expense", e);
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        try {
            logger.info("Deleting expense with id: {}", id);
            boolean deleted = expenseService.deleteExpense(id);
            if (!deleted) {
                logger.warn("Expense not found for delete: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting expense", e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<?>> getAllExpenses() {
        try {
            logger.info("Fetching all expenses");
            List<ExpenseResponse> expenses = expenseService.getAllExpenses();
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            logger.error("Error fetching all expenses", e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id) {
        try {
            logger.info("Fetching expense with id: {}", id);
            ExpenseResponse expense = expenseService.getExpenseById(id);
            if (expense == null) {
                logger.warn("Expense not found: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(expense);
        } catch (Exception e) {
            logger.error("Error fetching expense by id", e);
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
}
