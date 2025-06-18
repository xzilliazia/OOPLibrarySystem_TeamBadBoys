package com.librarysystem.UI;

import com.librarysystem.model.Book;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class PropertyBook {
    private SimpleIntegerProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty author;
    private SimpleStringProperty category;
    private SimpleIntegerProperty stock;

    public PropertyBook(int id, String title, String author, String category, int stock) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.category = new SimpleStringProperty(category);
        this.stock = new SimpleIntegerProperty(stock);
    }

    public static ArrayList<PropertyBook> bookToBind(ArrayList<Book> ary) {
        ArrayList<PropertyBook> e = new ArrayList<>();
        for (Book b : ary){
            PropertyBook j = new PropertyBook(b.getBookId(),b.getTitle(),b.getAuthor(),b.getCategory(),b.getStock());
            e.add(j);
        }
        return e;
    }

    //getters
    public int getId() {
        return id.get();
    }
    public String getTitle() {
        return title.get();
    }
    public String getAuthor() {
        return author.get();
    }
    public String getCategory() {
        return category.get();
    }
    public int getStock() {
        return stock.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }
    public void setTitle(String title) {

    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }
    public SimpleStringProperty titleProperty() {
        return title;
    }
    public SimpleStringProperty authorProperty() {
        return author;
    }
    public SimpleStringProperty categoryProperty() {
        return category;
    }
    public SimpleIntegerProperty stockProperty() {
        return stock;
    }

    public Book toBook() {
        return new Book(getId(), getTitle(), getAuthor(), getCategory(), getStock());
    }

}