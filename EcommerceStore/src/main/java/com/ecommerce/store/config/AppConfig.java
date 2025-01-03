package com.ecommerce.store.config;

import com.ecommerce.store.model.*;
import com.ecommerce.store.repositories.CategoryRepository;
import com.ecommerce.store.repositories.ProductRepository;
import com.ecommerce.store.repositories.RoleRepository;
import com.ecommerce.store.repositories.UserRepository;
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
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder,
                                      CategoryRepository categoryRepository, ProductRepository productRepository) {

        return args -> {

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

            User seller = new User();
            if (userRepository.findByUsername("seller1").isPresent())
            {
                seller = userRepository.findByUsername("seller1").get();
                seller.setRoles(sellerRoles);
                seller = userRepository.save(seller);
            }

            userRepository.findByUsername("admin1").ifPresent(user->{
                user.setRoles(adminRoles);
                userRepository.save(user);
            });


//            Create Categories

            Category fishingCategory = categoryRepository.findByCategoryName("Fishing");
            if (fishingCategory ==null){
                fishingCategory = categoryRepository.save(new Category("Fishing"));
            }

            Category cookingCategory = categoryRepository.findByCategoryName("Cooking");
            if (cookingCategory == null) {
                cookingCategory = categoryRepository.save(new Category("Cooking"));
            }

            Category sportsCategory = categoryRepository.findByCategoryName("Sports");
            if (sportsCategory == null) {
                sportsCategory = categoryRepository.save(new Category("Sports"));
            }


//            Create Products
            if(!productRepository.existsByProductNameAndCategory("12 ft Fishing Rod", fishingCategory)){
                Product newProduct = new Product("12ft Fishing Rod",
                        "Great for all types of fishing", 10, 80.00, "default.png", 5,
                        fishingCategory, seller);
                productRepository.save(newProduct);

            }

            if(!productRepository.existsByProductNameAndCategory("Fishing Net", fishingCategory)){
                Product newProduct = new Product("Fishing Net",
                        "Don't let them get away", 20, 20.00, "default.png", 10,
                        fishingCategory, seller);
                productRepository.save(newProduct);

            }

            if(!productRepository.existsByProductNameAndCategory("Casserole Dish", cookingCategory)){
                Product newProduct = new Product("Casserole Dish",
                        "Yum Yum in my tum", 230, 66.00, "default.png", 0,
                        cookingCategory, seller);
                productRepository.save(newProduct);

            }

            if(!productRepository.existsByProductNameAndCategory("Football", sportsCategory)){
                Product newProduct = new Product("Football",
                        "A literal football", 30, 10.00, "default.png", 10,
                        sportsCategory, seller);
                productRepository.save(newProduct);

            }

            if(!productRepository.existsByProductNameAndCategory("Goal Keeper Gloves", sportsCategory)){
                Product newProduct = new Product("!!SALE!!-- Goal Keeper Gloves--!!SALE!!",
                        "One day you will get picked to play outfield.", 30, 50.00, "default.png", 10,
                        sportsCategory, seller);
                productRepository.save(newProduct);

            }

        };

    }

}
