package com.libraryManagement.LibraryManagement.AuthController;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.libraryManagement.LibraryManagement.entity.Book;
import com.libraryManagement.LibraryManagement.entity.DeletedBook;
import com.libraryManagement.LibraryManagement.entity.User;
import com.libraryManagement.LibraryManagement.service.BookService;
import com.libraryManagement.LibraryManagement.service.TransactionService;
import com.libraryManagement.LibraryManagement.service.UserService;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    // Show all books with search functionality
    @GetMapping
    public String listBooks(Model model, HttpSession session,
                           @RequestParam(value = "q", required = false) String query,
                           @RequestParam(value = "category", required = false) String category) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<Book> books;
        if (query != null && !query.trim().isEmpty()) {
            // Search by title or author
            books = bookService.searchBooks(query.trim());
        } else if (category != null && !category.trim().isEmpty()) {
            // Filter by category
            books = bookService.getBooksByCategory(category.trim());
        } else {
            // Get all books
            books = bookService.getAllBooks();
        }

        model.addAttribute("books", books);
        model.addAttribute("query", query);
        model.addAttribute("category", category);
        model.addAttribute("categories", bookService.getDistinctCategories());
        return "books"; // JSP: books.jsp
    }

    // Browse books (same as listBooks but different endpoint for user dashboard)
    @GetMapping("/browseBook")
    public String browseBooks(Model model, HttpSession session,
                              @RequestParam(value = "q", required = false) String query,
                              @RequestParam(value = "category", required = false) String category) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<Book> books;

        // Fetch deleted book IDs to exclude
        List<DeletedBook> deletedBooks = bookService.getAllDeletedBooks();
        List<Long> deletedBookIds = deletedBooks.stream()
            .map(DeletedBook::getOriginalBookId)
            .toList();

        if (category != null && !category.trim().isEmpty()) {
            // Filter by category, exclude deleted books and quantity > 0
            books = bookService.getBooksByCategory(category.trim()).stream()
                .filter(book -> !deletedBookIds.contains(book.getId()) && book.getQuantity() > 0)
                .toList();
        } else if (query != null && !query.trim().isEmpty()) {
            // Search by title or author with partial match, exclude deleted books and quantity > 0
            books = bookService.searchBooks(query.trim()).stream()
                .filter(book -> !deletedBookIds.contains(book.getId()) && book.getQuantity() > 0)
                .toList();
        } else {
            // Get all books excluding deleted and quantity > 0
            books = bookService.getAllBooks().stream()
                .filter(book -> !deletedBookIds.contains(book.getId()) && book.getQuantity() > 0)
                .toList();
        }

        model.addAttribute("books", books);
        model.addAttribute("query", query);
        model.addAttribute("category", category);
        model.addAttribute("categories", bookService.getDistinctCategories());
        return "browseBook"; // JSP: books.jsp
    }
    
    // Add book page
    @GetMapping("/add")
    public String showAddBookForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.ADMIN)) {
            return "redirect:/login";
        }
        model.addAttribute("book", new Book());
        return "addBook"; // JSP: addBook.jsp
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.ADMIN)) {
            return "redirect:/login";
        }
        bookService.addBook(book);
        return "redirect:/books/add";
    }

    // Edit book
    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.ADMIN)) {
            return "redirect:/login";
        }
        Optional<Book> book = bookService.getBookById(id);
        model.addAttribute("book", book.orElseThrow(() -> new RuntimeException("Book not found")));
        return "editBook"; // JSP: editBook.jsp
    }
    @PostMapping("/edit")
    public String editBook(@ModelAttribute Book book, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.ADMIN)) {
            return "redirect:/login";
        }
        // Ensure the book exists before updating
        Optional<Book> existingBook = bookService.getBookById(book.getId());
        if (existingBook.isPresent()) {
            Book bookToUpdate = existingBook.get();
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setAuthor(book.getAuthor());
            bookToUpdate.setCategory(book.getCategory());
            bookToUpdate.setStatus(book.getStatus());
            bookToUpdate.setQuantity(book.getQuantity());
            bookService.updateBook(bookToUpdate);
        }
        return "redirect:/books";
    }

    // Delete book (soft delete)
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.ADMIN)) {
            return "redirect:/login";
        }
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    // Show deleted books page (admin only)
    @GetMapping("/deletedBooks")
    public String showDeletedBooks(Model model, HttpSession session,
                                   @RequestParam(value = "q", required = false) String query,
                                   @RequestParam(value = "category", required = false) String category,
                                   @RequestParam(value = "author", required = false) String author,
                                   @RequestParam(value = "page", defaultValue = "0") Integer page,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.ADMIN)) {
            return "redirect:/login";
        }

        // For simplicity, fetch all deleted books and filter in memory
        List<DeletedBook> allDeletedBooks = bookService.getAllDeletedBooks();
        List<DeletedBook> filteredBooks = allDeletedBooks.stream()
            .filter(book -> query == null || query.isEmpty() ||
                           book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                           book.getAuthor().toLowerCase().contains(query.toLowerCase()))
            .filter(book -> category == null || category.isEmpty() || category.equals(book.getCategory()))
            .filter(book -> author == null || author.isEmpty() ||
                           book.getAuthor().toLowerCase().contains(author.toLowerCase()))
            .collect(java.util.stream.Collectors.toList());

        // Simple pagination
        int totalBooks = filteredBooks.size();
        int start = page * size;
        int end = Math.min(start + size, totalBooks);
        List<DeletedBook> pageBooks = filteredBooks.subList(start, end);

        model.addAttribute("deletedBooks", pageBooks);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalBooks / size));
        model.addAttribute("query", query);
        model.addAttribute("category", category);
        model.addAttribute("author", author);
        model.addAttribute("categories", bookService.getDistinctCategories());
        return "deletedBooks"; // JSP page to be created
    }

    // Restore deleted book
    @PostMapping("/restore/{id}")
    public String restoreBook(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.ADMIN)) {
            return "redirect:/login";
        }
        bookService.restoreBook(id);
        return "redirect:/books/deletedBooks";
    }

    // Issue book
    @PostMapping("/issue/{bookId}/user/{userId}")
    public String issueBook(@PathVariable Long bookId, @PathVariable Long userId) {
        Book book = bookService.getBookById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        transactionService.issueBook(book, user);
        return "redirect:/books";
    }

    // Return book
    @PostMapping("/return/{transactionId}")
    public String returnBook(@PathVariable Long transactionId) {
        transactionService.returnBook(transactionId);
        return "redirect:/books";
    }



}
