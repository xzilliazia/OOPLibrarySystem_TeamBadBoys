package com.librarysystem.books;

public class FantasyBook extends Book {
    private String category = "Fantasy";
    public FantasyBook(String bookId, String title, String author, int stock) {
        super(bookId, title, author, stock);
        super.setCategory(category);
    }
    @Override
    public String getCategory() {
        return category;
    }
}
