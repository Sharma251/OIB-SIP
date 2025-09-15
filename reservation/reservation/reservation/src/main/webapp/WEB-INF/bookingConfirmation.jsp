<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Booking Confirmation</title>
    <link rel="stylesheet" href="bookingConfirmationStyle.css"></head>
<body>
<div class="container">
    <div class="confirmation-card">
        <h2>Booking Confirmed</h2>
        <p class="success-msg">Your ticket has been successfully booked!</p>

        <table>
            <tbody>
                <tr>
                    <th>PNR</th>
                    <td>${pnr}</td>
        		</tr>
                <tr>
                    <th>Train Number</th>
                    <td>${booking.trainNumber}</td>
                </tr>
                <tr>
                    <th>Train Name</th>
                    <td>${booking.trainName}</td>
                </tr>
                <tr>
                    <th>Journey Date</th>
                    <td>${booking.journeyDate}</td>
                </tr>
                <tr>
                    <th>Passenger Name</th>
                    <td>${booking.passengerName}</td>
		        </tr>
		         <tr>
                    <th>Passenger Age</th>
                    <td>${booking.passengerAge}</td>
		        </tr>
                <tr>
                    <th>From</th>
                    <td>${booking.origin}</td>
                </tr>
                <tr>
                    <th>To</th>
                    <td>${booking.destination}</td>
                </tr>
                 <tr>
                    <th>Class</th>
                    <td>${booking.classType}</td>
		        </tr>
            </tbody>
        </table>
<a href="/" class="btn">Back to Home</a>
</div>
</body>
</html>