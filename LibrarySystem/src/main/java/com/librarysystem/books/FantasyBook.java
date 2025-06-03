package com.librarysystem.books;

public class FantasyBook extends Book {
    private String category = "Fantasy";
    public FantasyBook(String bookId, String title, String author, int stock) {
        super(bookId, title, author, "Fantasy", stock);

    }
    @Override
    public String getCategory() {
        return category;
    }
}
