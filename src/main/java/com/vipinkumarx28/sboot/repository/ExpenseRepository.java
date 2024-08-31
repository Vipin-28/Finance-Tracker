package com.vipinkumarx28.sboot.repository;

import com.vipinkumarx28.sboot.entities.Category;
import com.vipinkumarx28.sboot.entities.Expense;
import com.vipinkumarx28.sboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
//    public Optional<Expense> findExpenseByName(String name);

    public Optional<List<Expense>> findAllByUserAndCategory(User User, Category category);

    public Optional<List<Expense>> findAllByUserAndExpenseName(User user, String name);

//    @Query("SELECT e FROM Expense e WHERE e.user = :user")
//    public Optional<List<Expense>> findAllByUser(@Param("user") User user);

    @Query("SELECT e FROM Expense e WHERE e.user = :user")
    public List<Expense> findAllByUser(@Param("user") User user);

    @Query("SELECT e FROM Expense e WHERE e.user.userId = :userId")
    public Optional<List<Expense>> findByUserId(@Param("userId") Long userId);

    List<Expense> findByUserUserId(Long userId);
    //public Optional<List<Expense>> findByUser(User user);

    @Query("SELECT e FROM Expense e WHERE EXTRACT(MONTH FROM e.creationDate) = :month AND EXTRACT(YEAR FROM e.creationDate) = :year")
    List<Expense> findByMonthAndYear(@Param("month") int month, @Param("year") int year);


}
