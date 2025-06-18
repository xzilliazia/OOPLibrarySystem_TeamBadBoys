package com.librarysystem.service;

import com.librarysystem.model.Book;
import com.librarysystem.model.BorrowRecord;
import com.librarysystem.model.BorrowStatus;
import com.librarysystem.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookUtil {

    public static ArrayList<Book> loadBooks() {
        ArrayList<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                bookList.add(new Book(
                        rs.getInt("id"),
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

    /**
     * Loads the list of borrowed books for a specific user.
     */
    public static List<BorrowRecord> getBorrowsForUser(int userId) {
        List<BorrowRecord> list = new ArrayList<>();
        String sql = "SELECT b.title, br.borrow_date, br.status FROM borrowed_books br " +
                "JOIN books b ON br.book_id = b.id " +
                "WHERE br.user_id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new BorrowRecord(
                        rs.getString("title"),
                        rs.getString("borrow_date"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<BorrowRecord> getAllBorrowRecords() {
        List<BorrowRecord> list = new ArrayList<>();
        String sql = "SELECT br.id, u.username AS borrower, b.title, br.status " +
                "FROM borrowed_books br " +
                "JOIN users u ON br.user_id = u.id " +
                "JOIN books b ON br.book_id = b.id";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new BorrowRecord(
                        rs.getInt("id"),
                        rs.getString("borrower"),
                        rs.getString("title"),
                        BorrowStatus.valueOf(rs.getString("status"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static void insertBorrowRecord(int userId, int bookId, BorrowStatus status) {
        String sql = "INSERT INTO borrowed_books (user_id, book_id, borrow_date, status) VALUES (?, ?, CURRENT_DATE, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);
            pstmt.setString(3, status.name());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void saveBook(ArrayList<Book> bookList) {
        String sqlInsert = "INSERT INTO books (title, author, category, stock) VALUES (?, ?, ?, ?) RETURNING id";
        String sqlUpdate = "UPDATE books SET title = ?, author = ?, category = ?, stock = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect()) {
            for (Book book : bookList) {
                if (book.getBookId() == 0) {
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
                        pstmt.setString(1, book.getTitle());
                        pstmt.setString(2, book.getAuthor());
                        pstmt.setString(3, book.getCategory());
                        pstmt.setInt(4, book.getStock());
                        try (ResultSet rs = pstmt.executeQuery()) {
                            if (rs.next()) {
                                book.setBookId(rs.getInt("id"));
                            }
                        }
                    }
                } else {
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
                        pstmt.setString(1, book.getTitle());
                        pstmt.setString(2, book.getAuthor());
                        pstmt.setString(3, book.getCategory());
                        pstmt.setInt(4, book.getStock());
                        pstmt.setInt(5, book.getBookId());
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
            if (String.valueOf(book.getBookId()).equalsIgnoreCase(id)) {

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
            pstmt.setInt(5, updatedBook.getBookId());

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

    public static void updateBookStock(int id, int newStock) {
        String sql = "UPDATE books SET stock = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newStock);
            pstmt.setInt(2, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Stock updated for book ID: " + id);
            } else {
                System.out.println("No book found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBorrowStatus(int borrowId, BorrowStatus status) {
        String sql = "UPDATE borrowed_books SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status.name());
            pstmt.setInt(2, borrowId);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Borrow status updated in DB for ID: " + borrowId);
            } else {
                System.out.println("No borrow record found with ID: " + borrowId);
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
                        rs.getInt("id"),
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
