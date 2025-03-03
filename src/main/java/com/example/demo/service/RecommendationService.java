package com.example.demo.service;

import com.example.demo.model.Book;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;


@Service
public class RecommendationService {
    @Autowired
    BookService bookService;


    public List<Book> getRecomendation(List<Long> highRatedBookIds) throws IOException {
        String filePath = "BookRecApp/src/main/resources/archive/book_similarity_matrix.csv";


        List<List<Float>> dataList = new ArrayList<>();
        List<Long> bookIds = new ArrayList<>(); // To store the book IDs (from first column)

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstRowSkipped = false;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if ("book_id".equals(values[0])){//skip first row
                    continue;
                }

                bookIds.add(Long.valueOf(values[0])); // Save book_id from first column
                List<Float> row = new ArrayList<>();
                for (int i=1; i < values.length; i++){//skip first column
                    row.add(Float.valueOf(values[i]));
                }
                dataList.add(row);
                }
            }

        List<Long> booksInMatrix = highRatedBookIds.stream()
                .filter(book -> bookIds.contains(book))
                .collect(Collectors.toList());

        if (booksInMatrix.isEmpty()) {
            System.out.println("No books found in the similarity matrix!");
            return List.of();
        }

        // Step 2: Calculate the average similarity score for each book
        Map<Long, Float> averageSimilarities = new HashMap<>();
        for (int i = 0; i < dataList.size(); i++) {
            Long bookId = bookIds.get(i);
            if (booksInMatrix.contains(bookId)) continue; // Skip the already rated books

            float totalSimilarity = 0;
            int count = 0;

            // Iterate over the books in the matrix and accumulate the similarity scores
            for (Long ratedBookId : booksInMatrix) {
                int ratedBookIndex = bookIds.indexOf(ratedBookId);
                totalSimilarity += dataList.get(i).get(ratedBookIndex);
                count++;
            }

            // Calculate the average similarity
            if (count > 0) {
                averageSimilarities.put(bookId, totalSimilarity / count);
            }
        }

        // Step 3: Sort the books by similarity score in descending order
        List<Map.Entry<Long, Float>> sortedEntries = new ArrayList<>(averageSimilarities.entrySet());
        sortedEntries.sort((entry1, entry2) -> Float.compare(entry2.getValue(), entry1.getValue()));

        // Step 4: Get the top N recommendations
        List<Long> recommendedBooks = new ArrayList<>();
        int topN = 5; // Modify if needed
        for (int i = 0; i < topN && i < sortedEntries.size(); i++) {
            recommendedBooks.add(sortedEntries.get(i).getKey());
        }

        System.out.println(recommendedBooks);

        return bookService.findBooksByIds(recommendedBooks);
    }

}
