package com.example.androidconcepts.ood.solid.o.learning1.after

import com.example.androidconcepts.ood.solid.o.learning1.Employee

interface TaxCalculator {
    fun getTax(employee: Employee): Long
}

class FullTimeEmployeeTaxCalculator : TaxCalculator {
    override fun getTax(employee: Employee): Long {
        return 100L
    }
}

class FreelanceEmployeeTaxCalculator : TaxCalculator {
    override fun getTax(employee: Employee): Long {
        return 200L
    }
}

class InternEmployeeTaxCalculator : TaxCalculator {
    override fun getTax(employee: Employee): Long {
        return 0L
    }
}