<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.reservation.reservation.model.TrainDetails" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PNR Status</title>
  <link rel="stylesheet" href="/pnrStatusStyle.css">
</head>
<body>
<div class="container">
 <h2>PNR Status Details</h2>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <p style="color:red;"><%= error %></p>
    <%
        } else {
            TrainDetails booking = (TrainDetails) request.getAttribute("booking");
            if (booking != null) {
    %>
        <table border="1">
        
        <tr><th>PNR Number</th><td><%= booking.getPnrNumber() %></td></tr>
            <tr><th>Train Number</th><td><%= booking.gettrainNumber() %></td></tr>
            <tr><th>Train Name</th><td><%= booking.getTrainName() %></td></tr>
            <tr><th>Origin</th><td><%= booking.getOrigin() %></td></tr>
            <tr><th>Destination</th><td><%= booking.getDestination() %></td></tr>
            <tr><th>Journey Date</th><td><%= booking.getJourneyDate() %></td></tr>
            <tr><th>Passenger Name</th><td><%= booking.getPassengerName() %></td></tr>
            <tr><th>Passenger Age</th><td><%= booking.getPassengerAge() %></td></tr>
            <tr><th>Status</th><td><%= booking.getStatus() %></td></tr>
        </table>
    <%
            } else {
    %>
        <p>No details found for this PNR.</p>
    <%
            }
        }
    %>

    <p><a href="pnrStatusForm">Check another PNR</a></p>
</div>     
</body>
</html>