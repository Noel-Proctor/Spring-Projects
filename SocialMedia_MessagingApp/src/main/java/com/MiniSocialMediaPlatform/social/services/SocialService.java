package com.MiniSocialMediaPlatform.social.services;

import com.MiniSocialMediaPlatform.social.models.SocialUser;
import com.MiniSocialMediaPlatform.social.repositories.SocialUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialService {

    @Autowired
    private SocialUserRepository socialUserRepository;

    public List<SocialUser> getAllUsers(){
        return socialUserRepository.findAll();
    }

    public SocialUser saveUser(SocialUser user) {
        return socialUserRepository.save(user);
    }
}
