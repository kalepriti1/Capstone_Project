package com.natwest.walletservice.repository;

import com.natwest.walletservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

//    Wallet findAccountByAccountNumber(Long accountNumber);
}
