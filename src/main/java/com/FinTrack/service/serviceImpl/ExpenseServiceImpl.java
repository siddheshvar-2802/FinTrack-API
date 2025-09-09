package com.FinTrack.service.serviceImpl;

import com.FinTrack.requests.ExpenseRequest;
import com.FinTrack.response.ExpenseResponse;
import com.FinTrack.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    @Override
    public List<ExpenseResponse> getAllExpenses() {
        try {
            logger.info("Fetching all expenses");
            // Replace with actual fetch logic
            return new ArrayList<>();
        } catch (Exception e) {
            logger.error("Error fetching all expenses", e);
            throw new RuntimeException("Failed to fetch expenses", e);
        }
    }

    @Override
    public ExpenseResponse getExpenseById(Long id) {
        try {
            logger.info("Fetching expense by id: {}", id);
            // Replace with actual fetch logic
            return null;
        } catch (Exception e) {
            logger.error("Error fetching expense by id: {}", id, e);
            throw new RuntimeException("Failed to fetch expense", e);
        }
    }

    @Override
    public String createExpense(ExpenseRequest expense) {
        try {
            logger.info("Creating expense: {}", expense);
            // Replace with actual create logic
            return "Expense created";
        } catch (Exception e) {
            logger.error("Error creating expense", e);
            throw new RuntimeException("Failed to create expense", e);
        }
    }

    @Override
    public String updateExpense(Long id, ExpenseRequest expense) {
        try {
            logger.info("Updating expense with id: {}", id);
            // Replace with actual update logic
            return "Expense updated";
        } catch (Exception e) {
            logger.error("Error updating expense with id: {}", id, e);
            throw new RuntimeException("Failed to update expense", e);
        }
    }

    @Override
    public boolean deleteExpense(Long id) {
        try {
            logger.info("Deleting expense with id: {}", id);
            // Replace with actual delete logic
            return true;
        } catch (Exception e) {
            logger.error("Error deleting expense with id: {}", id, e);
            throw new RuntimeException("Failed to delete expense", e);
        }
    }
}