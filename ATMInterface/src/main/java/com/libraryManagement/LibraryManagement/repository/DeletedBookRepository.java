package com.libraryManagement.LibraryManagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.libraryManagement.LibraryManagement.entity.DeletedBook;

public interface DeletedBookRepository extends JpaRepository<DeletedBook, Long> {

    // Search by title containing (for search bar)
    Page<DeletedBook> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // Search by category
    Page<DeletedBook> findByCategory(String category, Pageable pageable);

    // Search by author
    Page<DeletedBook> findByAuthor(String author, Pageable pageable);

    // Search by status
	/*
	 * Page<DeletedBook> findByStatus(DeletedBook.Status status, Pageable pageable);
	 */
    // Combined search
    @Query("SELECT d FROM DeletedBook d WHERE " +
           "(:title IS NULL OR LOWER(d.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:category IS NULL OR d.category = :category) AND " +
           "(:author IS NULL OR LOWER(d.author) LIKE LOWER(CONCAT('%', :author, '%')))")
    Page<DeletedBook> findWithFilters(@Param("title") String title,
                                      @Param("category") String category,
                                      @Param("author") String author,
                                      Pageable pageable);
}
