/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.interfaces;

import entity.account.Account;

/**
 *
 * @author Francesco Capriglione
 * @version 1.1
 */
public interface AccountOperation {
    
    public Account doLogin(String email, String password);
    
    public Account doRetrieveByEmail(String email);
    
    public Account doRetrieveByEmailToken(String emailToken);
    
}
