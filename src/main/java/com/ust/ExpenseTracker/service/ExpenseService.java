package com.ust.ExpenseTracker.service;




import com.ust.ExpenseTracker.dto.ExpenseDTO;
import com.ust.ExpenseTracker.model.Expense;
import com.ust.ExpenseTracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ExpenseDTO addExpense(ExpenseDTO expenseDTO) {
        Expense expense = new Expense(expenseDTO.getDescription(), expenseDTO.getAmount(), expenseDTO.getDate());
        Expense savedExpense = expenseRepository.save(expense);
        return convertToDTO(savedExpense);
    }

    public List<ExpenseDTO> getExpensesByDate(LocalDate date) {
        return expenseRepository.findByDate(date).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BigDecimal calculateBalance(LocalDate date) {
        List<Expense> expenses = expenseRepository.findByDate(date);
        BigDecimal totalExpenses = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalExpenses.negate(); // Negative because expenses reduce balance
    }

    public BigDecimal getCarryOverBalance(LocalDate date) {
        LocalDate previousDate = date.minusDays(1);
        BigDecimal previousBalance = calculateBalance(previousDate);
        return previousBalance.add(previousBalance); // Carry over to the next day
    }

    private ExpenseDTO convertToDTO(Expense expense) {
        return new ExpenseDTO(expense.getId(), expense.getDescription(), expense.getAmount(), expense.getDate());
    }
}

