package com.example.androidconcepts.ood.solid.o.learning1.before

import com.example.androidconcepts.ood.solid.o.learning1.Employee

class SalaryCalculatorBefore {
    fun calculateSalary(employee: Employee) {
        val tax = getTaxForEmployee(employee.type)
    }

    private fun getTaxForEmployee(employeeType: Int): Long {
        return when (employeeType) {
            Employee.FULLTIME -> {
                100L
            }
            Employee.FREELANCE -> {
                200L
            }
            Employee.INTERN -> {
                0L
            }
            else -> {
                200L
            }
        }
    }
}