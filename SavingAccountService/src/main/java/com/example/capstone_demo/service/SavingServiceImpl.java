package com.example.capstone_demo.service;

import com.example.capstone_demo.entity.SavingAccount;
import com.example.capstone_demo.entity.TransactionDetails;
import com.example.capstone_demo.entity.UserDto;
import com.example.capstone_demo.entity.Wallet;
import com.example.capstone_demo.exception.AccountNotFound;
import com.example.capstone_demo.exception.InvalidBalanceException;
import com.example.capstone_demo.util.*;
import com.example.capstone_demo.exception.InsufficientBalanceException;
import com.example.capstone_demo.repository.SavingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
@Service
public class SavingServiceImpl implements SavingService {

    @Autowired
    private SavingRepository savingRepository;
    @Autowired
    private  TransactionRepository transactionRepository;
    @Autowired
    private  WalletServiceClient walletServiceClient;

    @Override
    public String checkAccountBalance(Long accountNo){
        SavingAccount savingAccount = savingRepository.findAccountByAccountNo(accountNo);
        if (savingAccount == null) {
            throw new AccountNotFound(AppConstant.ACCOUNT_NOT_FOUND_WITH_ACOOUNTNUMBER_MESSAGE);
        }
        return "Balance :" + savingAccount.getBalance();
    }
    @Override
    public String deposit(Long accountNo, BigDecimal amount) throws InvalidBalanceException {
        SavingAccount savingAccount = savingRepository.findAccountByAccountNo(accountNo);
        if (savingAccount == null) {
            throw new AccountNotFound(AppConstant.ACCOUNT_NOT_FOUND_WITH_ACOOUNTNUMBER_MESSAGE);
        }

        BigDecimal currentBalance = savingAccount.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);
        savingAccount.setBalance(newBalance);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidBalanceException(AppConstant.INVALID_BALANCE);
        }

        TransactionDetails transactionDetails = new TransactionDetails(amount, "Deposit", savingAccount);
        TransactionDetails creditTransaction = transactionRepository.save(transactionDetails);

        savingAccount.addTransactionDetail(creditTransaction);

        savingRepository.save(savingAccount);

        return AppConstant.TRANSACTION_DONE_MESSAGE + "\n" + transactionDetails.toString();
    }


    public String transfer(Long payerAccountNo, Long payeeAccountNo, BigDecimal amount) throws InsufficientBalanceException {
        SavingAccount payerSavingAccount = savingRepository.findAccountByAccountNo(payerAccountNo);
        SavingAccount payeeSavingAccount = savingRepository.findAccountByAccountNo(payeeAccountNo);

        if (payerSavingAccount != null && payeeSavingAccount != null) {
            if ((amount.compareTo(payerSavingAccount.getBalance())) < 0) {
                BigDecimal transferAmount = payAmount(amount);
                BigDecimal walletAmount = transferAmount.subtract(amount);
                walletServiceClient.addWalletBalance(payerSavingAccount.getWalletId(), walletAmount);
                payerSavingAccount.setBalance(payerSavingAccount.getBalance().subtract(transferAmount));

                TransactionDetails debitTransaction = new TransactionDetails(transferAmount, "Debit from account " + payerAccountNo + " to account " + payeeAccountNo, payeeSavingAccount);
                debitTransaction = transactionRepository.save(debitTransaction);
                payerSavingAccount.addTransactionDetail(debitTransaction);

                payeeSavingAccount.setBalance(payeeSavingAccount.getBalance().add(amount));
                TransactionDetails creditTransaction = new TransactionDetails(amount, "Credit from account " + payerAccountNo + " to account " + payeeAccountNo, payerSavingAccount);
                creditTransaction = transactionRepository.save(creditTransaction);
                payeeSavingAccount.addTransactionDetail(creditTransaction);

                savingRepository.save(payerSavingAccount);
                savingRepository.save(payeeSavingAccount);

                return AppConstant.TRANSACTION_DONE_MESSAGE + "\n" + debitTransaction.toString();
            } else {
                throw new InsufficientBalanceException("Insufficient balance.");
            }
        } else {
            throw new AccountNotFound(AppConstant.ACCOUNT_NOT_FOUND_WITH_ACOOUNTNUMBER_MESSAGE);
        }
    }

    public String createSavingAccount(UserDto registerDto){
        Long accountNumber = Long.parseLong(generateAccountNumber());
        while(!savingRepository.existsByAccountNo(accountNumber)){
            SavingAccount savingAccount = new SavingAccount();
            savingAccount.setAccountNo(accountNumber);
            savingAccount.setEmail(registerDto.getEmail());
            savingAccount.setMobileNumber(registerDto.getMobileNumber());
            Wallet wallet = new Wallet(savingAccount.getBalance(),savingAccount.getAccountNo(),savingAccount.getEmail());
            wallet.setAccountNumber(savingAccount.getAccountNo());
            wallet.setEmail(savingAccount.getEmail());
            int walletId = walletServiceClient.createWalletBalance(wallet).getBody();
            savingAccount.setWalletId(walletId);
            savingRepository.save(savingAccount);
            break;
        }
        return AppConstant.ACCOUNT_ADDED_SUCCESSFULLY_MESSAGE+" :-"+accountNumber;
    }

    @Override
    public Long findAccountNumberByEmail(String email) {
        SavingAccount savingAccount = savingRepository.findByEmail(email);
        return savingAccount.getAccountNo();
    }

    @Override
    public List<TransactionDetails> showTransactions(Long accountNumber) {
        SavingAccount account = savingRepository.findAccountByAccountNo(accountNumber);
        return account.getTransactionDetails();
    }

    @Override
    public SavingAccount findAccountNumberByWalletId(int walletId) {
        return savingRepository.findByWalletId(walletId);
    }

    private String generateAccountNumber() {
        long randomNumber = new Random().nextLong() % 1000000000000L;
        return String.format("%012d", randomNumber < 0 ? -randomNumber : randomNumber);
    }
    private BigDecimal payAmount(BigDecimal amount) {
        BigDecimal one = BigDecimal.ONE;
        BigDecimal remainder = amount.remainder(one);
        if (remainder.compareTo(BigDecimal.ZERO) != 0) {
            return amount.setScale(0, BigDecimal.ROUND_CEILING);
        } else {
            return amount;
        }
    }
}