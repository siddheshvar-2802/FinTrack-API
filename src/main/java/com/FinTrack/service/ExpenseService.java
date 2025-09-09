package com.FinTrack.service;

import com.FinTrack.requests.ExpenseRequest;
import com.FinTrack.response.ExpenseResponse;

import java.util.List;

public interface ExpenseService {
    List<ExpenseResponse> getAllExpenses();

    ExpenseResponse getExpenseById(Long id);

    String createExpense(ExpenseRequest expense);

    String updateExpense(Long id, ExpenseRequest expense);

    boolean deleteExpense(Long id);
}
