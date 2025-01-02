package com.ecommerce.store.security.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
public class SignupRequest {

    @NotBlank
    @Size(min = 2, max = 50)
    private String username;

    @NotBlank
    @Size(min = 2, max = 50)
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 50)
    private String lastName;

    @Setter
    @Getter
    private Set<String> roles;

}
