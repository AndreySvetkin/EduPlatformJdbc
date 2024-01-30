
package ru.svetkin.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@EnableJdbcRepositories("ru.svetkin.repository")
@SpringBootApplication(scanBasePackages="ru.svetkin")
public class Main{
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}