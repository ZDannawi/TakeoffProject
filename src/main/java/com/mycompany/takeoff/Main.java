package com.mycompany.takeoff;
import java.util.*;

/**
 *
 * @author zachariadannawi
 */
public class Main {
    
    private static Scanner keyboard = new Scanner(System.in);
    private static ATM atm = new ATM();
    private static TestAccounts testAccounts = new TestAccounts();
    public static Account account;
    private static boolean authentication = true;
    
    public static void main(String[] args) {
        int choice = 0;
        boolean exit = true;
//        printInstructions();
        while(exit) {
            while(authentication) {
                account = authorizeAccount();
                performTransaction(account);
                authentication = false;
            }
            exit = false;
        }
    }
    
    public static void printInstructions() {
        System.out.println("Main Menu: ");
        System.out.println("1. - Make a withdrawal");
        System.out.println("2. - Make a deposit");
        System.out.println("3. - Print current balance");
        System.out.println("4. - Print transaction history");
        System.out.println("5. - Logout");
        System.out.println("6. - End application");
        System.out.println("-------------------");

    }
    
    public static Account authorizeAccount() {
        System.out.println("Please enter the accountId: ");
        long accountId = keyboard.nextLong();
        account = testAccounts.loadAccount(accountId);
        if(account != null ) {
            System.out.println("Please enter the pin associated with the account: ");
            int pin = keyboard.nextInt();
            if(account.validatePin(pin)) {
                System.out.println(accountId + " successfully authorized");
                System.out.println();
            } else {
                System.out.println("Authorization failed");
            }
        }
        return account;
    }
    
    public static void performTransaction(Account currentAccount) {
        boolean quit = false;
        while(!quit) {
            printInstructions();
            System.out.println("What action would you like to perform?");
            int action = keyboard.nextInt();
            switch(action) {
                case 1:
                    withdraw(currentAccount);
                    break;
                case 2:
                    deposit(currentAccount);
                    break;
                case 3:
                    balance(currentAccount);
                    break;
                case 4:
                    printTransactionHistory(currentAccount);
                    break;
                case 5:
                    logout(currentAccount);
                    break;
                case 6:
                    quit = true;
                    break;
            }
        }
    }
    
    public static void withdraw(Account currentAccount) {
        System.out.println("How much money would you like to withdraw?");
        int amount = keyboard.nextInt();
        int cashDispensed;
        if(amount % 20 != 0) {
            System.out.println("Invalid amount! Must be multiple of $20");
            amount = keyboard.nextInt();
        }
        if(!currentAccount.overdrawnFlag()) {
            if(atm.validateWithdrawalLimit(amount)) {
                cashDispensed = atm.dispenseAmount(amount);
                System.out.println("Amount dispensed: $" + cashDispensed);
                currentAccount.withdraw(cashDispensed);
            } else {
                cashDispensed = atm.dispenseAmount(amount);
                System.out.println("Amount dispensed: $" + cashDispensed);
                currentAccount.withdraw(cashDispensed);
            }
        }
        System.out.println("Your account is overdrawn! You cannot make withdrawals at this time");
    }

    public static void deposit(Account currentAccount) {
        System.out.println("How much money would you like to deposit?");
        double amount = keyboard.nextDouble();
        currentAccount.deposit(amount);
    }
    
    public static void balance(Account currentAccount) {
        currentAccount.printBalance();
    }
    
    public static void printTransactionHistory(Account currentAccount) {
        account.printTransactionHistory(currentAccount);
    }
    
    public static void logout(Account currentAccount){
        account.logout(currentAccount);
    }
    

}
