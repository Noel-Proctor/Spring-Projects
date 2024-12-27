package com.MiniSocialMediaPlatform.social.configuration;

import com.MiniSocialMediaPlatform.social.models.Post;
import com.MiniSocialMediaPlatform.social.models.SocialGroup;
import com.MiniSocialMediaPlatform.social.models.SocialProfile;
import com.MiniSocialMediaPlatform.social.models.SocialUser;
import com.MiniSocialMediaPlatform.social.repositories.PostRepository;
import com.MiniSocialMediaPlatform.social.repositories.SocialGroupRepository;
import com.MiniSocialMediaPlatform.social.repositories.SocialProfileRepository;
import com.MiniSocialMediaPlatform.social.repositories.SocialUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitialiser {

    private final PostRepository postRepository;
    private final SocialGroupRepository socialGroupRepository;
    private final SocialProfileRepository socialProfileRepository;
    private final SocialUserRepository socialUserRepository;

    public DataInitialiser(PostRepository postRepository, SocialGroupRepository socialGroupRepository, SocialProfileRepository socialProfileRepository,
                           SocialUserRepository socialUserRepository) {
        this.postRepository = postRepository;
        this.socialGroupRepository = socialGroupRepository;
        this.socialProfileRepository = socialProfileRepository;
        this.socialUserRepository = socialUserRepository;
    }


    @Bean
    public CommandLineRunner initialiseData(){
            return args -> {

                SocialUser socialUser = new SocialUser();
                SocialUser socialUser1 = new SocialUser();
                SocialUser socialUser2 = new SocialUser();

                socialUser.setUsername("Noel");
                socialUser1.setUsername("Andrew");
                socialUser2.setUsername("Dave");

                socialUserRepository.save(socialUser);
                socialUserRepository.save(socialUser1);
                socialUserRepository.save(socialUser2);

//                Fetch Types

                System.out.println("FETCHING USER");
                socialUserRepository.findById( 1L);

                SocialGroup group = new SocialGroup();
                SocialGroup group1 = new SocialGroup();
                SocialGroup group2 = new SocialGroup();

                group.setGroupName("Fishing Ireland");
                group1.setGroupName("Cooking Together");
                group2.setGroupName("Cute Dogs");

                group.getSocialUsers().add(socialUser);
                socialUser.getGroups().add(group);

                group.getSocialUsers().add(socialUser1);
                socialUser1.getGroups().add(group);

                group1.getSocialUsers().add(socialUser2);
                socialUser2.getGroups().add(group1);

                group2.getSocialUsers().add(socialUser);
                socialUser.getGroups().add(group2);

                group2.getSocialUsers().add(socialUser1);
                socialUser1.getGroups().add(group2);

                group2.getSocialUsers().add(socialUser2);
                socialUser2.getGroups().add(group2);

                socialGroupRepository.save(group);
                socialGroupRepository.save(group1);
                socialGroupRepository.save(group2);

                socialUserRepository.save(socialUser);
                socialUserRepository.save(socialUser1);
                socialUserRepository.save(socialUser2);

                Post post = new Post();
                Post post1 = new Post();
                Post post2 = new Post();

                post.setUser(socialUser);
                post1.setUser(socialUser1);
                post2.setUser(socialUser);

                postRepository.save(post);
                postRepository.save(post1);
                postRepository.save(post2);

                SocialProfile socialProfile = new SocialProfile();
                SocialProfile socialProfile1 = new SocialProfile();
                SocialProfile socialProfile2 = new SocialProfile();

                socialProfile.setUser(socialUser);
                socialProfile1.setUser(socialUser1);
                socialProfile2.setUser(socialUser2);

                socialProfileRepository.save(socialProfile);
                socialProfileRepository.save(socialProfile1);
                socialProfileRepository.save(socialProfile2);

                group2.getSocialUsers().add(socialUser);



            };
    }
}
