<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.reservation.reservation.model.TrainDetails" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cancelled Ticket History</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/cancelledHistoryStyle.css">
</head>
<body>
<div class="container">
    <h2>Cancelled Ticket History</h2>

    <%
        List<TrainDetails> cancelledTickets = (List<TrainDetails>) request.getAttribute("cancelledTickets");
        if (cancelledTickets == null || cancelledTickets.isEmpty()) {
    %>
        <p>No cancelled tickets found.</p>
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
                    for (TrainDetails ticket : cancelledTickets) {
                %>
                <tr>
                    <td><%= ticket.getPnrNumber() %></td>
                    <td><%= ticket.gettrainNumber() %></td>
                    <td><%= ticket.getTrainName() %></td>
                    <td><%= ticket.getOrigin() %></td>
                    <td><%= ticket.getDestination() %></td>
                    <td><%= ticket.getJourneyDate() %></td>
                    <td><%= ticket.getPassengerName() %></td>
                    <td><%= ticket.getPassengerAge() %></td>
                    <td><%= ticket.getStatus() %></td>
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
