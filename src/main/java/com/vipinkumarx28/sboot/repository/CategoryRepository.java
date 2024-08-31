package com.vipinkumarx28.sboot.repository;

import com.vipinkumarx28.sboot.entities.Category;
import com.vipinkumarx28.sboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Object> findByCategoryName(String name);
    public Optional<Category> findByUserAndCategoryName(User user, String categoryName);

//    @Query("SELECT c FROM Category c WHERE c.user.userId = :userId")
//    List<Category> findAllByUserId();

    @Query("SELECT c FROM Category c WHERE c.categoryName = :categoryName AND c.user.userId = :userId")
    Optional<Category> findCategoryByCategoryNameAndUserName(@Param("categoryName") String categoryName, @Param("userId") Long userId);

    List<Category> findByUserUserId(Long userId);

}
