package com.FinTrack.service;

import com.FinTrack.requests.TransactionRequest;
import com.FinTrack.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    List<TransactionResponse> getAllTransactionsByUserId(Long userId);

    TransactionResponse getTransactionById(Long id);

    String createTransaction(TransactionRequest expense);

    String updateTransaction(Long id, TransactionRequest expense);

    boolean deleteExpense(Long id);
}
