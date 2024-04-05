// TransactionDetails.java

package com.example.capstone_demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private LocalDate transactionDate;

    private BigDecimal amount;

    private String transactionType;

    @ManyToOne
    @JoinColumn(name = "saving_email", referencedColumnName = "email")
    @JsonIgnore // Ignoring for JSON serialization to avoid infinite recursion
    private SavingAccount savingAccount;

    public TransactionDetails(BigDecimal amount, String transactionType,SavingAccount savingAccount) {
        this.transactionDate = LocalDate.now();
        this.amount = amount;
        this.transactionType = transactionType;
        this.savingAccount = savingAccount;
    }

    @Override
    public String toString() {
        return "TransactionDetails{" +
                "transactionId=" + transactionId +
                ", transactionDate=" + transactionDate +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
