package com.example.bankingapp.service;

import com.example.bankingapp.model.Account;
import com.example.bankingapp.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final AccountRepository accountRepository;
    public void transferMoney(Account accountFrom, String accountToNumber, double moneyToTransfer){
        var accountTo = accountRepository.findByAccountNumberContains(accountToNumber);
        if (accountTo == null) {
            System.out.println("There is no account number like this in our repository.");
        } else {
            if (accountTo.getAccountNumber().equals(accountFrom.getAccountNumber())) {
                System.out.println("Account which sends and account which receives cannot have the same number.");
            } else {
                if (moneyToTransfer > accountFrom.getBalance()) {
                    System.out.println("The amount that you wish to transfer is bigger than your balance, please try again.");
                } else {
                    accountFrom.setBalance(accountFrom.getBalance() - moneyToTransfer);
                    accountTo.setBalance(accountTo.getBalance() + moneyToTransfer);
                    accountRepository.save(accountFrom);
                    accountRepository.save(accountTo);
                    System.out.println("You have transferred: " + moneyToTransfer + " zlotys to the account number: " + accountToNumber);
                }
            }
        }
    }
}
