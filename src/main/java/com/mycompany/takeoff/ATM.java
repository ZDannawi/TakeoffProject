
package com.mycompany.takeoff;
import java.util.*;

public class ATM {
    
    // Default amount of bills inside ATM
    private static int ATM_AMOUNT;
    
    public ATM() {
        this.ATM_AMOUNT = 10000;
    }

    /**
     * dispenseAmount(int amount)
     * @param amount
     * @return 
     * 
     * Checks first if ATM has enough money to initiate a withdrawal.
     * If the amount requested is greater than the amount inside the ATM,
     * then we return whatever amount is inside the ATM.
     */
    public int dispenseAmount(int amount) {
        if(validateWithdrawalLimit(amount)) {
            this.ATM_AMOUNT -= amount;
        } else {
            amount = this.ATM_AMOUNT;
            this.ATM_AMOUNT = 0;
        }
        return amount;
    }
    
    public void depositAmount(int amount) {
        this.ATM_AMOUNT += amount;
    }
    
    /**
     * validateWithdrawal(int requestedAmount)
     * @param requestedAmount
     * @return 
     * 
     * Returns false if there is no money / not enough money to cover the
     * amount requested by the user. Otherwise, returns true.
     */
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
    
    public int getATMAmount() {
        return this.ATM_AMOUNT;
    }
    
}
