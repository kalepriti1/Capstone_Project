// SavingAccount.java

package com.example.capstone_demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "SavingsAccount")
public class SavingAccount {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "accountNo")
    private Long accountNo;

    @Column(name = "mobile_number")
    private Long mobileNumber;

    @Column(name = "balance")
    private BigDecimal balance = BigDecimal.valueOf(5000);
    @Column(name = "walletId")
    private int walletId;
    @OneToMany(mappedBy = "savingAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionDetails> transactionDetails = new ArrayList<>();

    public SavingAccount(String email, Long accountNo, Long mobileNumber, BigDecimal balance) {
        this.email = email;
        this.accountNo = accountNo;
        this.mobileNumber = mobileNumber;
        this.balance.add(balance);
    }


    public void addTransactionDetail(TransactionDetails transaction) {
        transaction.setSavingAccount(this); // Set the saving account for the transaction
        transactionDetails.add(transaction); // Add transaction to the list
    }
}
