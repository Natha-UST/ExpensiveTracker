package com.ust.ExpenseTracker.controller;


import com.ust.ExpenseTracker.dto.ExpenseDTO;
import com.ust.ExpenseTracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public List<ExpenseDTO> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO) {
        ExpenseDTO createdExpense = expenseService.addExpense(expenseDTO);
        return ResponseEntity.ok(createdExpense);
    }

    @GetMapping("/date/{date}")
    public List<ExpenseDTO> getExpensesByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return expenseService.getExpensesByDate(localDate);
    }

    @GetMapping("/balance/{date}")
    public ResponseEntity<BigDecimal> getCarryOverBalance(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        BigDecimal carryOverBalance = expenseService.getCarryOverBalance(localDate);
        return ResponseEntity.ok(carryOverBalance);
    }
}

