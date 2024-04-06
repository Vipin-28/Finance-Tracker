package com.vipinkumarx28.sboot.repository;

import com.vipinkumarx28.sboot.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    public Optional<Expense> findExpenseByName(String name);
}
