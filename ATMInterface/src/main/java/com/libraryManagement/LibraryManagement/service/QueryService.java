package com.libraryManagement.LibraryManagement.service;

import com.libraryManagement.LibraryManagement.entity.Query;
import com.libraryManagement.LibraryManagement.entity.User;
import com.libraryManagement.LibraryManagement.repository.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QueryService {

    @Autowired
    private QueryRepository queryRepository;

    // Submit a new query
    public Query submitQuery(User user, String subject, String message) {
        Query query = new Query(user, subject, message);
        return queryRepository.save(query);
    }

    // Get all queries
    public List<Query> getAllQueries() {
        return queryRepository.findAllByOrderByDateDesc();
    }

    // Get queries by user
    public List<Query> getUserQueries(User user) {
        return queryRepository.findByUser(user);
    }

    // Get queries by status
    public List<Query> getQueriesByStatus(Query.Status status) {
        return queryRepository.findByStatus(status);
    }

    // Get query by ID
    public Optional<Query> getQueryById(Long queryId) {
        return queryRepository.findById(queryId);
    }

    // Update query status
    public Query updateQueryStatus(Long queryId, Query.Status status) {
        Optional<Query> opt = queryRepository.findById(queryId);
        if (opt.isEmpty()) {
            throw new RuntimeException("Query not found!");
        }

        Query query = opt.get();
        query.setStatus(status);
        return queryRepository.save(query);
    }

    // Delete query
    public void deleteQuery(Long queryId) {
        if (!queryRepository.existsById(queryId)) {
            throw new RuntimeException("Query not found!");
        }
        queryRepository.deleteById(queryId);
    }
}
