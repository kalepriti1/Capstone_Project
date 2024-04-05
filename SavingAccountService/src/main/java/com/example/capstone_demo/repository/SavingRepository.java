package com.example.capstone_demo.repository;

import com.example.capstone_demo.entity.SavingAccount;
import com.example.capstone_demo.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingRepository extends JpaRepository<SavingAccount, String> {

    SavingAccount findAccountByAccountNo(Long accountNo);

    boolean existsByAccountNo(Long accountNo);

    SavingAccount findByEmail(String email);

    boolean existsByEmail(String email);

    SavingAccount findByWalletId(int walletId);

    boolean existsByWalletId(int walletId);

}