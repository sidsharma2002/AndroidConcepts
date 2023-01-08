package com.example.androidconcepts.ood.solid.o.learning1.after

import com.example.androidconcepts.ood.solid.o.learning1.Employee

class TaxCalculatorFactory {
    fun getTaxCalculator(employee: Employee): TaxCalculator {
        return when (employee.type) {
            Employee.FREELANCE -> FreelanceEmployeeTaxCalculator()
            Employee.FULLTIME -> FullTimeEmployeeTaxCalculator()
            Employee.INTERN -> InternEmployeeTaxCalculator()
            else -> { FullTimeEmployeeTaxCalculator() }
        }
    }
}