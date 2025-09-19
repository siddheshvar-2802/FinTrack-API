package com.FinTrack.service.serviceImpl;

import com.FinTrack.model.Expenses;
import com.FinTrack.model.Users;
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
            List<Expenses> expenses = expenseRepository.findAll();
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
            Optional<Expenses> expenseOpt = expenseRepository.findById(id);
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
            Expenses expenses = mapToEntity(expenseRequest);
            expenseRepository.save(expenses);
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
            Optional<Expenses> expenseOpt = expenseRepository.findById(id);
            if (expenseOpt.isPresent()) {
                Expenses expenses = expenseOpt.get();
                expenses.setAmount(expenseRequest.getAmount());
                expenses.setDescription(expenseRequest.getDescription());
                expenses.setDate(expenseRequest.getDate());
                expenseRepository.save(expenses);
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

    private ExpenseResponse mapToResponse(Expenses expenses) {
        ExpenseResponse response = new ExpenseResponse();
        response.setId(expenses.getId());
        response.setAmount(expenses.getAmount());
        response.setDescription(expenses.getDescription());
        response.setDate(expenses.getDate());
        response.setCategory(expenses.getCategory());
        response.setPaymentMode(expenses.getPaymentMode());
        response.setUserEmail(expenses.getUsers() != null ? expenses.getUsers().getEmail() : null);
        return response;
    }

    private Expenses mapToEntity(ExpenseRequest request) {
        Expenses expenses = new Expenses();
        expenses.setAmount(request.getAmount());
        expenses.setDescription(request.getDescription());
        expenses.setDate(request.getDate());
        expenses.setCategory(request.getCategory());
        expenses.setPaymentMode(request.getPaymentMode());

        Users users = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + request.getUserId()));
        expenses.setUsers(users);

        return expenses;
    }
}