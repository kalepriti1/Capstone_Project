package com.natwest.walletservice.service;

import com.natwest.walletservice.entity.Transaction;
import com.natwest.walletservice.entity.Wallet;
import com.natwest.walletservice.exception.BelowWithdrawalThresholdException;
import com.natwest.walletservice.exception.InsufficientWalletBalanceException;
import com.natwest.walletservice.exception.WalletDoesntExistsException;
import com.natwest.walletservice.repository.WalletRepository;
import com.natwest.walletservice.utility.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    WalletRepository walletRepository;
    @Autowired
    SavingAccountClient savingAccountClient;

    @Override
    public int addWallet(Wallet wallet) {
        return walletRepository.save(wallet).getId();
    }

    @Override
    public Wallet getWallet(int walletId) throws WalletDoesntExistsException {
        if (!walletRepository.existsById(walletId))
            throw new WalletDoesntExistsException(AppConstant.WALLET_DOESNT_EXIST_MESSAGE);
        return walletRepository.findById(walletId).get();
    }

    @Override
    public Transaction addBalance(BigDecimal amount, int walletId) throws WalletDoesntExistsException {
        if (!walletRepository.existsById(walletId))
            throw new WalletDoesntExistsException(AppConstant.WALLET_DOESNT_EXIST_MESSAGE);

        Wallet wallet = walletRepository.findById(walletId).get();
        Transaction transaction = new Transaction(amount, LocalDate.now(), "deposit");
        System.out.println("before adding balance: " + wallet.getBalance());
        wallet.setBalance(wallet.getBalance().add(amount));

        transaction.setWallet(wallet);
        System.out.println("After adding balance: " + wallet.getBalance());

        wallet.getTransactionHistory().add(transaction);
        walletRepository.save(wallet);

        return transaction;
    }


    @Override
    public Transaction walletToWalletTransaction(BigDecimal amount, int sourceWalletId, int targetWalletId) throws InsufficientWalletBalanceException,
            BelowWithdrawalThresholdException, WalletDoesntExistsException {

        if (!walletRepository.existsById(sourceWalletId))
            throw new WalletDoesntExistsException("source: "+AppConstant.WALLET_DOESNT_EXIST_MESSAGE);

        else if (!walletRepository.existsById(targetWalletId)) {
            throw new WalletDoesntExistsException("target: "+AppConstant.WALLET_DOESNT_EXIST_MESSAGE);
        }

        Wallet wallet = walletRepository.findById(sourceWalletId).get();
        Transaction transaction = new Transaction(amount, LocalDate.now(), "withdrawal");

        int comparisonResult = wallet.getBalance().compareTo(wallet.getWithdrawalThreshold());

        int comparisonResult2 = wallet.getBalance().compareTo(amount);

        if (comparisonResult < 0){
            throw new BelowWithdrawalThresholdException(AppConstant.BELOW_WITHDRAWAL_THRESHOLD_MESSAGE);
        } else if (comparisonResult2 < 0) {
            throw new InsufficientWalletBalanceException(AppConstant.INSUFFICIENT_WALLET_BALANCE_MESSAGE);
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));

        transaction.setWallet(wallet);
        wallet.getTransactionHistory().add(transaction);
        walletRepository.save(wallet);

        addBalance(amount, targetWalletId);
        return transaction;
    }

    @Override
    public BigDecimal checkBalance(int walletId) throws WalletDoesntExistsException {
        if (!walletRepository.existsById(walletId))
            throw new WalletDoesntExistsException(AppConstant.WALLET_DOESNT_EXIST_MESSAGE);

        return walletRepository.findById(walletId).get().getBalance();
    }

    @Override
    public List<Transaction> showTransactionHistory(int walletId) throws WalletDoesntExistsException {
        if (!walletRepository.existsById(walletId))
            throw new WalletDoesntExistsException(AppConstant.WALLET_DOESNT_EXIST_MESSAGE);

        return walletRepository.findById(walletId).get().getTransactionHistory();
    }

    @Override
    public Transaction walletToBankTransaction(int walletId, String accountNum) throws WalletDoesntExistsException {
        if (!walletRepository.existsById(walletId)){
            throw new WalletDoesntExistsException(AppConstant.WALLET_DOESNT_EXIST_MESSAGE);
        }
        Wallet wallet = walletRepository.findById(walletId).get();
        savingAccountClient.transferMoneyToBank(wallet.getId(),wallet.getBalance());
        Transaction transaction = new Transaction(wallet.getBalance(), LocalDate.now(), "Debit");
        wallet.setBalance(BigDecimal.valueOf(0));
        return transaction;
    }


}

