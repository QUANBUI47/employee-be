package com.gms.employeebe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EmployeeBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeBeApplication.class, args);
    }


    @Bean
    CommandLineRunner printBcrypt(PasswordEncoder encoder) {
        return args -> {
            String raw = "admin123"; // đổi nếu cần
            String hash = encoder.encode(raw);
            System.out.println("========== COPY BCRYPT ==========");
            System.out.println("BCrypt(\"" + raw + "\") = " + hash);
            System.out.println("=================================");
        };
    }


}
