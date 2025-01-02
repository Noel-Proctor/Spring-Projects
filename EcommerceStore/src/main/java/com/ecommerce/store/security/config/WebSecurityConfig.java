package com.ecommerce.store.security.config;


import com.ecommerce.store.model.ApplicationRole;
import com.ecommerce.store.model.Role;
import com.ecommerce.store.model.User;
import com.ecommerce.store.repositories.RoleRepository;
import com.ecommerce.store.repositories.UserRepository;
import com.ecommerce.store.security.Services.UserDetailsService.UserDetailsServiceImpl;
import com.ecommerce.store.security.jwt.AuthEntryPointJwt;
import com.ecommerce.store.security.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
            return authConfig.getAuthenticationManager();
        }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(
                        unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
//                                .requestMatchers("/api/admin/**").permitAll()
//                                .requestMatchers("api/public/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/api/test/**").permitAll()
                                .requestMatchers("/images/**").permitAll().anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);

        http.headers(headers -> headers.frameOptions(
                frameOptions -> frameOptions.sameOrigin()
        ));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/configuration/security"));
    }


    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){

        return args->{
            Role userRole = roleRepository.findByRoleName(ApplicationRole.USER)
                    .orElseGet(
                            ()-> {
                                Role newUserRole = new Role(ApplicationRole.USER);
                                return roleRepository.save(newUserRole);
                            });

            Role sellerRole = roleRepository.findByRoleName(ApplicationRole.SELLER)
                    .orElseGet(
                            ()-> {
                                Role newSellerRole = new Role(ApplicationRole.SELLER);
                                return roleRepository.save(newSellerRole);
                            });

            Role adminRole = roleRepository.findByRoleName(ApplicationRole.ADMIN)
                    .orElseGet(
                            ()-> {
                                Role newAdminRole = new Role(ApplicationRole.ADMIN);
                                return roleRepository.save(newAdminRole);
                            });

            Set<Role> userRoles = Set.of(userRole);
            Set<Role> sellerRoles = Set.of(sellerRole);
            Set<Role> adminRoles = Set.of(adminRole);

            if (!userRepository.existsByUsername("user1")){
                User user = new User("user1", passwordEncoder.encode("123qwe"), "Bill", "Williamson", "dutchvanderlinesgang@gmail.com");
                userRepository.save(user);
            }

            if (!userRepository.existsByUsername("seller1")){
                User seller = new User("seller1",  passwordEncoder.encode("123qwe"), "Luke", "Littler", "dartsman2004@gmail.com");
                userRepository.save(seller);

            }

            if (!userRepository.existsByUsername("admin1")){
                User admin = new User("admin1",  passwordEncoder.encode("123qwe"), "Kekius", "Maximius", "spaceman@space.com");
                userRepository.save(admin);

            }

            userRepository.findByUsername("user1").ifPresent(user->{
                user.setRoles(userRoles);
                userRepository.save(user);
            });

            userRepository.findByUsername("seller1").ifPresent(user->{
                user.setRoles(sellerRoles);
                userRepository.save(user);
            });

            userRepository.findByUsername("admin1").ifPresent(user->{
                user.setRoles(adminRoles);
                userRepository.save(user);
            });
        };


    }







}
