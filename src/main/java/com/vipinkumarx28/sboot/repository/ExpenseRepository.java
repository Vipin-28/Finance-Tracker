package com.vipinkumarx28.sboot.repository;

import com.vipinkumarx28.sboot.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    public Optional<Expense> findExpenseByName(String name);

    @Query("SELECT e FROM Expense e WHERE EXTRACT(MONTH FROM e.creationDate) = :month AND EXTRACT(YEAR FROM e.creationDate) = :year")
    List<Expense> findByMonthAndYear(@Param("month") int month, @Param("year") int year);
}
