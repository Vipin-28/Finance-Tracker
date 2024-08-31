//package com.vipinkumarx28.sboot.config;
//
//
//import com.vipinkumarx28.sboot.entities.Category;
//import com.vipinkumarx28.sboot.repository.CategoryRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class CategoryStartConfiguration {
//    @Bean
//    CommandLineRunner commandLineRunnerCategories(CategoryRepository categoryRepository){
//        return args -> {
//            Category others = new Category(
//                    1L,
//                    "others",
//                    "default category",
//                    null
//            );
//
//            Category groceries = new Category(
//                    2L,
//                    "groceries",
//                    "You can add groceries here",
//                    null
//            );
//            Category travel = new Category(
//                    3L,
//                    "travel",
//                    "You can add travel here",
//                    null
//            );
//            Category concerts = new Category(
//                    4L,
//                    "concerts",
//                    "You can add concerts here",
//                    null
//            );
//            Category cinema = new Category(
//                    5L,
//                    "cinema",
//                    "You can add cinema here",
//                    null
//            );
//            Category fitness = new Category(
//                    6L,
//                    "fitness",
//                    "You can add fitness here",
//                    null
//            );
//            categoryRepository.saveAll(
//                    List.of(
//                            others,
//                            groceries,
//                            travel,
//                            concerts,
//                            cinema,
//                            fitness
//                    )
//            );
//        };
//    }
//}
