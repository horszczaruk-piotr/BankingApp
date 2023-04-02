package com.example.bankingapp.service;

import com.example.bankingapp.model.Account;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@AllArgsConstructor
public class MenuService {
    private final AccountService accountService;
    private final UserDashboardService userDashboardService;
    private final ApplicationContext context;

    @PostConstruct
    public void showMenu() {

        System.out.println("Welcome to the banking app!");

        while (true) {
            int selectedOption = chooseMenuOption();

            switch (selectedOption) {
                case 1:
                    var newAccount = accountService.createNewAccount();

                    break;
                case 2:
                    //logowanie
                    Account accountInput = accountService.loginInput();
                    Account loggedAccount = accountService.loginVerify(accountInput);
                    userDashboardService.showDashboard(loggedAccount);
                    break;
                case 0:
                    closeApplication();
                    break;
                default:
                    System.out.println("You have pressed the wrong key. Please choose a valid option.");
                    break;
            }
        }
    }

    private int chooseMenuOption() {

        boolean isMenu = true;
        while (isMenu) {
            System.out.println("Press 1 to create an account.");
            System.out.println("Press 2 to login.");
            System.out.println("Press 0 to close the app.");
            try {
                Scanner scanner = new Scanner(System.in);
                int selectedOption = scanner.nextInt();
                isMenu = false;
                return selectedOption;
            } catch (Exception exception) {
                System.out.println("You have type the wrong format. Please use correct numbers. ");
            }
        }
        return 0;
    }

    private void closeApplication(){
        int exitCode = SpringApplication.exit(context, () -> 0);
        System.out.println("Done!");
        System.exit(exitCode);
    }
}
