package com.librarysystem.service;

import com.librarysystem.model.Book;
import com.librarysystem.util.DatabaseConnection;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class BookUtil {

    public static ArrayList<Book> loadBooks() {
        ArrayList<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                bookList.add(new Book(
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public static void saveBook(ArrayList<Book> bookList) {
        String sqlInsert = "INSERT INTO books (title, author, category, stock) " +
                "VALUES (?, ?, ?, ?) RETURNING id";

        String sqlUpdate = "UPDATE books SET title = ?, author = ?, category = ?, stock = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect()) {
            for (Book book : bookList) {
                if (book.getBookId() == null || book.getBookId().isEmpty()) {
                    // INSERT new book
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
                        pstmt.setString(1, book.getTitle());
                        pstmt.setString(2, book.getAuthor());
                        pstmt.setString(3, book.getCategory());
                        pstmt.setInt(4, book.getStock());

                        try (ResultSet rs = pstmt.executeQuery()) {
                            if (rs.next()) {
                                String generatedId = String.valueOf(rs.getInt("id"));
                                book.setBookId(generatedId); // Assign new ID back to the book
                            }
                        }
                    }
                } else {
                    // UPDATE existing book
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
                        pstmt.setString(1, book.getTitle());
                        pstmt.setString(2, book.getAuthor());
                        pstmt.setString(3, book.getCategory());
                        pstmt.setInt(4, book.getStock());
                        pstmt.setInt(5, Integer.parseInt(book.getBookId()));
                        pstmt.executeUpdate();
                    }
                }
            }

            System.out.println("Books saved to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Book findBookById(String id) {
        ArrayList<Book> books = loadBooks();
        for (Book book : books) {
            if (book.getBookId().equalsIgnoreCase(id)) {
                return book;
            }
        }
        return null;
    }


    public static void updateBook(Book updatedBook) {
        String sql = "UPDATE books SET title = ?, author = ?, category = ?, stock = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, updatedBook.getTitle());
            pstmt.setString(2, updatedBook.getAuthor());
            pstmt.setString(3, updatedBook.getCategory());
            pstmt.setInt(4, updatedBook.getStock());
            pstmt.setLong(5, Long.parseLong(updatedBook.getBookId()));

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Book updated.");
            } else {
                System.out.println("Book ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteBook(String idOrTitle) {
        String sql = "DELETE FROM books WHERE id::text = ? OR LOWER(title) = LOWER(?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idOrTitle);
            pstmt.setString(2, idOrTitle);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Book(s) deleted.");
            } else {
                System.out.println("No matching book found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Book> searchBooksByTitle(String keyword) {
        ArrayList<Book> result = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE LOWER(title) LIKE ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                result.add(new Book(
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}