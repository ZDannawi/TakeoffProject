
package com.mycompany.takeoff;
import java.util.*;

public class ATM {
    
    // Default amount of bills inside ATM
    private static int ATM_AMOUNT;
    
    public ATM() {
        this.ATM_AMOUNT = 10000;
    }

    public int dispenseAmount(int amount) {
        if(validateWithdrawalLimit(amount)) {
            this.ATM_AMOUNT -= amount;
        } else {
            return this.ATM_AMOUNT -= this.ATM_AMOUNT;
        }
        return amount;
    }
    
    public void depositAmount(int amount) {
        this.ATM_AMOUNT += amount;
    }
    
    public boolean validateWithdrawalLimit(int requestedAmount) {
        if(this.ATM_AMOUNT == 0) {
            System.out.println("Unable to process your withdrawal at this time.");
            return false;
        } else if (requestedAmount > this.ATM_AMOUNT) {
            System.out.println("Unable to dispense full amount requested at this time.");
            return false;
        }
        return true;
    }
    
    public int getATMAMount() {
        return this.ATM_AMOUNT;
    }
    
}
