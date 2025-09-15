package com.libraryManagement.LibraryManagement.repository;

import com.libraryManagement.LibraryManagement.entity.Query;
import com.libraryManagement.LibraryManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryRepository extends JpaRepository<Query, Long> {

    // Find all queries by user
    List<Query> findByUser(User user);

    // Find all queries by status
    List<Query> findByStatus(Query.Status status);

    // Find all queries ordered by date (newest first)
    List<Query> findAllByOrderByDateDesc();

    // Find queries by user and status
    List<Query> findByUserAndStatus(User user, Query.Status status);
}
