package com.natwest.walletservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private BigDecimal balance;
    private Long accountNumber;
    private String email;
    private BigDecimal withdrawalThreshold = BigDecimal.valueOf(50);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wallet", cascade = CascadeType.ALL)
    @MapKey(name = "date")
    private List<Transaction> transactionHistory = new ArrayList<>();

    public Wallet(Long accountNumber, String email,BigDecimal balance) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.email = email;
    }
}



