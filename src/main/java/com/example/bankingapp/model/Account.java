package com.example.bankingapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {
    @Id
    private String accountNumber;
    @Transient
    private String accountInput;
    private String pin;
    double balance;


    public String getLogin(){
        return accountNumber.substring(accountNumber.length() - 5);
    }

    @Override
    public String toString() {
        return "Account details: " +
                "Your account number='" + accountNumber + '\'' +
                ", balance=" + balance +
                '.';
    }
}
