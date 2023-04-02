package com.example.bankingapp.service;

import com.example.bankingapp.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Scanner;
@Service
@RequiredArgsConstructor
public class UserDashboardService {
    private final AccountService accountService;

    public void showDashboard(Account loggedAccount){

        boolean isLogged = true;
        while(isLogged){
            System.out.println(loggedAccount.toString());
            int selectedOption = chooseDashboardOption();

            switch (selectedOption){
                case 1:
                    accountService.addMoney(loggedAccount);
                    break;
                case 2:
                    accountService.withdrawMoney(loggedAccount);
                    break;
               case 3:
                   accountService.initializeTransferMoney(loggedAccount);
                case 0:
                    isLogged = false;
                    break;
            }
        }


    }

    public int chooseDashboardOption(){
        boolean isDashboard = true;
        while(isDashboard){
            System.out.println("Press 1 to add money.");
            System.out.println("Press 2 to withdraw money.");
            System.out.println("Press 3 to transfer money.");
            System.out.println("Press 0 to log out.");
            try {
                Scanner scanner = new Scanner(System.in);
                int selectedOption = scanner.nextInt();
                isDashboard = false;
                return  selectedOption;
            } catch (Exception exception) {
                System.out.println("You have type the wrong format. Please use correct numbers. ");
            }

        }
        return 0;
    }
}
