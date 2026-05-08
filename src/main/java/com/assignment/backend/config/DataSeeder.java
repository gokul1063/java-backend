package com.assignment.backend.config;

import com.assignment.backend.entity.Bot;
import com.assignment.backend.entity.User;
import com.assignment.backend.repository.BotRepository;
import com.assignment.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BotRepository botRepository;

    public DataSeeder(UserRepository userRepository,
                      BotRepository botRepository) {

        this.userRepository = userRepository;
        this.botRepository = botRepository;
    }

    @Override
    public void run(String... args) {

        if (userRepository.count() == 0) {

            userRepository.save(
                    new User(
                            null,
                            "gokul",
                            true
                    )
            );

            userRepository.save(
                    new User(
                            null,
                            "alex",
                            false
                    )
            );
        }

        if (botRepository.count() == 0) {

            botRepository.save(
                    new Bot(
                            null,
                            "NewsBot",
                            "News summarization bot"
                    )
            );

            botRepository.save(
                    new Bot(
                            null,
                            "TechBot",
                            "Technology assistant bot"
                    )
            );
        }

        System.out.println("Seed data inserted successfully");
    }
}
