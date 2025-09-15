package com.libraryManagement.LibraryManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.libraryManagement.LibraryManagement.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Search by category
    List<Book> findByCategory(String category);

    // Search by author
    List<Book> findByAuthor(String author);

    // Search by status (AVAILABLE, ISSUED, RESERVED)
    List<Book> findByStatus(Book.Status status);

    // Search by title containing (for search bar)
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Search by title or author containing (for search bar)
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(@Param("query") String query);

    // Get distinct categories
    @Query("SELECT DISTINCT b.category FROM Book b WHERE b.category IS NOT NULL ORDER BY b.category")
    List<String> findDistinctCategories();
}
