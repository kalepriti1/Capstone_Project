package com.example.capstone_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    private int id;
    private BigDecimal balance;
    private Long accountNumber;
    private String email;
    private List<TransactionDetails> transactionHistory = new ArrayList<>();

    public Wallet(BigDecimal balance, Long accountNo, String email) {
        this.balance = balance;
        this.accountNumber =accountNo;
        this.email = email;
    }
}