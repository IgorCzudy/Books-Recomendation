package com.example.demo.configuration;

import com.example.demo.model.Book;
import com.example.demo.model.User;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.UserRepository;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;


@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadBookData(BookRepository bookRepository) {
        return args -> {
//            Thread.sleep(5000);


            if (bookRepository.count() == 0) { // Only insert if empty
                LinkedList<Book> books = new LinkedList<>();
                String filePath = "BookRecApp/src/main/resources/archive/books.csv"; // File path

                try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
                    String[] nextLine;
                    int lineCount = 0;

                    // Read up to the first 30 rows
                    while ((nextLine = reader.readNext()) != null && lineCount < 300) {
                        // Print the current line (or process it as needed)

                    if (nextLine[0].equals("book_id")) {
                        // Skip the header row
                        continue;
                    }

                    // Create a new Book object and set values
                    Book book = new Book();
                    Long id = Long.valueOf(nextLine[0]);
                    String author = nextLine[7];
                    String title = nextLine[10];
                    String imgURL = nextLine[21];
                    if (author.isEmpty() || title.isEmpty() || imgURL.isEmpty()) {
                        continue;
                    }

                    books.add(new Book(id, author, title, imgURL));
                        System.out.println(String.join(", ", nextLine));
                        lineCount++;
                    }

                    if (lineCount < 30) {
                        System.out.println("File has less than 30 rows.");
                    }

                } catch (IOException e) {
                    System.err.println("Error reading file: " + e.getMessage());
                }
                bookRepository.saveAll(books);
            }
        };
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadUserData(UserRepository userRepository) {
        return args -> {
//            Thread.sleep(5000);

            if (userRepository.count() == 0) { // Only insert if empty
                userRepository.save(new User("User", passwordEncoder.encode("User")));
            }
        };
    }
}
