package com.librarysystem.model;

public class BorrowRecord {
    private String title;
    private String borrowDate;
    private int id;
    private String status;
    private String borrower;

    public BorrowRecord(String title, String borrowDate) {
        this.title = title;
        this.borrowDate = borrowDate;
    }
    public BorrowRecord(int id, String borrower,String title, String status) {
        this.title = title;
        this.borrower = borrower;
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getBorrower() {
        return borrower;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
