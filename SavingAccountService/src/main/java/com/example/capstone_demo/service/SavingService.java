package com.example.capstone_demo.service;

import com.example.capstone_demo.entity.SavingAccount;
import com.example.capstone_demo.entity.UserDto;
import com.example.capstone_demo.entity.TransactionDetails;
import com.example.capstone_demo.exception.InsufficientBalanceException;
import com.example.capstone_demo.exception.InvalidBalanceException;

import java.math.BigDecimal;
import java.util.List;

public interface SavingService {
    String checkAccountBalance(Long accountNo);
    String  deposit(Long accountNo, BigDecimal amount) throws InvalidBalanceException;
    String transfer(Long payerAccountNo, Long payeeAccountNo, BigDecimal amount) throws InsufficientBalanceException;

//    String getWalletBalance(Long accountNo);
    String  createSavingAccount(UserDto userDto);

    Long findAccountNumberByEmail(String email);

    List<TransactionDetails> showTransactions(Long accountNumber);

    SavingAccount findAccountNumberByWalletId(int walletId);
}
