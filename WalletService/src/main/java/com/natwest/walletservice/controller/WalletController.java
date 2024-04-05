package com.natwest.walletservice.controller;

import com.natwest.walletservice.entity.Transaction;
import com.natwest.walletservice.entity.Wallet;
import com.natwest.walletservice.exception.BelowWithdrawalThresholdException;
import com.natwest.walletservice.exception.InsufficientWalletBalanceException;
import com.natwest.walletservice.exception.WalletDoesntExistsException;
import com.natwest.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class WalletController {
    @Autowired
    WalletService walletService;

    @PostMapping("/create-wallet")
    public ResponseEntity<Integer> createWallet(@RequestBody Wallet wallet){
        int walletId = walletService.addWallet(wallet);
        return new ResponseEntity<>(walletId, HttpStatus.OK);
    }

    @PutMapping("/wallet/{walletId}/{amount}")
    public ResponseEntity<Void> addBalanceToWallet(@PathVariable int walletId, @PathVariable BigDecimal amount)
            throws WalletDoesntExistsException {
        walletService.addBalance(amount, walletId);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/wallet/{sourceWalletId}/{targetWalletId}/{amount}")
    public ResponseEntity<Transaction> withdrawMoneyFromWallet(@PathVariable int sourceWalletId, @PathVariable int targetWalletId, @PathVariable BigDecimal amount) throws InsufficientWalletBalanceException, BelowWithdrawalThresholdException, WalletDoesntExistsException {
        return new ResponseEntity<>(walletService.walletToWalletTransaction(amount, sourceWalletId, targetWalletId), HttpStatus.OK);
    }

    @PutMapping("/wallet/{walletId}/{accountNum}")
    public ResponseEntity<Transaction> transferToAccount(@PathVariable int walletId, @PathVariable String accountNum) throws WalletDoesntExistsException {
        return new ResponseEntity<>(walletService.walletToBankTransaction(walletId, accountNum), HttpStatus.OK);
    }

    @GetMapping("/wallet/{id}")
    public ResponseEntity<Wallet> getWallet(@PathVariable int id) throws WalletDoesntExistsException {
        return new ResponseEntity<>(walletService.getWallet(id),HttpStatus.OK);
    }

    @GetMapping("/wallet/{walletId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable int walletId)throws WalletDoesntExistsException {
        return new ResponseEntity<>(walletService.showTransactionHistory(walletId),HttpStatus.OK);
    }

}




