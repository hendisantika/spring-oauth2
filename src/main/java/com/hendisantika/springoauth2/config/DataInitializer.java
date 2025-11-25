package com.hendisantika.springoauth2.config;

import com.hendisantika.springoauth2.entity.Account;
import com.hendisantika.springoauth2.entity.Role;
import com.hendisantika.springoauth2.repository.AccountRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Initialize sample data for testing
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(AccountRepo accountRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create a regular user
            Account user = new Account();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("password"));
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setEmail("user@example.com");
            user.grantAuthority(Role.ROLE_USER);
            accountRepo.save(user);

            // Create an admin user
            Account admin = new Account();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail("admin@example.com");
            admin.grantAuthority(Role.ROLE_ADMIN);
            admin.grantAuthority(Role.ROLE_USER);
            accountRepo.save(admin);
        };
    }
}
