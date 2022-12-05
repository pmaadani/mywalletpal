package com.budgetapp.app.model;

public class MonthlyBudget {
    private int id;
    private String monthYear;
    private String monthlybudget;

    public MonthlyBudget(){

    }

    public MonthlyBudget(int id, String monthlybudget, String monthYear) {
        this.id = id;
        this.monthlybudget = monthlybudget;
        this.monthYear=monthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonthlybudget() {
        return monthlybudget;
    }

    public void setMonthlybudget(String name) {
        this.monthlybudget = name;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }
}
