package com.librarysystem.UI;

import com.librarysystem.books.Book;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class ProBook {
    private SimpleStringProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty author;
    private SimpleStringProperty category;
    private SimpleIntegerProperty stock;

    ProBook(String id,String title, String author, String category, int stock) {
        this.id = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.category = new SimpleStringProperty(category);
        this.stock = new SimpleIntegerProperty(stock);
    }

    public static ArrayList<ProBook> bookToBind(ArrayList<Book> ary) {
        ArrayList<ProBook> e = new ArrayList<>();
        for (Book b : ary){
            ProBook j = new ProBook(b.getBookId(),b.getTitle(),b.getAuthor(),b.getCategory(),b.getStock());
            e.add(j);
        }
        return e;
    }

    //getters
    public String getId() {
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
    //sset
    public void setId(String id) {
        this.id.set(id);
    }
    public void setTitle(String title) {
        this.title.set(title);
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


    public SimpleStringProperty idProperty() {
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
}
