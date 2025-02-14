package com.example.demo.configuration;

import com.example.demo.model.Book;
import com.example.demo.model.User;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;


@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadBookData(BookRepository bookRepository) {
        return args -> {
            if (bookRepository.count() == 0) { // Only insert if empty
                LinkedList<Book> books = new LinkedList<>();
                try (BufferedReader br = new BufferedReader(new FileReader("/home/igor-czudy/code/demo (3)/demo/src/main/java/com/example/demo/configuration/archive/book30-listing-test.csv"))) {
                    br.readLine(); // Skip the first line (header row)
                    String line;
                    int i=0;
                    while (i<30){//((line = br.readLine()) != null) {
                        i+=1;
                        line = br.readLine();
                        String[] values = line.split(";");
                        if (values.length >= 5) {
                            String author = values[4];
                            String title = values[3];
                            String imgURL = values[2];
                            if (author.isEmpty() || title.isEmpty() || imgURL.isEmpty()){
                                continue;
                            }

                            books.add(new Book(author, title, imgURL));
                        } else {
                            System.err.println("Skipping invalid row: " + Arrays.toString(values));
                        }

                    }
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
            if (userRepository.count() == 0) { // Only insert if empty
                userRepository.save(new User("User", passwordEncoder.encode("User")));
            }
        };
    }
}
