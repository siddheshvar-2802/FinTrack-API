package com.FinTrack.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private Double amount;
    private String description;
    private String category;
    private String transactionType;
    private String flow;
    private Date transactionDate;
    private Long userId;

}
