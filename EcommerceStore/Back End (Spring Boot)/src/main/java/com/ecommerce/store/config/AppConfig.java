package com.ecommerce.store.config;

import com.ecommerce.store.model.ApplicationRole;
import com.ecommerce.store.model.Role;
import com.ecommerce.store.model.User;
import com.ecommerce.store.repositories.RoleRepository;
import com.ecommerce.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    @Transactional
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {

        return args -> {

            if (AppConstants.RESET_DATABASE){

                Role userRole = roleRepository.findByRoleName(ApplicationRole.ROLE_USER)
                        .orElseGet(
                                ()-> {
                                    Role newUserRole = new Role(ApplicationRole.ROLE_USER);
                                    return roleRepository.save(newUserRole);
                                });

                Role sellerRole = roleRepository.findByRoleName(ApplicationRole.ROLE_SELLER)
                        .orElseGet(
                                ()-> {
                                    Role newSellerRole = new Role(ApplicationRole.ROLE_SELLER);
                                    return roleRepository.save(newSellerRole);
                                });

                Role adminRole = roleRepository.findByRoleName(ApplicationRole.ROLE_ADMIN)
                        .orElseGet(
                                ()-> {
                                    Role newAdminRole = new Role(ApplicationRole.ROLE_ADMIN);
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

                User seller = new User();
                if (userRepository.findByUsername("seller1").isPresent())
                {
                    seller = userRepository.findByUsername("seller1").get();
                    seller.setRoles(sellerRoles);
                    userRepository.save(seller);
                }

                userRepository.findByUsername("admin1").ifPresent(user->{
                    user.setRoles(adminRoles);
                    userRepository.save(user);
                });

                System.out.println("Database has been reset");

            }else{
                System.out.println("Database not reset");
            }
        };

    }

}
