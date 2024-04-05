package com.example.capstone_demo.controller;

import com.example.capstone_demo.entity.UserDto;
import com.example.capstone_demo.entity.TransactionDetails;
import com.example.capstone_demo.exception.InsufficientBalanceException;
import com.example.capstone_demo.exception.InvalidBalanceException;
import com.example.capstone_demo.service.SavingService;
import com.example.capstone_demo.service.WalletServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/savings")
public class SavingController {

    @Autowired
    private SavingService savingService;

    @Autowired
    WalletServiceClient walletServiceClient;
    @GetMapping("/balance/{accountNo}")
    public ResponseEntity<String> checkAccountBalance(@PathVariable Long accountNo) {
        return new ResponseEntity<>(savingService.checkAccountBalance(accountNo),HttpStatus.OK);
    }

    @PutMapping("/deposit/{accountNo}/{amount}")
    public ResponseEntity<String> depositAmount(@PathVariable Long accountNo, @PathVariable BigDecimal amount) throws InvalidBalanceException {
        return new ResponseEntity<>((savingService.deposit(accountNo,amount)), HttpStatus.OK);
    }

    @PutMapping("/withdrow/{accountNo}/{amount}")
    public ResponseEntity<String> withdrowAmount(@PathVariable Long accountNo, @PathVariable BigDecimal amount) throws InvalidBalanceException {
        return new ResponseEntity<>((savingService.deposit(accountNo,amount)), HttpStatus.OK);
    }
//    @PutMapping("/WalletBalance/{walletId}")
//    public ResponseEntity<TransactionDetails> transferMoneyToBank(@PathVariable int walletId){
//        return new ResponseEntity<>(savingService.findAccountNumberByWalletId(walletId),HttpStatus.OK);
//    }

    @PutMapping("/transfer/{payerAccountNo}/{payeeAccountNo}/{amount}")
    public ResponseEntity<String> tranferMoney(@PathVariable Long payerAccountNo,@PathVariable Long payeeAccountNo ,@PathVariable BigDecimal amount) throws InsufficientBalanceException {
        return new ResponseEntity<>((savingService.transfer(payeeAccountNo,payerAccountNo,amount)), HttpStatus.OK);
    }

    @PostMapping("/new-Account")
    public ResponseEntity<String> createAccount(@RequestBody UserDto userDto){
         String  message = savingService.createSavingAccount(userDto);
         return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @GetMapping("/accountNumber/{email}")
    public ResponseEntity<String> getAccountNumber(@PathVariable String email){
        Long accountNumber = savingService.findAccountNumberByEmail(email);
        return ResponseEntity.ok("AccountNumber: " + accountNumber);
    }
    @GetMapping("/transactions/{accountNumber}")
    public ResponseEntity<List<TransactionDetails>> showTransactions(@PathVariable Long accountNumber){
        List<TransactionDetails> transactions = savingService.showTransactions(accountNumber);
        return new ResponseEntity<>(transactions,HttpStatus.OK);
    }
}