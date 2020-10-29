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
    
    /**
     * Account constructor
     * @param accountId
     * @param pin
     * @param balance 
     * 
     * Sets initial authorization value to 0 to indicate that triggering
     * any command outside of logout or end will result in an invalid request.
     * Creates a new ArrayList<String> of a transactionHistory that will be
     * used to keep track of all transactions.
     */
    
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
    
    /**
     * 
     * @param amount 
     * Deposits money into account and adds specific transaction to
     * account history and prints current balance to console.
     */
    
    public void deposit(double amount) {
        this.balance += amount;
        addTransactionToHistory(amount);
        printBalance();
    }
    
    public void printBalance() {
        System.out.println("Current balance: " + formatAmount(this.balance));
    }
    
    /**
     * 
     * @param amount 
     * Checks to make sure that the account has sufficient money first
     * before making a withdrawal. Adds that withdrawal amount
     * (plus an overdraft fee if triggered) to transactionHistory
     */
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
    
    /**
     * 
     * @param amount
     * @return 
     * 
     * Validates if the current balance is greater than the amount requested 
     * to withdraw.
     */
    public boolean validateAccountLimit(int amount) {
        return (this.balance > amount);
    }
    
    
    /**
     * Authorize method
     * @param accountId
     * @param pin
     * @return 
     * 
     * This method looks up accounts from the TestAccount class
     * If the accountId that is passed in is not found, then we return false.
     * Otherwise, we validate the pin matches, and set the authorization value
     * 
     */
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
    
    
    /**
     * 
     * @param amount 
     * Gets the date and time of when the transaction was initiated, and
     * adds that history to TransactionHistory list.
     */
    public void addTransactionToHistory(double amount) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String transactionHistory = new String(timeStamp + "\t" + formatAmount(amount) + "\t" + this.getBalance());
        this.transactionHistory.add(transactionHistory);
    }
    
    /**
     * 
     * @param currentAccount 
     * Checks Account object first to see if there's any transaction history
     * If there are transactions, then prints transaction history in reverse order (most recent transactions first)
     */
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
    
    /**
     * Helper method that formats the double value to 2 decimal places.
     * @param amount
     * @return 
     */
    public double formatAmount(double amount) {
        DecimalFormat df = new DecimalFormat("#.##");
        amount = Double.valueOf(df.format(amount));
        return amount;
    }
}
