package com.vipinkumarx28.sboot.config;


import com.vipinkumarx28.sboot.entities.Category;
import com.vipinkumarx28.sboot.entities.User;
import com.vipinkumarx28.sboot.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PopulateInitialCategoriesComponent {

    @Autowired
    private CategoryRepository categoryRepository;

    public void populateCategoriesForUser(User user){
        Category others = new Category(
                "others",
                "default category",
                user
        );

        Category groceries = new Category(
                "groceries",
                "You can add groceries here",
                user
        );
        Category travel = new Category(
                "travel",
                "You can add travel here",
                user
        );
        Category concerts = new Category(
                "concerts",
                "You can add concerts here",
                user
        );
        Category cinema = new Category(
                "cinema",
                "You can add cinema here",
                user
        );
        Category fitness = new Category(
                "fitness",
                "You can add fitness here",
                user
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
    }
}
