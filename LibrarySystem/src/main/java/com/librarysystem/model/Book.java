package com.librarysystem.model;

public class Book {
    private String title, author, category;
    private int stock, bookId;

    public Book(int bookId, String title, String author, String category, int stock) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.stock = stock;
    }
    public int getBookId() {
        return bookId;
    }
    public String getTitle() {
        return title;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getAuthor() {
        return author;
    }
    public String getCategory() {
        return category;
    }
    public int getStock(){
        return stock;
    }

}
