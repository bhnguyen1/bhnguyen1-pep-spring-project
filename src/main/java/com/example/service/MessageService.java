package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/*
 Purpose of this class is to contain business logic and use the repository 

 what logic is needed
    create a message 
    cases:
    * can't be an empty message
    * can't be over 255 characters
    * user must exist
    * success
    ----------------------------------------------------------------
    delete a message 
    cases: 
    * must have existing id 
    ----------------------------------------------------------------
    retrieve all messages from a user 
    cases:
    * user must exist
    ----------------------------------------------------------------
    retrieve all messages
    cases: 
    * no message exist in the database
    ----------------------------------------------------------------
    retrieve a message by its id
    cases: 
    * must have existing id
    ----------------------------------------------------------------
    update a message by its id
    cases: 
    * can't be an empty message
    * can't be over 255 characters
    * message_id must exist
    * success
*/
@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public int updateMessage(int messageId, String newMessage) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(!optionalMessage.isPresent()) {
            throw new NoSuchElementException("Message does not exist!");
        }
        if(newMessage.isEmpty() || newMessage.length() > 255) {
            throw new IllegalArgumentException("Invalid text!");
        }
        Message message = optionalMessage.get();
        message.setMessageText(newMessage);
        messageRepository.save(message);
        return 1;
    }

    //check to see if arrayList will end up being wrong 
    public List<Message> getUserMessages(int postedBy) {
        if(!accountRepository.existsAccountByAccountId(postedBy)) {
            System.out.println("User does not exist");
            return new ArrayList<>();
        }
        if(messageRepository.getMessagesFromUser(postedBy).isEmpty()) {
            System.out.println("Empty List!");
            return new ArrayList<>();
        } else {
            return messageRepository.getMessagesFromUser(postedBy);
        }
    }

    public Message getMessageById(int messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent()) {
            return optionalMessage.get();
        } else {
            throw new NoSuchElementException("Could not find message");
        }
    } 

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Transactional
    public boolean deleteMessage(int messageId) { //might need to hotfix by doing .getById() instead
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent()) {
            messageRepository.deleteById(messageId);
            return true;
        } else {
            return false;
        }
    }
    
    @Transactional
    public Message createMessage(Message message) {
        if(!accountRepository.existsAccountByAccountId(message.getPostedBy())) {
            throw new NoSuchElementException("Account does not exist!");
        }
        if(message.getMessageText().isEmpty() || message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Invalid text!");
        }
        return messageRepository.save(message);
    }
}
