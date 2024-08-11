package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/*
 Purpose of this class is to contain business logic and use the repository 

 what logic is needed
    register a new user with password
    cases:
    * can't be a blank username
    * can't be a password with less than a length of 4 characters
    * can't use a username that already exists 
    * success
    -------------------------------------------------------------------
    login into account with password
    cases: 
    * invalid username
    * invalid password
    * success
*/

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account loginIntoAccount(String username, String password) {
        Account account = accountRepository.login(username, password);
        return account;
    }

    @Transactional
    public Account addAccount(Account account) {
        if(accountRepository.existsAccountByUsername(account.getUsername())) {
            System.out.println("Username already exists!");
            return null;
        }
        if(account.getUsername().isEmpty() || account.getPassword().length() < 4) {
            System.out.println("Invalid username or password!");
            return null;
        }
        return accountRepository.save(account);
    }


}
