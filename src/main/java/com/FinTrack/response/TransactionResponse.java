package com.FinTrack.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private Double amount;
    private String description;
    private String category;
    private String paymentMode;
    private LocalDate date;
    private String userEmail;

}
