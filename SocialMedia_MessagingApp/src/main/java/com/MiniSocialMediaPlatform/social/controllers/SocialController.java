package com.MiniSocialMediaPlatform.social.controllers;


import com.MiniSocialMediaPlatform.social.models.SocialUser;
import com.MiniSocialMediaPlatform.social.services.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SocialController {

    @Autowired
    private SocialService socialService;


    @GetMapping("/social/users")
    public ResponseEntity<List<SocialUser>> getUsers() {
        return new ResponseEntity<>(socialService.getAllUsers(), HttpStatus.OK);
    }


    @PostMapping("/social/users")
    public ResponseEntity<SocialUser> createUsers(@RequestBody SocialUser user) {
        return new ResponseEntity<>(socialService.saveUser(user), HttpStatus.CREATED);
    }


}
