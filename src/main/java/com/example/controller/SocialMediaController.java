package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    @PatchMapping("/messages/{messageID}")
    public void patchMessage() {

    }
    
    //focus on the wording of messageID
    @DeleteMapping("/messages/{messageID}")
    public void deleteMessage() {

    }
    
    //focus on the wording of accountID
    @GetMapping ("/accounts/{accountID}/messages")
    public List<Message> getUserMessage() {
        return null;
    }

    //focus on the wording of messageID
    @GetMapping("/messages/{messageID}")
    public Message getMessage() {
        return null;
    }
    
    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return null;
    }

    @PostMapping("/messages")
    public Message postMessage() {
        return null;
    }

    @PostMapping("/login")
    public Account postLogin() {
        return null;
    }
    
    @PostMapping("/register")
    public Account postRegister() {
        return null;
    }

}
