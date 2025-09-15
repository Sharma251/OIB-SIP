<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.reservation.reservation.model.TrainDetails" %>
<!DOCTYPE html>
<html>
<head>
    <title>Booking History</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bookingHistoryStyle.css">
</head>
<body>
<div class="container">
    <h2>Booking History</h2>

    <%
        List<TrainDetails> bookings = (List<TrainDetails>) request.getAttribute("bookings");
        if (bookings == null || bookings.isEmpty()) {
    %>
        <p>No bookings found.</p>
    <%
        } else {
    %>
        <table>
            <thead>
                <tr>
                    <th>PNR</th>
                    <th>Train No</th>
                    <th>Train Name</th>
                    <th>From</th>
                    <th>To</th>
                    <th>Date</th>
                    <th>Passenger</th>
                    <th>Age</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (TrainDetails b : bookings) {
                %>
                <tr>
                    <td><%= b.getPnrNumber() %></td>
                    <td><%= b.gettrainNumber() %></td>
                    <td><%= b.getTrainName() %></td>
                    <td><%= b.getOrigin() %></td>
                    <td><%= b.getDestination() %></td>
                    <td><%= b.getJourneyDate() %></td>
                    <td><%= b.getPassengerName() %></td>
                    <td><%= b.getPassengerAge() %></td>
                    <td><%= b.getStatus() %></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <%
        }
    %>
</div>
</body>
</html>
