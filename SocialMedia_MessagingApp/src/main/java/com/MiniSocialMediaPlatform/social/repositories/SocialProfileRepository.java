package com.MiniSocialMediaPlatform.social.repositories;

import com.MiniSocialMediaPlatform.social.models.SocialProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialProfileRepository extends JpaRepository<SocialProfile, Long> {
}
