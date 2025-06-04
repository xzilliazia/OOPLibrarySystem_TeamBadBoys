package com.librarysystem.service;

import com.librarysystem.books.Book;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

public class BookUtil {
    private static final String FILE_NAME = "data/books.csv";

    public static void saveBook(ArrayList<Book> bookList) {
        try (BufferedWriter wr = new BufferedWriter(new FileWriter(FILE_NAME))) {
            //header file
            wr.write("id,title,author,category,stock");
            wr.newLine();
            //data
            for (Book book : bookList) {
                wr.write(String.format("%s,%s,%s,%s,%d",
                        book.getBookId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getCategory(),
                        book.getStock()));
                wr.newLine();
            }
            System.out.println("Book Saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Book> loadBooks() {
        ArrayList<Book> bookList = new ArrayList<>();
        File file = new File(FILE_NAME);
        //antisipasi file kosong/tidak aadd

        if (!file.exists()) return bookList;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            reader.readLine(); // Lewati header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);

                if (parts.length == 5) {
                    String id = parts[0];
                    String title = parts[1];
                    String author = parts[2];
                    String category = parts[3];
                    int stock = Integer.parseInt(parts[4]);

                    bookList.add( new Book(id, title, author, category, stock));
                }
            }
            System.out.println("Loaded sucessfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } return bookList;
    }

    public static void updateBook(Book updatedBook) {
        ArrayList<Book> books = loadBooks();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookId().equals(updatedBook.getBookId())) {
                books.set(i, updatedBook);
                saveBook(books);
                System.out.println("Book Updated!");
                return;
            }
        }
        System.out.println("Book id not found!");
    }

    public static void deleteBook(String bookId) {
        ArrayList<Book> books = loadBooks();
        boolean removed = books.removeIf(book -> book.getBookId().equals(bookId));
        if (removed) {
            saveBook(books);
            System.out.println("Book deleted!");
        } else {
            System.out.println("Book ID not found to delete.");
        }
    }

    public static ArrayList<Book> searchBooksByTitle(String keyword) {
        ArrayList<Book> books = loadBooks();
        ArrayList<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
}