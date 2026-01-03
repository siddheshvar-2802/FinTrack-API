package com.FinTrack.model;

import com.FinTrack.enums.FlowType;
import com.FinTrack.enums.TransactionType;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "Transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    private Double amount;
    @Enumerated(EnumType.STRING)

    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)

    private FlowType flow;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Accounts account;

    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
