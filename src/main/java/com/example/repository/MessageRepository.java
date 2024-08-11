package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
 * Point of the repository class is that it acts as a way to write custom queries in a JpaRepository using JPQL
 * Sort of treat this like the DAO class we did back in project 1, however only implement queries that are otherwise
 * not in the defined methods
 * It abstacts away from boilerplate logic by directly mapping queries to ORM (Object Relational Mapper) entities
 * It is also vendor-agnostic: JPQL query does not change between, like MySQL 
 
    Use table named: message
    message_id (primary key) : type int
    posted_by (foregin key) : type int
    message_text : type varchar(255)
    time_posted_epoch : type long

    retrieve all messages from a user_id (select)
*/

public interface MessageRepository extends JpaRepository<Message, Integer>{
    //to see all messages from a specific user with their id
    @Query("Select m from Message m where m.postedBy = :postedBy")
    List<Message> getMessagesFromUser(@Param("postedBy") Integer postedBy);

    // List<Message> findMessagesByPostedBy(Integer postedBy); 
}
