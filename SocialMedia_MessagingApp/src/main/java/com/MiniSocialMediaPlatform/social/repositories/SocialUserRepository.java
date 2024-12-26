package com.MiniSocialMediaPlatform.social.repositories;

import com.MiniSocialMediaPlatform.social.models.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {
}
