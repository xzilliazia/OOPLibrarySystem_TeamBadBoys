package com.librarysystem.model;

public class Book {
    private String bookId, title, author, category;
    private int stock;

    public Book(String bookId, String title, String author,String category, int stock) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.stock = stock;
    }
    public String getBookId() {
        return bookId;
    }
    public String getTitle() {
        return title;
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
