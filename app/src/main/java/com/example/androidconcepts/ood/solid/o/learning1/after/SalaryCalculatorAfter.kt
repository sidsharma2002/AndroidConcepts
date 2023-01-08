package com.example.androidconcepts.ood.solid.o.learning1.after

import com.example.androidconcepts.ood.solid.o.learning1.Employee

class SalaryCalculatorAfter {
    private val taxCalculatorFactory = TaxCalculatorFactory()

    fun calculateSalary(employee: Employee) {
        val taxCalculator = taxCalculatorFactory.getTaxCalculator(employee)
        val tax = taxCalculator.getTax(employee)
    }
}