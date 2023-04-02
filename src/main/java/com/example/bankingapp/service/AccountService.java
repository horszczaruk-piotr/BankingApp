package com.example.bankingapp.service;

import com.example.bankingapp.model.Account;
import com.example.bankingapp.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransferService transferService;


    public Account createNewAccount() {
        Account account = new Account();

        long random = generateRandom(5);
        account.setAccountNumber("0000123456789" + random);

        System.out.println("You decided to create a new account.");
        String accountPin = getValidatedAccountPIN();

        account.setPin(accountPin);

        account.setBalance(0.00);

        System.out.println(account);

        accountRepository.save(account);
        return account;


    }

    private static String getValidatedAccountPIN() {
        String accountPIN = "0000";
        boolean isPIN = true;
        System.out.print("Please enter a valid PIN for your new account. PIN should have 4 digits: ");
        while (isPIN) {
            Scanner scanner = new Scanner(System.in);
            accountPIN = scanner.nextLine();
            // niech sprawdza czy PIN jest 4-cyfrowy
            Matcher m = Pattern.compile("\\d{4}").matcher(accountPIN);
            if (m.matches()) {
                isPIN = false;
                System.out.println("You have entered a 4-digit pin! ");
            } else {
                System.out.println("You have entered a wrong PIN. PIN should have 4 digits. ");
            }
        }
        return accountPIN;
    }

    public Account loginInput() {
        Account accountInput = new Account();
        System.out.println("Enter last five digits of your account number: ");
        Scanner scanner = new Scanner(System.in);
        String accountLogin = scanner.nextLine();
        accountInput.setAccountInput(accountLogin);
        System.out.println("Enter your PIN: ");
        Scanner scanner1 = new Scanner(System.in);
        String pinInput = scanner1.nextLine();
        accountInput.setPin(pinInput);
        return accountInput;
    }

    public Account loginVerify(Account accountInput) {
        var currentAccount = accountRepository.findByAccountNumberContains(accountInput.getAccountInput());
        System.out.println(accountInput.getAccountInput());
        if (currentAccount == null) {
            System.out.println("There is no account number like this in our repository.");
        } else {
            if (currentAccount.getPin().equals(accountInput.getPin())) {
                System.out.println("You have logged in successfully.");
                return currentAccount;
            } else {
                System.out.println("Login failed.");
            }
        }

        return null;
    }

    private long generateRandom(int input) {

        long Min = (long) Math.pow(10, input - 1);
        long Max = (long) (Math.pow(10, input) - 1);
        long rand = Min + (long) (Math.random() * ((Max - Min) + 1));
        return rand;
    }

    public Account addMoney(Account account){
        double balanceBefore = account.getBalance();

        boolean isRunning = true;
        while(isRunning){
            try {
                System.out.println("Enter the amount that you want to add: ");
                Scanner scanner = new Scanner(System.in);
                double addedAmount = scanner.nextDouble();
                //BigDecimal addedAmountBg = BigDecimal.valueOf(addedAmount);
                account.setBalance(balanceBefore + addedAmount);
                System.out.println("You have deposited " + addedAmount + " successfully.");
                isRunning = false;
            } catch (Exception exception) {
                System.out.println("You have typed the wrong format. Please use correct numbers.  ");
            }
        }

        accountRepository.save(account);
        return account;
    }

    public Account withdrawMoney(Account account){
        account.toString();
        double balanceBefore = account.getBalance();
        System.out.println(" Enter the amount that you wish to withdraw: ");

        try {
            Scanner scanner = new Scanner(System.in);
            double withdrawedAmount = scanner.nextDouble();
            if(withdrawedAmount > balanceBefore)
            {
                System.out.println("The amount that you wish to withdraw is bigger than your balance, please try again.");
            }
            else{
                account.setBalance(balanceBefore - withdrawedAmount);
                System.out.println("Please collect your " + withdrawedAmount +" zlotys.");
            }
        } catch (Exception exception) {
            log.error("You have typed the wrong format. Please use correct numbers. ", exception);

        }

        accountRepository.save(account);
        return account;
    }

    public void initializeTransferMoney(Account accountFrom){
        accountFrom.toString();
        boolean isRunning = true;

        while(isRunning){
            try {
                System.out.println("Enter the account number to which you want to transfer your money to: ");
                Scanner scanner = new Scanner(System.in);
                String accountToNumber = scanner.nextLine();
                System.out.println("Enter the amount of money which you want to transfer: ");
                double moneyToTransfer = scanner.nextDouble();
                transferService.transferMoney(accountFrom, accountToNumber, moneyToTransfer);
                isRunning = false;
            }catch (Exception exception){
                log.error("You have typed the wrong format. Please use correct numbers. ", exception);
            }
        }


    }

    public void showBalance(Account account){
        System.out.println(account.toString());

    }
}
