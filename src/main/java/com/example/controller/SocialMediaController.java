package com.example.controller;

import com.example.entity.*;
import com.example.service.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PatchMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Integer> patchMessage(@PathVariable int messageId, 
                                                              @RequestBody Message newMessage) {
        try {
            int result = messageService.updateMessage(messageId, newMessage.getMessageText());
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(400).body(null);
        } 
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }
    
    //focus on the wording of messageID
    @DeleteMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Integer> deleteMessage(@PathVariable int messageId) {
        boolean deleted = messageService.deleteMessage(messageId);
        if(deleted) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok(null);
        }
    }
    
    //focus on the wording of accountID
    @GetMapping ("/accounts/{accountId}/messages")
    public @ResponseBody List<Message> getUserMessage(@PathVariable int accountId) {
        return messageService.getUserMessages(accountId);
    }

    //focus on the wording of messageID
    @GetMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> getMessage(@PathVariable int messageId) {
        try {
            Message message = messageService.getMessageById(messageId);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }
    
    @GetMapping("/messages")
    public @ResponseBody List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> postMessage(@RequestBody Message message) {
        try {
            Message newMessage = messageService.createMessage(message);
            return ResponseEntity.ok(newMessage);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(400).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> postLogin(@RequestBody Account account) {
        try {
            Account registeredAccount = accountService.loginIntoAccount(account.getUsername(), account.getPassword());
            return ResponseEntity.ok(registeredAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(null);
        }
    }
    
    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> postRegister(@RequestBody Account account) {
        try {
            Account newAccount = accountService.addAccount(account);
            return ResponseEntity.ok(newAccount);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

}
