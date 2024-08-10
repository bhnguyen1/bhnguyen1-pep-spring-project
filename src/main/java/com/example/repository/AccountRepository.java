package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
 * Point of the repository class is that it acts as a way to write custom queries in a JpaRepository using JPQL
 * It abstacts away from boilerplate logic by directly mapping queries to ORM (Object Relational Mapper) entities
 * It is also vendor-agnostic: JPQL query does not change between, like MySQL 
 * Sort of treat this like the DAO class we did back in project 1, however only implement queries that are otherwise
 * not in the defined methods
 
    Use table named: account
    account_id (primary key) : type int
    username (unique) : type varchar(255)
    password: type varchar(255)

    check if account exists (select)
    -------------------------------------------------------------------
    login into account with password (select) 
*/

public interface AccountRepository extends JpaRepository<Account, Long> {
    //Checks to see if the account exists with username
    @Query("Select a from Account a where a.username = :username")
    Account checkAccount(@Param("username") String username);

    //checks to see if account username exists and matches with password
    @Query("Select a from Account a where a.username = :username and a.password = :password")
    Account login(@Param("username") String username, @Param("password") String password);

    // Account findAccountByUsername(String username);
    // Account findAccountByUsernameAndPassword(String username, String password)
}
