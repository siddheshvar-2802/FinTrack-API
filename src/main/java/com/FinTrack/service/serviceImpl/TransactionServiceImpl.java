package com.FinTrack.service.serviceImpl;

import com.FinTrack.enums.FlowType;
import com.FinTrack.enums.TransactionType;
import com.FinTrack.model.Accounts;
import com.FinTrack.model.Category;
import com.FinTrack.model.Transactions;
import com.FinTrack.model.User;
import com.FinTrack.repository.AccountsRepository;
import com.FinTrack.repository.CategoryRepository;
import com.FinTrack.repository.TransactionsRepository;
import com.FinTrack.repository.UserRepository;
import com.FinTrack.requests.TransactionRequest;
import com.FinTrack.response.TransactionResponse;
import com.FinTrack.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<TransactionResponse> getAllTransactionsByUserId(Long userId) {
        try {
            logger.info("Fetching all transactions by userId {}", userId);
            List<Object[]> expenses = transactionsRepository.findAllTransactionsByUserId(userId);
            return expenses.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching all expenses", e);
            throw new RuntimeException("Failed to fetch expenses", e);
        }
    }

    @Override
    public TransactionResponse getTransactionById(Long id) {
        try {
            logger.info("Fetching transaction by id: {}", id);

            Object[] transaction = transactionsRepository.findTransactionByTransactionId(id)
                    .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));

            return mapToResponse(transaction);

        } catch (Exception e) {
            logger.error("Error fetching transaction by id: {}", id, e);
            throw new RuntimeException("Failed to fetch transaction", e);
        }
    }

    @Override
    public String createTransaction(TransactionRequest transactionRequest) {
        try {

            logger.info("Creating Transaction: {}", transactionRequest);
            Transactions expenses = mapToEntity(transactionRequest);

            transactionsRepository.save(expenses);
            return "Transaction created";

        } catch (Exception e) {
            logger.error("Error creating transaction cause:{}", e.getMessage());
            throw new RuntimeException("Failed to create transaction cause: " + e.getMessage());
        }
    }

    @Override
    public String updateTransaction(Long id, TransactionRequest transactionRequest) {
        try {
            logger.info("Updating expense with id: {}", id);
            Optional<Transactions> transactionOpt = transactionsRepository.findById(id);
            if (transactionOpt.isPresent()) {
                Transactions transaction = transactionOpt.get();
                /*transaction.setAmount(expenseRequest.getAmount());
                transaction.setDescription(expenseRequest.getDescription());
                transaction.setTransactionDate(expenseRequest.getTransactionDate());*/
                transactionsRepository.save(transaction);
                return "Transaction updated";
            } else {
                return "Transaction not found";
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
            if (transactionsRepository.existsById(id)) {
                transactionsRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("Error deleting expense with id: {}", id, e);
            throw new RuntimeException("Failed to delete expense", e);
        }
    }

    private TransactionResponse mapToResponse(Object[] transactions) {
        TransactionResponse response = new TransactionResponse();
        /*response.setId(expenses.getId());
        response.setAmount(expenses.getAmount());
        response.setDescription(expenses.getDescription());
        response.setTransactionDate(expenses.getTransactionDate());
        response.setCategory(expenses.getCategory());
        response.setPaymentMode(expenses.getPaymentMode());
        response.setUserEmail(expenses.getUsers() != null ? expenses.getUsers().getEmail() : null);*/
        return response;
    }

    private Transactions mapToEntity(TransactionRequest request) {
        try {
            Transactions transaction = new Transactions();
            transaction.setAmount(request.getAmount());
            transaction.setDescription(request.getDescription());
            transaction.setTransactionDate(request.getTransactionDate());

            Category category = categoryRepository.findCategoryByCategoryName(request.getCategory())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found: " + request.getCategory()));
            transaction.setCategory(category);

            Accounts accounts = accountsRepository.findAccountByAccountName(request.getAmount())
                    .orElseThrow(() -> new IllegalArgumentException("Account not found: " + request.getAmount()));
            transaction.setAccount(accounts);

            transaction.setTransactionType(TransactionType.valueOf(request.getTransactionType()));
            transaction.setFlow(FlowType.valueOf(request.getFlow()));

            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + request.getUserId()));
            transaction.setUser(user);

            return transaction;
        } catch (Exception e) {
            logger.warn("Error while creating transaction: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}