package com.example.capstone_demo.service;

import com.example.capstone_demo.entity.Wallet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "WALLETSERVICE")
public interface WalletServiceClient {

    @PostMapping("/api/v1/create-wallet")
    public ResponseEntity<Integer> createWalletBalance(@RequestBody Wallet wallet);

    @PutMapping("/api/v1/wallet/{id}/{amount}")
    ResponseEntity<Void> addWalletBalance(@PathVariable("id") int id, @PathVariable("amount") BigDecimal amount);

    @GetMapping("/api/v1//wallet/{id}")
    public ResponseEntity<Wallet> getWallet(@PathVariable int id);
}