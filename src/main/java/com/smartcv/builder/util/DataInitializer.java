package com.smartcv.builder.util;

import com.smartcv.builder.entity.Role;
import com.smartcv.builder.entity.Template;
import com.smartcv.builder.entity.User;
import com.smartcv.builder.repository.TemplateRepository;
import com.smartcv.builder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize Templates
        if (templateRepository.count() == 0) {
            Template modern = Template.builder()
                    .name("Modern")
                    .description("A clean, two-column layout perfect for modern tech and creative roles.")
                    .htmlStructure("modern") // Filename reference
                    .cssStyle("body { font-family: 'Helvetica'; }")
                    .active(true)
                    .build();

            Template classic = Template.builder()
                    .name("Classic")
                    .description("A traditional, single-column layout suitable for formal industries like law or finance.")
                    .htmlStructure("classic")
                    .cssStyle("body { font-family: 'Times New Roman'; }")
                    .active(true)
                    .build();

            Template creative = Template.builder()
                    .name("Creative")
                    .description("A bold, high-contrast design with specialized sections for portfolio and expertise.")
                    .htmlStructure("creative")
                    .cssStyle("body { font-family: 'Outfit'; }")
                    .active(true)
                    .build();

            templateRepository.saveAll(Arrays.asList(modern, classic, creative));
            System.out.println("Templates initialized successfully!");
        }

        // Initialize Admin User
        if (userRepository.findByEmail("admin@smartcv.com").isEmpty()) {
            User admin = User.builder()
                    .name("System Admin")
                    .email("admin@smartcv.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("Admin user created: admin@smartcv.com / admin123");
        }
    }
}
