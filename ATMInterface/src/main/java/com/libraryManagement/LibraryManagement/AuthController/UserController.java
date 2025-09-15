package com.libraryManagement.LibraryManagement.AuthController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.libraryManagement.LibraryManagement.entity.Book;
import com.libraryManagement.LibraryManagement.entity.Reservation;
import com.libraryManagement.LibraryManagement.entity.Transaction;
import com.libraryManagement.LibraryManagement.entity.User;
import com.libraryManagement.LibraryManagement.service.TransactionService;
import com.libraryManagement.LibraryManagement.service.UserService;
import com.libraryManagement.LibraryManagement.service.BookService;
import com.libraryManagement.LibraryManagement.service.ReservationService;
import com.libraryManagement.LibraryManagement.service.QueryService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private QueryService queryService;

    // User dashboard
    @GetMapping("/userDashboard")
    public String dashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.USER)) {
            return "redirect:/login";
        }

        // Get user's transactions (issued books)
        List<Transaction> allTransactions = transactionService.getAllTransactions();
        List<Transaction> userTransactions = allTransactions.stream()
            .filter(t -> t.getUser().getId().equals(user.getId()) && t.getReturnDate() == null)
            .collect(Collectors.toList());

        // Get user's reservations (books reserved but not issued)
        List<Reservation> userReservations = reservationService.getUserPendingReservations(user);

        // Calculate overdue transactions
        List<Transaction> overdueTransactions = userTransactions.stream()
            .filter(t -> t.getDueDate() != null &&
                        t.getDueDate().isBefore(java.time.LocalDate.now()))
            .collect(Collectors.toList());

        model.addAttribute("user", user);
        model.addAttribute("transactions", userTransactions);
        model.addAttribute("reservations", userReservations);
        model.addAttribute("overdueCount", overdueTransactions.size());

        return "userDashboard"; // JSP: userDashboard.jsp
    }

    @GetMapping("/reservations")
    public String reservations(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.USER)) {
            return "redirect:/login";
        }

        // Get user's reservations
        List<Reservation> userReservations = reservationService.getUserPendingReservations(user);

        model.addAttribute("user", user);
        model.addAttribute("reservations", userReservations);

        return "reservations"; // JSP: reservations.jsp
    }

    @GetMapping("/myIssues")
    public String myIssues(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.USER)) {
            return "redirect:/login";
        }

        // Get user's active transactions (issued books not yet returned)
        List<Transaction> allTransactions = transactionService.getAllTransactions();
        List<Transaction> userTransactions = allTransactions.stream()
            .filter(t -> t.getUser().getId().equals(user.getId()) && t.getReturnDate() == null)
            .collect(Collectors.toList());

        model.addAttribute("user", user);
        model.addAttribute("transactions", userTransactions);

        return "myIssues"; // JSP: myIssues.jsp
    }

    @GetMapping("/issueBook")
    public String issueBook(@RequestParam(value = "bookId", required = false) Long bookId,
                           @RequestParam(value = "reservationId", required = false) Long reservationId,
                           Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Book book = null;

        if (reservationId != null) {
            // Coming from reservations page
            Optional<Reservation> reservationOpt = reservationService.getReservationById(reservationId);
            if (reservationOpt.isPresent()) {
                Reservation reservation = reservationOpt.get();
                // Verify the reservation belongs to the current user
                if (!reservation.getUser().getId().equals(user.getId())) {
                    return "redirect:/user/reservations";
                }
                book = reservation.getBook();
            } else {
                return "redirect:/user/reservations";
            }
        } else if (bookId != null) {
            // Coming from browse books page
            Optional<Book> bookOpt = bookService.getBookById(bookId);
            if (bookOpt.isPresent()) {
                book = bookOpt.get();
            } else {
                return "redirect:/books/browseBook";
            }
        } else {
            return "redirect:/books/browseBook";
        }

        model.addAttribute("book", book);
        model.addAttribute("user", user);

        return "issueBook"; // JSP page to issue the book
    }

    @PostMapping("/confirmIssue")
    public String confirmIssue(@RequestParam("bookId") Long bookId,
                              @RequestParam("userId") Long userId,
                              @RequestParam("issueDate") String issueDateStr,
                              @RequestParam("dueDate") String dueDateStr,
                              @RequestParam("status") String status,
                              HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getId().equals(userId)) {
            return "redirect:/login";
        }

        try {
            Optional<Book> bookOpt = bookService.getBookById(bookId);
            Optional<User> userOpt = userService.getUserById(userId);

            if (bookOpt.isPresent() && userOpt.isPresent()) {
                Book book = bookOpt.get();
                User issuingUser = userOpt.get();

                // Check if book is available for issuing
                if (bookService.isBookAvailableForIssue(book, issuingUser)) {
                    // Parse dates
                    java.time.LocalDate issueDate = java.time.LocalDate.parse(issueDateStr);
                    java.time.LocalDate dueDate = java.time.LocalDate.parse(dueDateStr);

                    // Issue the book with custom dates
                    transactionService.issueBookWithDates(book, issuingUser, issueDate, dueDate);

                    return "redirect:/user/userDashboard";
                }
            }
        } catch (Exception e) {
            // Handle parsing errors or other exceptions
            return "redirect:/books/browseBook";
        }

        return "redirect:/books/browseBook";
    }

    @GetMapping("/returnBook")
    public String returnBook(@RequestParam("transactionId") Long transactionId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.USER)) {
            return "redirect:/login";
        }

        try {
            // Find the transaction
            Optional<Transaction> transactionOpt = transactionService.getTransactionById(transactionId);
            if (transactionOpt.isPresent()) {
                Transaction transaction = transactionOpt.get();

                // Verify the transaction belongs to the current user
                if (!transaction.getUser().getId().equals(user.getId())) {
                    return "redirect:/user/myIssues";
                }

                // Add transaction details to model for the return form
                model.addAttribute("transaction", transaction);
                model.addAttribute("user", user);

                return "returnBook"; // Show return confirmation page
            } else {
                return "redirect:/user/myIssues";
            }
        } catch (Exception e) {
            // Handle errors (transaction not found, etc.)
            return "redirect:/user/myIssues";
        }
    }

    @PostMapping("/processReturn")
    public String processReturn(@RequestParam("issueId") Long issueId,
                               @RequestParam("bookId") Long bookId,
                               @RequestParam("fine") long fine,
                               @RequestParam("returnDate") String returnDateStr,
                               @RequestParam("condition") String condition,
                               HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.USER)) {
            return "redirect:/login";
        }

        try {
            // If fine = 0, process return immediately
            if (fine == 0) {
                Transaction returnedTransaction = transactionService.returnBook(issueId);
                return "redirect:/user/myIssues";
            } else {
                // If fine > 0, redirect to payment page
                session.setAttribute("pendingReturnTransactionId", issueId);
                session.setAttribute("pendingReturnFine", fine);
                return "redirect:/user/payment";
            }
        } catch (Exception e) {
            // Handle errors
            return "redirect:/user/myIssues";
        }
    }

    @GetMapping("/payment")
    public String payment(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.USER)) {
            return "redirect:/login";
        }

        // Check if there's a pending payment
        if (session.getAttribute("pendingReturnTransactionId") == null) {
            return "redirect:/user/myIssues";
        }

        return "payment"; // JSP: payment.jsp
    }

    @PostMapping("/processPayment")
    public String processPayment(@RequestParam("transactionId") Long transactionId,
                                @RequestParam("fineAmount") long fineAmount,
                                @RequestParam("cardNumber") String cardNumber,
                                @RequestParam("expiryDate") String expiryDate,
                                @RequestParam("cvv") String cvv,
                                @RequestParam("cardHolderName") String cardHolderName,
                                HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.USER)) {
            return "redirect:/login";
        }

        try {
            // Simulate payment processing
            // In a real application, this would integrate with a payment gateway

            // Process the return after successful payment
            Transaction returnedTransaction = transactionService.returnBook(transactionId);

            // Clear session attributes
            session.removeAttribute("pendingReturnTransactionId");
            session.removeAttribute("pendingReturnFine");

            return "redirect:/user/myIssues";
        } catch (Exception e) {
            // Handle payment errors
            return "redirect:/user/payment";
        }
    }

    // Reserve a book
    @PostMapping("/reserveBook")
    public String reserveBook(@RequestParam("bookId") Long bookId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.USER)) {
            return "redirect:/login";
        }

        try {
            Optional<Book> bookOpt = bookService.getBookById(bookId);
            if (bookOpt.isPresent()) {
                Book book = bookOpt.get();
                if (bookService.isBookAvailableForReservation(book, user)) {
                    reservationService.reserveBook(book, user);
                }
                return "redirect:/books/browseBook";
            }
        } catch (Exception e) {
            // Handle reservation errors
            return "redirect:/books/browseBook";
        }

        return "redirect:/books/browseBook";
    }

    // Cancel reservation
    @PostMapping("/cancelReservation")
    public String cancelReservation(@RequestParam("reservationId") Long reservationId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.USER)) {
            return "redirect:/login";
        }

        try {
            reservationService.cancelReservation(reservationId, user);
        } catch (Exception e) {
            // Handle errors
        }

        return "redirect:/user/reservations";
    }

    // Issue a reserved book
    @PostMapping("/issueReservedBook")
    public String issueReservedBook(@RequestParam("reservationId") Long reservationId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.USER)) {
            return "redirect:/login";
        }

        try {
            reservationService.issueReservedBook(reservationId, user);
        } catch (Exception e) {
            // Handle errors
        }

        return "redirect:/user/reservations";
    }

    // Query page
    @GetMapping("/query")
    public String query(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.USER)) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "query"; // JSP: query.jsp
    }

    // Submit query
    @PostMapping("/submitQuery")
    public String submitQuery(@RequestParam("subject") String subject,
                             @RequestParam("message") String message,
                             HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.USER)) {
            return "redirect:/login";
        }

        try {
            queryService.submitQuery(user, subject, message);
            // Add success message to session
            session.setAttribute("querySuccess", "Your query has been submitted successfully!");
        } catch (Exception e) {
            // Add error message to session
            session.setAttribute("queryError", "Failed to submit query. Please try again.");
        }

        return "redirect:/user/query";
    }
}
