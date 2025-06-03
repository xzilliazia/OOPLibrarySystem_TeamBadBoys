package com.librarysystem.service;

import com.librarysystem.books.Book;

import java.io.*;
import java.util.ArrayList;

public class BookUtil {
    private static final String FILE_NAME = "data/books.csv";

    public static void saveBook(ArrayList<Book> bookList) {
        try (BufferedWriter wr = new BufferedWriter(new FileWriter(FILE_NAME))) {
            wr.write("id,title,author,category,stock");
            wr.newLine();

            for (Book book : bookList) {
                wr.write(String.format("%s,%s,%s,%s,%d",
                        book.getBookId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getCategory(),
                        book.getStock()));
                wr.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Book> loadBooks() {
        ArrayList<Book> bookList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            reader.readLine(); // Lewati header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 5) {
                    String id = parts[0];
                    String title = parts[1];
                    String author = parts[2];
                    String category = parts[3];
                    int stock = Integer.parseInt(parts[4]);

                    Book book = new Book(id, title, author, category, stock);
                    bookList.add(book);
                }
            }
            System.out.println("Loaded sucessfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } return bookList;
    }
}