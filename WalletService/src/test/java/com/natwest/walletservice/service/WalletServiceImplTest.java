package com.natwest.walletservice.service;

import com.natwest.walletservice.entity.Transaction;
import com.natwest.walletservice.entity.Wallet;
import com.natwest.walletservice.exception.BelowWithdrawalThresholdException;
import com.natwest.walletservice.exception.InsufficientWalletBalanceException;
import com.natwest.walletservice.exception.WalletDoesntExistsException;
import com.natwest.walletservice.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WalletServiceImplTest {
//
//    @Mock
//    WalletRepository walletRepository;
//    @InjectMocks
//    WalletServiceImpl walletService;
//    @Mock
//    Wallet wallet;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void addWallet() {
//        when(walletRepository.save(wallet)).thenReturn(wallet);
//        assertEquals(wallet, walletService.addWallet(wallet));
//    }
//
//    @Test
//    void getWallet() throws WalletDoesntExistsException {
//        when(walletRepository.existsById(1)).thenReturn(true);
//        when(walletRepository.findById(1)).thenReturn(Optional.ofNullable(wallet));
//
//        assertEquals(wallet, walletService.getWallet(1));
//    }
//
//    @Test
//    void getWalletThrowsWalletDoesntExistsException() {
//        when(walletRepository.existsById(1)).thenReturn(false);
//        when(walletRepository.findById(1)).thenReturn(Optional.ofNullable(wallet));
//
//        assertThrows(WalletDoesntExistsException.class, () -> walletService.getWallet(1));
//    }
//
//    @Test
//    public void addBalance() throws WalletDoesntExistsException {
//
//    MockitoAnnotations.openMocks(this);
//    int walletId = 1;
//    BigDecimal initialBalance = BigDecimal.valueOf(30.0);
//    BigDecimal amountToAdd = BigDecimal.valueOf(10.00);
//
//    Wallet wallet = new Wallet();
//        wallet.setId(walletId);
//        wallet.setBalance(initialBalance);
//
//    when(walletRepository.existsById(walletId)).thenReturn(true);
//    when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
//
//    Transaction transaction = walletService.addBalance(amountToAdd, walletId);
//
//    verify(walletRepository).existsById(walletId);
//    verify(walletRepository).findById(walletId);
//    verify(walletRepository).save(wallet);
//
//    // Verify the transaction details
//    assertNotNull(transaction);
//    assertEquals(amountToAdd, transaction.getAmount());
//    assertEquals(LocalDate.now(), transaction.getDate());
//    assertEquals("deposit", transaction.getTransactionType());
//
//    // Verify the updated balance
//    assertEquals(initialBalance.add(amountToAdd), wallet.getBalance());
//    }
//
//
//
//    @Test
//    void checkBalance() throws WalletDoesntExistsException {
//        int walletId = 1;
//        BigDecimal balance = new BigDecimal("100.00");
//        when(walletRepository.existsById(walletId)).thenReturn(true);
//        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
//        when(wallet.getBalance()).thenReturn(balance);
//
//        assertEquals(balance, walletService.checkBalance(walletId));
//
//    }
//
//    @Test
//    void showTransactionHistory() throws WalletDoesntExistsException {
//        int walletId = 1;
//        List<Transaction> transactionHistory = new ArrayList<>();
//        transactionHistory.add(new Transaction(new BigDecimal("50.00"), null, "deposit"));
//        when(walletRepository.existsById(walletId)).thenReturn(true);
//        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
//        when(wallet.getTransactionHistory()).thenReturn(transactionHistory);
//
//        assertEquals(transactionHistory, walletService.showTransactionHistory(walletId));
//    }

//    @Test
//    void testWalletToWalletTransaction() throws WalletDoesntExistsException,
//             InsufficientWalletBalanceException, BelowWithdrawalThresholdException {
//
//        int sourceWalletId = 1;
//        int targetWalletId = 2;
//        BigDecimal amount = BigDecimal.valueOf(10.00);
//        BigDecimal initialBalance = BigDecimal.valueOf(30.0);
//
//        Wallet sourceWallet = new Wallet();
//        sourceWallet.setId(sourceWalletId);
//        sourceWallet.setBalance(initialBalance);
//        sourceWallet.setWithdrawalThreshold(BigDecimal.TEN);
//
//        Wallet targetWallet = new Wallet();
//        targetWallet.setId(targetWalletId);
//
//        when(sourceWallet.getBalance()).thenReturn(BigDecimal.valueOf(30.00));
//        when(walletRepository.existsById(sourceWalletId)).thenReturn(true);
//        when(walletRepository.existsById(targetWalletId)).thenReturn(true);
//        when(walletRepository.findById(sourceWalletId)).thenReturn(Optional.of(sourceWallet));
//        when(walletRepository.findById(targetWalletId)).thenReturn(Optional.of(targetWallet));
//
//        Transaction transaction = walletService.walletToWalletTransaction(BigDecimal.valueOf(10), sourceWalletId, targetWalletId);
//
//        assertThrows(InsufficientWalletBalanceException.class, () -> walletService.walletToWalletTransaction(BigDecimal.valueOf(150), 1, 2));
//        verify(walletRepository, times(2)).existsById(anyInt());
//        verify(walletRepository, times(2)).findById(anyInt());
//        verify(walletRepository).save(any(Wallet.class));
//
//        assertNotNull(transaction);
//        assertEquals(amount, transaction.getAmount());
//        assertEquals(LocalDate.now(), transaction.getDate());
//        assertEquals("withdrawal", transaction.getTransactionType());
//
//        // Verify the updated balance of source wallet
//        assertEquals(initialBalance.subtract(amount), sourceWallet.getBalance());
//
//        // Verify that addBalance was called for target wallet
//        verify(walletService).addBalance(amount, targetWalletId);
//    }

}
