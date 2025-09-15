<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.reservation.reservation.model.TrainDetails" %>
<%@ page import="com.reservation.reservation.model.Schedule" %>
<%@ page import="java.net.URLEncoder" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Train Search Results</title>
<link rel="stylesheet" href="trainListStyle.css">
</head>
<body>
<div class="container">
    <h2>Available Trains</h2>

    <%
        List<TrainDetails> trains = (List<TrainDetails>) request.getAttribute("trains");
        if (trains != null && !trains.isEmpty()) {
            for (TrainDetails t : trains) {
    %>
        <div>
            <h3><%= t.getTrainName() %> (<%= t.gettrainNumber() %>)</h3>
            <p>From: <%= t.getOrigin() %> → To: <%= t.getDestination() %></p>

            <!-- BOOK NOW BUTTON -->
            <p>
                <a href="${pageContext.request.contextPath}/bookTicket/<%= t.gettrainNumber() %>
                   ?trainName=<%= URLEncoder.encode(t.getTrainName(), "UTF-8") %>
                   &origin=<%= URLEncoder.encode(t.getOrigin(), "UTF-8") %>
                   &destination=<%= URLEncoder.encode(t.getDestination(), "UTF-8") %>">
                   Book Now
                </a>
            </p>

            <table border="1" cellpadding="5" cellspacing="0">
                <thead>
                    <tr>
                        <th>Station Name</th>
                        <th>Arrival</th>
                        <th>Departure</th>
                        <th>Distance (km)</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Schedule s : t.getSchedule()) {
                    %>
                    <tr>
                        <td><%= s.getStationName() %></td>
                        <td><%= s.getArrivalTime() %></td>
                        <td><%= s.getDepartureTime() %></td>
                        <td><%= s.getDistance() %></td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
        <hr/>
    <%
            }
        } else {
    %>
        <p>No trains found for your search.</p>
    <%
        }
    %>
</div>
</body>
</html>
