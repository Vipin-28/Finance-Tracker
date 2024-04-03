package com.vipinkumarx28.sboot.config;


import com.vipinkumarx28.sboot.entities.Category;
import com.vipinkumarx28.sboot.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CategoryStartConfiguration {
    @Bean
    CommandLineRunner commandLineRunnerCategories(CategoryRepository categoryRepository){
        return args -> {
            Category others = new Category(
                    1L,
                    "others",
                    "default category"
            );

            Category groceries = new Category(
                    2L,
                    "groceries",
                    "You can add groceries here"
            );
            Category travel = new Category(
                    3L,
                    "travel",
                    "You can add travel here"
            );
            Category concerts = new Category(
                    4L,
                    "concerts",
                    "You can add concerts here"
            );
            Category cinema = new Category(
                    5L,
                    "cinema",
                    "You can add cinema here"
            );
            Category fitness = new Category(
                    6L,
                    "fitness",
                    "You can add fitness here"
            );
            categoryRepository.saveAll(
                    List.of(
                            others,
                            groceries,
                            travel,
                            concerts,
                            cinema,
                            fitness
                    )
            );
        };
    }
}
