package com.natwest.walletservice.service;

import com.natwest.walletservice.entity.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.math.BigDecimal;

@FeignClient(name="SAVINGACCOUNTSERVICE")
public interface SavingAccountClient {
    @PutMapping("/savings/WalletBalance/{walletId}/{amount}")
    public ResponseEntity<Transaction> transferMoneyToBank(@PathVariable int walletId, BigDecimal amount);
}
