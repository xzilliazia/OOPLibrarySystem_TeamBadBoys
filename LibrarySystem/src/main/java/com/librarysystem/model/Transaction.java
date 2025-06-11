package com.librarysystem.model;


import java.time.LocalDate;

public abstract class Transaction {
    private String transactionId;
    private String userId;
    private String itemId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LocalDate dueDate;

    public String getTransactionId() {
        return transactionId;
    }
    public String getUserId() {
        return userId;
    }
    public String getItemId() {
        return itemId;
    }
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
