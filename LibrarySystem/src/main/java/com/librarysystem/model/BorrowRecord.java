package com.librarysystem.model;

public class BorrowRecord {
    private String title;
    private String borrowDate;
    private int id;
    private String borrower;
    private BorrowStatus status;

    // Constructor for simple borrow info (e.g. history view)
    public BorrowRecord(String title, String borrowDate) {
        this.title = title;
        this.borrowDate = borrowDate;
    }

    public BorrowRecord(String title, String borrowDate, String status) {
        this.title = title;
        this.borrowDate = borrowDate;
        this.status = BorrowStatus.valueOf(status);  // convert String to BorrowStatus
    }


    // Constructor for detailed record (e.g. admin panel)
    public BorrowRecord(int id, String borrower, String title, BorrowStatus status) {
        this.id = id;
        this.borrower = borrower;
        this.title = title;
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getBorrower() {
        return borrower;
    }

    public BorrowStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public void setStatus(BorrowStatus status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }
}
