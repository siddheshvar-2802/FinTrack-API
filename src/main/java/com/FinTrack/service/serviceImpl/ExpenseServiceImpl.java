package com.FinTrack.service.serviceImpl;

import com.FinTrack.model.Expense;
import com.FinTrack.model.User;
import com.FinTrack.repository.ExpenseRepository;
import com.FinTrack.repository.UserRepository;
import com.FinTrack.requests.ExpenseRequest;
import com.FinTrack.response.ExpenseResponse;
import com.FinTrack.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<ExpenseResponse> getAllExpenses() {
        try {
            logger.info("Fetching all expenses");
            List<Expense> expenses = expenseRepository.findAll();
            return expenses.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching all expenses", e);
            throw new RuntimeException("Failed to fetch expenses", e);
        }
    }

    @Override
    public ExpenseResponse getExpenseById(Long id) {
        try {
            logger.info("Fetching expense by id: {}", id);
            Optional<Expense> expenseOpt = expenseRepository.findById(id);
            return expenseOpt.map(this::mapToResponse).orElse(null);
        } catch (Exception e) {
            logger.error("Error fetching expense by id: {}", id, e);
            throw new RuntimeException("Failed to fetch expense", e);
        }
    }

    @Override
    public String createExpense(ExpenseRequest expenseRequest) {
        try {
            logger.info("Creating expense: {}", expenseRequest);
            Expense expense = mapToEntity(expenseRequest);
            expenseRepository.save(expense);
            return "Expense created";
        } catch (Exception e) {
            logger.error("Error creating expense cause:{}", e.getMessage());
            throw new RuntimeException("Failed to create expense cause: " + e.getMessage());
        }
    }

    @Override
    public String updateExpense(Long id, ExpenseRequest expenseRequest) {
        try {
            logger.info("Updating expense with id: {}", id);
            Optional<Expense> expenseOpt = expenseRepository.findById(id);
            if (expenseOpt.isPresent()) {
                Expense expense = expenseOpt.get();
                // Update fields from request
                expense.setAmount(expenseRequest.getAmount());
                expense.setDescription(expenseRequest.getDescription());
                expense.setDate(expenseRequest.getDate());
                // Add other fields as needed
                expenseRepository.save(expense);
                return "Expense updated";
            } else {
                return "Expense not found";
            }
        } catch (Exception e) {
            logger.error("Error updating expense with id: {}", id, e);
            throw new RuntimeException("Failed to update expense", e);
        }
    }

    @Override
    public boolean deleteExpense(Long id) {
        try {
            logger.info("Deleting expense with id: {}", id);
            if (expenseRepository.existsById(id)) {
                expenseRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("Error deleting expense with id: {}", id, e);
            throw new RuntimeException("Failed to delete expense", e);
        }
    }

    private ExpenseResponse mapToResponse(Expense expense) {
        ExpenseResponse response = new ExpenseResponse();
        response.setId(expense.getId());
        response.setAmount(expense.getAmount());
        response.setDescription(expense.getDescription());
        response.setDate(expense.getDate());
        response.setCategory(expense.getCategory());
        response.setPaymentMode(expense.getPaymentMode());
        response.setUserEmail(expense.getUser() != null ? expense.getUser().getEmail() : null);
        return response;
    }

    private Expense mapToEntity(ExpenseRequest request) {
        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setDate(request.getDate());
        expense.setCategory(request.getCategory());
        expense.setPaymentMode(request.getPaymentMode());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + request.getUserId()));
        expense.setUser(user);

        return expense;
    }
}