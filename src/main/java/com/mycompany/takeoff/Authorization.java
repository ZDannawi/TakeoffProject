
package com.mycompany.takeoff;

/**
 *
 * @author zachariadannawi
 */
public abstract class Authorization {
        
    abstract public boolean authorize(long accountId, int pin);
    
    abstract public void logout(Account account);
}
