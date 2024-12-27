package com.MiniSocialMediaPlatform.social.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne()
    @JoinColumn(name = "social_user")
    @JsonIgnore
    private SocialUser user;

//    public void setUser(SocialUser user) {
//        this.user = user;
//        if (user.getProfile() != this) {
//            user.setProfile(this);
//        }
//    }

    private String description;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
