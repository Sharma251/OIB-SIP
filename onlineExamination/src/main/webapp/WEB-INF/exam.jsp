<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.onlineExamination.onlineExamination.entity.Question" %>
<%@ page import="com.onlineExamination.onlineExamination.entity.Option" %>
<%@ page import="com.onlineExamination.onlineExamination.entity.Exam" %>
<%
    Exam exam = (Exam) request.getAttribute("exam");
    List<Question> questions = (List<Question>) request.getAttribute("questions");
    Object durationObj = request.getAttribute("duration");
    Object totalDurationObj = request.getAttribute("totalDuration");
    int timeLeft = 60;
    int totalTime = 3600; // default 1 hour
    if (durationObj instanceof Integer) {
        timeLeft = ((Integer) durationObj).intValue();
    } else if (durationObj instanceof Long) {
        timeLeft = ((Long) durationObj).intValue();
    }
    if (totalDurationObj instanceof Integer) {
        totalTime = ((Integer) totalDurationObj).intValue();
    } else if (totalDurationObj instanceof Long) {
        totalTime = ((Long) totalDurationObj).intValue();
    }
%>
<html>
<head>
    <title>Exam<%= (exam != null) ? ": " + exam.getTitle() : "" %></title>
    <link rel="stylesheet" href="/examStyle.css">
    <script>
        var duration = <%= timeLeft %>;
        var totalDuration = <%= totalTime %>;
        var endTime = new Date().getTime() + duration * 1000;

        function updateTimer() {
            var now = new Date().getTime();
            var timeLeft = Math.floor((endTime - now) / 1000);

            if (timeLeft <= 0) {
                document.getElementById("timer").innerHTML = "0m 0s";
                document.getElementById("progress").style.width = "0%";
                document.getElementById("examForm").submit();
            } else {
                document.getElementById("timer").innerHTML =
                    Math.floor(timeLeft / 60) + "m " + (timeLeft % 60) + "s";
                var progress = (timeLeft / totalDuration) * 100;
                document.getElementById("progress").style.width = progress + "%";
                setTimeout(updateTimer, 1000);
            }
        }
        window.onload = updateTimer;
    </script>
</head>
<body>
<div class="container">
    <form action="/logout" method="get" style="text-align: right; margin-bottom: 10px;">
        <button type="submit" class="btn">Logout</button>
    </form>
    <%
        if (exam == null) {
    %>
        <h2>Error: Exam not found</h2>
        <p>The requested exam could not be loaded. Please contact the administrator.</p>
    <%
        } else {
    %>
    <h2 class="exam-title">Exam: <%= exam.getTitle() %></h2>
    <div id="timer" class="timer"></div>
    <div class="progress-bar">
        <div id="progress" class="progress-fill"></div>
    </div>
    <form id="examForm" action="/exam/submit/<%= exam.getId() %>" method="post" class="exam-form">
        <input type="hidden" name="examId" value="<%= exam.getId() %>"/>
        <%
            if (questions != null && !questions.isEmpty()) {
                for (Question q : questions) {
        %>
            <div class="question-card">
                <p><b>Q<%= q.getId() %>: </b><%= q.getText() %></p>
                <%
                    if (q.getOptions() != null) {
                        for (Option opt : q.getOptions()) {
                %>
                    <label>
                        <input type="radio" name="q_<%= q.getId() %>" value="<%= opt.getOptionText() %>" required>
                        <%= opt.getOptionText() %>
                    </label><br/>
                <%
                        }
                    }
                %>
            </div>
        <%
                }
            } else {
        %>
            <p>No questions available for this exam.</p>
        <%
            }
        %>
        <button type="submit" class="btn">Submit Exam</button>
    </form>
    <%
        }
    %>
</div>
</body>
</html>
