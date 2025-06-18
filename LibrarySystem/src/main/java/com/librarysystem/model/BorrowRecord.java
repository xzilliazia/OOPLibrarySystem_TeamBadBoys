package com.librarysystem.model;

public class BorrowRecord {
    private String title;
    private String borrowDate;

    public BorrowRecord(String title, String borrowDate) {
        this.title = title;
        this.borrowDate = borrowDate;
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
}
