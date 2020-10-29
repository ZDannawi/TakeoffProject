/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.takeoff;
import java.util.*;
import java.text.*;

/**
 *
 * @author zachariadannawi
 */
public class Account extends Authorization {
    
    long accountId;
    int pin;
    double balance;
    int authValue;
    ArrayList<String> transactionHistory;
    TestAccounts testAccount;
    
    public Account(long accountId, int pin, double balance) {
        this.accountId = accountId;
        this.pin = pin;
        this.balance = balance;
        this.authValue = 0;
        this.transactionHistory = new ArrayList<>();
    }
    
    public boolean validatePin(int pin) {
        return (this.pin == pin);
    }
    
    public boolean isAccountAuthorized() {
        return this.authValue == 1;
    }
    
    public boolean overdrawnFlag() {
        return 0 > this.balance;
    }

    public long getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }
    
    public int getAuthValue() {
        return this.authValue;
    }

    public void setAuthValue(int authValue) {
        this.authValue = authValue;
    }
    
    public void deposit(double amount) {
        this.balance += amount;
        addTransactionToHistory(amount);
        printBalance();
    }
    
    public void printBalance() {
        System.out.println("Current balance: " + formatAmount(this.balance));
    }
    
    public void withdraw(int amount) {
        if(validateAccountLimit(amount)) {
            this.balance -= amount;
            addTransactionToHistory(amount);
            printBalance();
        } else {
            this.balance -= amount + 5;
            System.out.println("You have been charged an overdraft fee of $5");
            addTransactionToHistory(amount);
        }
    }
    
    public boolean validateAccountLimit(int amount) {
        return (this.balance > amount);
    }
    
    @Override
    public boolean authorize(long accountId, int pin) {
        testAccount = new TestAccounts();
        Account userAccount = testAccount.loadAccount(accountId);
        if(userAccount != null) {
            if(userAccount.validatePin(pin)) {
                setAuthValue(1);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void logout(Account account) {
        if(account.getAuthValue() == 1) {
            System.out.println("Account " + account.getAccountId() + " logged out");
            setAuthValue(0);
        }
        System.out.println("No account is currently authorized");
    }
    
    public void addTransactionToHistory(double amount) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String transactionHistory = new String(timeStamp + "\t" + formatAmount(amount) + "\t" + this.getBalance());
        this.transactionHistory.add(transactionHistory);
    }
    
    public void printTransactionHistory(Account currentAccount) {
        if(currentAccount.transactionHistory.isEmpty()) {
            System.out.println("No history found");
            return;
        }
        System.out.println(currentAccount.transactionHistory.size());
        for(int i = currentAccount.transactionHistory.size() - 1; i >= 0; i--) {
            System.out.println(currentAccount.transactionHistory.get(i));
        }
    }
    
    public double formatAmount(double amount) {
        DecimalFormat df = new DecimalFormat("#.##");
        amount = Double.valueOf(df.format(amount));
        return amount;
    }
    
    @Override
    public String toString() {
        return "Account{" + "accountId=" + accountId + ", pin=" + pin + ", balance=" + balance + ", authValue=" + authValue + ", transactionHistory=" + transactionHistory + '}';
    }
    
    
    
}
