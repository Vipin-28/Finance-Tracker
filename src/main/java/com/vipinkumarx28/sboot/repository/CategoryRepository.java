package com.vipinkumarx28.sboot.repository;

import com.vipinkumarx28.sboot.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Object> findByName(String name);
}
