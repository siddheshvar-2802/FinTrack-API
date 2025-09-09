package com.FinTrack.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest {
    private Double amount;
    private String description;
    private String category;
    private String paymentMode;
    private LocalDate date;
    private Long userId;
}
