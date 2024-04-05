package com.natwest.walletservice.service;

import com.natwest.walletservice.entity.Transaction;
import com.natwest.walletservice.entity.Wallet;
import com.natwest.walletservice.exception.BelowWithdrawalThresholdException;
import com.natwest.walletservice.exception.InsufficientWalletBalanceException;
import com.natwest.walletservice.exception.WalletDoesntExistsException;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    int addWallet(Wallet wallet);

    Wallet getWallet(int walletId) throws WalletDoesntExistsException;
    Transaction addBalance(BigDecimal amount, int walletId) throws WalletDoesntExistsException;

    Transaction walletToWalletTransaction(BigDecimal amount, int sourceWalletId, int targetWalletId) throws InsufficientWalletBalanceException,
            BelowWithdrawalThresholdException, WalletDoesntExistsException;

    BigDecimal checkBalance(int walletId) throws WalletDoesntExistsException;

    List<Transaction> showTransactionHistory(int walletId) throws WalletDoesntExistsException;

    Transaction walletToBankTransaction(int walledId, String accountNum) throws WalletDoesntExistsException;
}
