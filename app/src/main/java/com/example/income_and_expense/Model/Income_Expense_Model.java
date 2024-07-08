package com.example.income_and_expense.Model;

public class Income_Expense_Model {

    private int regId;
    private String income;
    private String expense;
    private Byte cat_img;
    private String category;
    private String paymentmethod;
    private String notes;
    private String date;
    private String time;
    private String user_email;
    private String user_name;

    public Income_Expense_Model(int regId, String income, String expense, Byte cat_img, String category, String paymentmethod, String notes, String date, String time, String user_email) {
        this.regId = regId;
        this.income = income;
        this.expense = expense;
        this.cat_img = cat_img;
        this.category = category;
        this.paymentmethod = paymentmethod;
        this.notes = notes;
        this.date = date;
        this.time = time;
        this.user_email = user_email;
    }

    public Income_Expense_Model(int regId, String income, String expense, String category, String paymentmethod, String notes, String date, String time, String user_email) {
        this.regId = regId;
        this.income = income;
        this.expense = expense;
        this.category = category;
        this.paymentmethod = paymentmethod;
        this.notes = notes;
        this.date = date;
        this.time = time;
        this.user_email = user_email;
    }

    public Income_Expense_Model( String user_name, String user_email) {
        this.user_name = user_name;
        this.user_email = user_email;

    }

    public Income_Expense_Model(String user_email) {
        this.user_email = user_email;

    }

    public Income_Expense_Model(int regId, String income, String expense, String category, String paymentmethod, String notes, String date, String time) {
        this.regId = regId;
        this.income = income;
        this.expense = expense;
        this.category = category;
        this.paymentmethod = paymentmethod;
        this.notes = notes;
        this.date = date;
        this.time = time;
    }

    public Income_Expense_Model(int regId, String income, String expense, String category, String paymentmethod, String date) {
        this.regId = regId;
        this.income = income;
        this.expense = expense;
        this.category = category;
        this.paymentmethod = paymentmethod;
        this.date = date;
    }


    public Income_Expense_Model( int income, String paymentmethod) {
        this.income = String.valueOf(income);
        this.paymentmethod = paymentmethod;
    }

    public Income_Expense_Model(String category, String income, String expense) {
        this.income = income;
        this.expense = expense;
        this.category = category;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public int getRegId() {
        return regId;
    }

    public void setRegId(int regId) {
        this.regId = regId;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
