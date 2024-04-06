package com.vipinkumarx28.sboot.config;


import com.vipinkumarx28.sboot.entities.Expense;
import com.vipinkumarx28.sboot.repository.ExpenseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class ExpenseStartConfigruation {
    @Bean
    CommandLineRunner commandLineRunnerExpenses(ExpenseRepository expenseRepository){
        return args -> {
            Expense rent = new Expense(
                    1L, "Room Rent", 1L, 10000.0, LocalDate.now().minusDays(1), "Room rent for current month"
            );
            expenseRepository.saveAll(
                    List.of(
                            rent
                    )
            );
        };
    }
}
