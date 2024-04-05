package com.example.capstone_demo.service;

import com.example.capstone_demo.entity.*;
import com.example.capstone_demo.exception.*;
import com.example.capstone_demo.repository.SavingRepository;
import com.example.capstone_demo.util.AppConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class SavingServiceImplTest {

    @Mock
    private SavingRepository savingRepository;

    @Mock
    private WalletServiceClient walletServiceClient;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private SavingServiceImpl savingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeposit() throws InvalidBalanceException {
        SavingAccount savingAccount = new SavingAccount("test@example.com", 123456789L, 123456789L, BigDecimal.ZERO);
        BigDecimal amount = new BigDecimal("100.00");
        TransactionDetails transactionDetails = new TransactionDetails(amount, "Deposit", savingAccount);

        when(savingRepository.findAccountByAccountNo(savingAccount.getAccountNo())).thenReturn(savingAccount);
        when(transactionRepository.save(any(TransactionDetails.class))).thenReturn(transactionDetails);

        String result = savingService.deposit(savingAccount.getAccountNo(), amount);

        assertEquals(AppConstant.TRANSACTION_DONE_MESSAGE + "\n" + transactionDetails.toString(), result);
        assertEquals(amount, savingAccount.getBalance());
        verify(savingRepository, times(1)).save(savingAccount);
    }


    @Test
    void testTransfer() throws InsufficientBalanceException {
        SavingAccount payerAccount = new SavingAccount("payer@example.com", 123456789L, 123456789L, new BigDecimal("500.00"));
        SavingAccount payeeAccount = new SavingAccount("payee@example.com", 987654321L, 987654321L, BigDecimal.ZERO);
        payeeAccount.setTransactionDetails(new ArrayList<>());

        BigDecimal amount = new BigDecimal("100.00");
        BigDecimal walletAmount = new BigDecimal("50.00");
        Wallet wallet  = new Wallet(1,BigDecimal.valueOf(100),123456789L,"payer@example.com",new ArrayList<>());

        when(savingRepository.findAccountByAccountNo(payerAccount.getAccountNo())).thenReturn(payerAccount);
        when(savingRepository.findAccountByAccountNo(payeeAccount.getAccountNo())).thenReturn(payeeAccount);
        when(walletServiceClient.addWalletBalance(anyInt(), any(BigDecimal.class)))
                .thenReturn(ResponseEntity.ok().build());
        String result = savingService.transfer(payerAccount.getAccountNo(), payeeAccount.getAccountNo(), amount);

        assertEquals(AppConstant.TRANSACTION_DONE_MESSAGE, result);
        assertEquals(new BigDecimal("400.00"), payerAccount.getBalance());
        assertEquals(new BigDecimal("100.00"), payeeAccount.getBalance());
        verify(savingRepository, times(1)).save(payerAccount);
        verify(savingRepository, times(1)).save(payeeAccount);
    }
    @Test
    void testDeposit_AccountNotFound() {
        Long accountNo = 123456789L;
        BigDecimal amount = new BigDecimal("100.00");

        when(savingRepository.findAccountByAccountNo(accountNo)).thenReturn(null);

        assertThrows(AccountNotFound.class, () -> savingService.deposit(accountNo, amount));
    }

    @Test
    void testDeposit_InvalidBalance() {
        SavingAccount savingAccount = new SavingAccount("test@example.com", 123456789L, 123456789L, BigDecimal.ZERO);
        BigDecimal amount = new BigDecimal("-100.00");

        when(savingRepository.findAccountByAccountNo(savingAccount.getAccountNo())).thenReturn(savingAccount);

        assertThrows(InvalidBalanceException.class, () -> savingService.deposit(savingAccount.getAccountNo(), amount));
    }

    @Test
    void testTransfer_InsufficientBalance() {
        SavingAccount payerAccount = new SavingAccount("payer@example.com", 123456789L, 123456789L, BigDecimal.ZERO);
        SavingAccount payeeAccount = new SavingAccount("payee@example.com", 987654321L, 987654321L, BigDecimal.ZERO);
        BigDecimal amount = new BigDecimal("100.00");

        when(savingRepository.findAccountByAccountNo(payerAccount.getAccountNo())).thenReturn(payerAccount);
        when(savingRepository.findAccountByAccountNo(payeeAccount.getAccountNo())).thenReturn(payeeAccount);

        assertThrows(InsufficientBalanceException.class, () -> savingService.transfer(payerAccount.getAccountNo(), payeeAccount.getAccountNo(), amount));
    }
}
