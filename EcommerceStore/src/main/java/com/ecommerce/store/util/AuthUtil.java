package com.ecommerce.store.util;


import com.ecommerce.store.model.User;
import com.ecommerce.store.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    private UserRepository userRepository;

    public String loggedInEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                ()-> new UsernameNotFoundException("User not found with username "+authentication.getName()));

       return user.getEmail();
    }

    public Long loggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                ()-> new UsernameNotFoundException("User not found with username "+authentication.getName()));

        return user.getUserId();
    }


    public User loggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                ()-> new UsernameNotFoundException("User not found with username "+authentication.getName()));

        return user;
    }









}
