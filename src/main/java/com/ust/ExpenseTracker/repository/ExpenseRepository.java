package com.ust.ExpenseTracker.repository;


import com.ust.ExpenseTracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByDate(LocalDate date);
}
