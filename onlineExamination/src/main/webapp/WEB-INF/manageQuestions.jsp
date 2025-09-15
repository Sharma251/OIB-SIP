<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.onlineExamination.onlineExamination.entity.Exam" %>
<%@ page import="com.onlineExamination.onlineExamination.entity.Question" %>
<%@ page import="com.onlineExamination.onlineExamination.entity.Option" %>

<!DOCTYPE html>
<html>
<head>
    <title>
        <%
            Exam exam = (Exam) request.getAttribute("exam");
            if (exam != null) {
                out.print("Manage Questions for " + exam.getTitle());
            } else {
                out.print("Manage Questions");
            }
        %>
    </title>
    <link rel="stylesheet" href="/manageQuestionStyle.css">
</head>
<body>
<div class="container mt-3">

    <%
        List<Exam> exams = (List<Exam>) request.getAttribute("exams");
        List<Question> questions = (List<Question>) request.getAttribute("questions");
        String errorMessage = (String) request.getAttribute("errorMessage");
    %>

    <% if (errorMessage != null) { %>
        <h3 style="color:red;"><%= errorMessage %></h3>
    <% } %>

    <% if (exam != null) { %>
        <h3>Questions for Exam: <%= exam.getTitle() %></h3>

        <!-- Add Question -->
        <form action="/questions/add" method="post" class="mb-3">
            <input type="hidden" name="examId" value="<%= exam.getId() %>">
            <input type="text" name="text" placeholder="Question text" class="form-control mb-2" required>
            <button type="submit" class="btn btn-success">Add Question</button>
        </form>

        <!-- Questions List -->
        <% if (questions != null && !questions.isEmpty()) { %>
            <% for (Question q : questions) { %>
                <div class="card mb-2">
                    <div class="card-body">
                        <strong>Q<%= q.getId() %>:</strong> <%= q.getText() %>
                        
                        <!-- DELETE QUESTION -->
                        <form action="/questions/delete/<%= q.getId() %>" method="get" style="display:inline;">
                            <button type="submit" class="btn btn-danger btn-sm float-end">Delete</button>
                        </form>

                        <!-- Add Option -->
                        <form action="/options/add" method="post" class="mt-2">
                            <input type="hidden" name="questionId" value="<%= q.getId() %>">
                            <input type="text" name="text" placeholder="Option text" required>
                            <label>
                                <input type="checkbox" name="isCorrect"> Correct
                            </label>
                            <button type="submit" class="btn btn-primary btn-sm">Add Option</button>
                        </form>

                        <!-- Display Options -->
                        <ul>
                            <%
                                List<Option> options = q.getOptions();
                                if (options != null && !options.isEmpty()) {
                                    for (Option opt : options) {
                            %>
                                <li>
                                    <%= opt.getOptionText() %>
                                    <% if (opt.isCorrect()) { %> 
                                        <span style="color:green;">(Correct)</span> 
                                    <% } %>

                                    <!-- DELETE OPTION -->
                                    <form action="/options/delete/<%= opt.getId() %>" method="get" style="display:inline;">
                                        <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                    </form>
                                </li>
                            <%
                                    }
                                } else {
                            %>
                                <li><em>No options added yet</em></li>
                            <% } %>
                        </ul>
                    </div>
                </div>
            <% } %>
        <% } else { %>
            <p><em>No questions available for this exam.</em></p>
        <% } %>

    <% } else if (exams != null && !exams.isEmpty()) { %>
        <h3>Select an Exam to Manage Questions</h3>
        <ul>
        <% for (Exam e : exams) { %>
            <li><a href="/questions/<%= e.getId() %>"><%= e.getTitle() %></a> - <%= e.getDescription() %></li>
        <% } %>
        </ul>
    <% } else { %>
        <h3>No exams available.</h3>
    <% } %>

</div>
</body>
</html>
