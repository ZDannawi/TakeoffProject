/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mycompany.takeoff.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author zachariadannawi
 */
public class TakeoffUnitTests {
    static TestAccounts testAccounts;
    static Account account;
    static ATM atm;
    
    
    public TakeoffUnitTests() {
        
    }
    
    @BeforeEach
    public static void setUp() {
        testAccounts = new TestAccounts();
        atm = new ATM();
    }
    
    @Test
    public void testAuthorizedAccount(){
        account = testAccounts.loadAccount(2859459814L);
        assertEquals(account.getAuthValue(), 0, "Authorization is initialized to invalid value");
        assertTrue(account.authorize(2859459814L, 7386));
        assertTrue(account.isAccountAuthorized(), "Authorization successful");
    }
    
    @Test
    public void testAuthorized_Failure() {
        account = testAccounts.loadAccount(2859459814L);
        assertEquals(account.getAuthValue(), 0, "Authorization is initialized to invalid value");
        assertFalse(account.authorize(2859459814L, 0));
        assertFalse(account.isAccountAuthorized(), "Authorization invalid");
    }
    
    @Test
    public void testWithdrawAmount_TriggerOverdraft() {
        account = testAccounts.loadAccount(2859459814L);
        assertEquals(account.getBalance(), 10.24, "Initial balance of account");
        atm.dispenseAmount(20);
        account.withdraw(20);
        assertTrue(account.overdrawnFlag(), "Account now overdrawn");
        assertEquals(account.getBalance(), -14.76, "Account overdraft with $5 charge");
    }
    
    @Test
    public void testATM_WithdrawAmountLargerThanCurrentATMAmount() {
        testAccounts.addTestAccount(1234512323L, 1111, 15000.00);
        account = testAccounts.loadAccount(1234512323L);
        assertEquals(atm.getATMAmount(), 10000, "Initial ATM balance");
        atm.dispenseAmount(11000);
        assertFalse(atm.validateWithdrawalLimit(11000), "Unable to dispense full amount");
        assertEquals(atm.getATMAmount(), 0, "ATM out of money");
    }
}
