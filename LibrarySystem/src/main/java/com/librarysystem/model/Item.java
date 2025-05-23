package com.librarysystem.model;

public abstract class Item {
    private String id;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private boolean available;

    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {return author;}
    public String getPublisher() {
        return publisher;
    }
    public int getYear() {
        return year;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public void setYear(int year) {
        this.year = year;
    }

    abstract String getItemName();

}
