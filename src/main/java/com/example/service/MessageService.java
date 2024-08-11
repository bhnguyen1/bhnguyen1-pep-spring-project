package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    //logic on here might be funky (71-73)
    public Message updateMessage(int messageId, Message newMessage) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(!optionalMessage.isPresent()) {
            System.out.println("Could not find message");
            return null;
        }
        if(newMessage.getMessageText().isEmpty() || newMessage.getMessageText().length() > 255) {
            System.out.println("Invalid Text");
            return null;
        }
        Message message = optionalMessage.get();
        message.setMessageText(newMessage.getMessageText());
        return messageRepository.save(message);
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
            return null;
        }
    } 

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Transactional
    public boolean deleteMessage(Message message) { //might need to hotfix by doing .getById() instead
        Optional<Message> optionalMessage = messageRepository.findById(message.getMessageId());
        if(optionalMessage.isPresent()) {
            messageRepository.deleteById(message.getMessageId());
            return true;
        } else {
            System.out.println("Could not find message");
            return false;
        }
    }
    
    @Transactional
    public Message createMessage(Message message) {
        if(!accountRepository.existsAccountByAccountId(message.getPostedBy())) {
            System.out.println("User does not exist!");
            return null;
        }
        if(message.getMessageText().isEmpty() || message.getMessageText().length() > 255) {
            System.out.println("Invalid text!");
            return null;
        }
        return messageRepository.save(message);
    }
}
