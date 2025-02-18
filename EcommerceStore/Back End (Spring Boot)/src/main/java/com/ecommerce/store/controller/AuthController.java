package com.ecommerce.store.controller;

import com.ecommerce.store.model.ApplicationRole;
import com.ecommerce.store.model.Role;
import com.ecommerce.store.model.User;
import com.ecommerce.store.repositories.RoleRepository;
import com.ecommerce.store.repositories.UserRepository;
import com.ecommerce.store.security.Services.UserDetailsService.UserDetailsImpl;
import com.ecommerce.store.security.jwt.JwtUtils;
import com.ecommerce.store.security.requests.LoginRequest;
import com.ecommerce.store.security.requests.SignupRequest;
import com.ecommerce.store.security.response.MessageResponse;
import com.ecommerce.store.security.response.UserInfoResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication auth;

        try{
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword())
            );
        }catch (AuthenticationException e){
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);

            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(auth);
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), roles);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username already exists");
        }

        if  (userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.badRequest().body("Error: Email already exists");
        }

        User user = new User(signupRequest.getUsername(), encoder.encode(signupRequest.getPassword()),signupRequest.getFirstName(), signupRequest.getLastName(),
                signupRequest.getEmail());

        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();


        if (strRoles == null){
            Role userRole = roleRepository.findByRoleName(ApplicationRole.ROLE_USER).orElseThrow(
                    ()-> new RuntimeException("Error: Role not found")
            );

            roles.add(userRole);
        }
        else{
            strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByRoleName(ApplicationRole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);

                    break;
                case "seller":
                    Role modRole = roleRepository.findByRoleName(ApplicationRole.ROLE_SELLER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);

                    break;
                default:
                    Role userRole = roleRepository.findByRoleName(ApplicationRole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
            }
        });
    }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
}

    @PostMapping("/signout")
    public ResponseEntity<?> signOut(Authentication auth){

        ResponseCookie responseCookie = jwtUtils.cleanCookie();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                responseCookie.toString()).body(new MessageResponse("You have been signed out."));


    }

    @GetMapping("/username")
    public String currentUsername(Authentication auth){
        if (auth !=null){
            return auth.getName();
        }else{
            return "NULL";
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(Authentication auth){

        if (auth ==null){
            return ResponseEntity.ok("No user currently logged in.");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), roles);

        return ResponseEntity.ok().body(response);


    }

}




