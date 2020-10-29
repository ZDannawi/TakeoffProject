
package com.mycompany.takeoff;
import java.util.*;


public class TestAccounts {
    public ArrayList<Account> accounts = new ArrayList<>();
    
    public TestAccounts() {
        accounts.add(new Account(2859459814L,7386,10.24));
        accounts.add(new Account(1434597300L,4557,90000.55));
        accounts.add(new Account(7089382418L,0075,0.00));
        accounts.add(new Account(2001377812L,5950,60.00));
    }
    
    public void addTestAccount(long accountId, int pin, double balance) {
        accounts.add(new Account(accountId, pin, balance));
    }
    
    private Account getAccount(long accountId) {
        for(Account currentAccount : accounts) {
            if(currentAccount.getAccountId() == accountId) {
                return currentAccount;
            }
        }
        return null;
    }
    
    public boolean authorize(long accountId, int pin) {
        Account userAccount = getAccount(accountId);
        if(userAccount != null) {
            userAccount.validatePin(pin);
            userAccount.setAuthValue(1);
            return true;
        } else {
            return false;
        }
    }
    
    public Account loadAccount(long accountId) {
        return getAccount(accountId);
    }
}
