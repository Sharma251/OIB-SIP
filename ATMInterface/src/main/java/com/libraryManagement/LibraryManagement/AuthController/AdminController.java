package com.libraryManagement.LibraryManagement.AuthController;

import jakarta.servlet.http.HttpSession;
import com.libraryManagement.LibraryManagement.entity.User;
import com.libraryManagement.LibraryManagement.entity.Book;
import com.libraryManagement.LibraryManagement.entity.Transaction;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.libraryManagement.LibraryManagement.service.BookService;
import com.libraryManagement.LibraryManagement.service.TransactionService;
import com.libraryManagement.LibraryManagement.service.UserService;
import com.libraryManagement.LibraryManagement.service.QueryService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private QueryService queryService;

    @Autowired
    private TransactionService transactionService;

    // Admin dashboard
    @GetMapping("/adminDashboard")
    public String dashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.ADMIN)) {
            return "redirect:/login";
        }

        // Get data from database
        List<Book> books = bookService.getAllBooks();
        List<Transaction> transactions = transactionService.getAllTransactions();

        // Calculate statistics
        int totalBooks = books.size();
        long issuedToday = transactions.stream()
            .filter(t -> t.getIssueDate() != null && t.getIssueDate().equals(java.time.LocalDate.now()))
            .count();
        long overdues = transactions.stream()
            .filter(t -> t.getDueDate() != null && t.getReturnDate() == null && t.getDueDate().isBefore(java.time.LocalDate.now()))
            .count();
        long reservations = transactions.stream()
            .filter(t -> t.getReturnDate() == null)
            .count();

        // Add to model
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("books", books);
        model.addAttribute("transactions", transactions);
        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("issuedToday", issuedToday);
        model.addAttribute("overdues", overdues);
        model.addAttribute("reservations", reservations);

        return "adminDashboard"; // JSP: adminDashboard.jsp
    }

    // History page showing all transactions
    @GetMapping("/history")
    public String history(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(User.Role.ADMIN)) {
            return "redirect:/login";
        }

        List<Transaction> transactions = transactionService.getAllTransactions();
        model.addAttribute("transactions", transactions);
        model.addAttribute("user", user);

        return "history"; // JSP: history.jsp
    }

      // Reports page
      @GetMapping("/reports")
      public String reports(Model model, HttpSession session) {
          User user = (User) session.getAttribute("user");
          if (user == null || !user.getRole().equals(User.Role.ADMIN)) {
              return "redirect:/login";
          }

          List<com.libraryManagement.LibraryManagement.entity.Query> queries = queryService.getAllQueries();
          model.addAttribute("queries", queries);
          return "reports"; // JSP: reports.jsp
      }

      // Update query status
      @PostMapping("/updateQueryStatus")
      public String updateQueryStatus(@RequestParam("queryId") Long queryId,
                                     @RequestParam("status") String status,
                                     HttpSession session) {
          User user = (User) session.getAttribute("user");
          if (user == null || !user.getRole().equals(User.Role.ADMIN)) {
              return "redirect:/login";
          }

          try {
              com.libraryManagement.LibraryManagement.entity.Query.Status queryStatus =
                  com.libraryManagement.LibraryManagement.entity.Query.Status.valueOf(status);
              queryService.updateQueryStatus(queryId, queryStatus);
          } catch (Exception e) {
              // Handle errors
          }

          return "redirect:/admin/reports";
      }

      // Reservations page
      @GetMapping("/reservations")
      public String reservations(Model model) {
          // Assuming reservations are handled similarly, add logic if needed
          return "reservations"; // JSP: reservations.jsp
      }

      // Issued Books page
      @GetMapping("/issuedBooks")
      public String issuedBooks(Model model, HttpSession session) {
          User user = (User) session.getAttribute("user");
          if (user == null || !user.getRole().equals(User.Role.ADMIN)) {
              return "redirect:/login";
          }

          List<Transaction> allTransactions = transactionService.getAllTransactions();
          List<Transaction> activeTransactions = allTransactions.stream()
              .filter(t -> t.getReturnDate() == null)
              .collect(Collectors.toList());
          model.addAttribute("transactions", activeTransactions);

          return "issuedBooks"; // JSP: issuedBooks.jsp
      }

}
