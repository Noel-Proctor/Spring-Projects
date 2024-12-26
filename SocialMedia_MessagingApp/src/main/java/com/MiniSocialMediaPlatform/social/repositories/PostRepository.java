package com.MiniSocialMediaPlatform.social.repositories;

import com.MiniSocialMediaPlatform.social.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
