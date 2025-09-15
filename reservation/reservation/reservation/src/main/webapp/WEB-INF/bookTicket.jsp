<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book Ticket</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/bookTicketStyle.css" />

 <script>
        function fetchTrainDetails() {
            var trainNumber = document.getElementById("trainNumber").value;
            var journeyDate = document.getElementById("journeyDate").value;
            if(trainNumber && journeyDate) {
                // AJAX or form submit logic
            }
        }
    </script>
    
</head>
<body>
	<div class="container">
		<h2>Book Your Ticket</h2>
		
		<form action="/bookTicket" method="post" class="ticket-form">
			<label for="passengerName">Passenger Name:</label>
            <input type="text" id="passengerName" name="passengerName" required>

            <label for="passengerAge">Age:</label>
            <input type="number" min="1" id="passengerAge" name="passengerAge" required>

            <label for="gender">Gender:</label>
            <select id="gender" name="gender" required>
                <option value="">--Select Gender--</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                 <option value="Other">Other</option>
            </select>

            <label for="fromStation">From Station:</label>
            <input type="text" id="fromStation" name="origin" value="${origin}" readonly>

            <label for="toStation">To Station:</label>
            <input type="text" id="toStation" name="destination" value="${destination}" readonly>

            <label for="journeyDate">Journey Date:</label>
            <input type="date" id="journeyDate" name="journeyDate" value="${param.journeyDate}" onchange="fetchTrainDetails()" required>

            <label for="classType">Class Type:</label>
            <select id="classType" name="classType" required>
            
            	<option value="">--Select Class--</option>
                <option value="Sleeper">Sleeper</option>
                <option value="General">General</option>
                <option value="AC 3 Tier">AC 3 Tier</option>
                <option value="AC 2 Tier">AC 2 Tier</option>
                <option value="AC First Class">AC First Class</option>
            </select>

            <label for="trainNumber">Train Number:</label>
            <input type="text" id="trainNumber" name="trainNumber" value="${trainNumber}" onchange="fetchTrainDetails()" readonly>

            <label for="trainName">Train Name:</label>
            <input type="text" id="trainName" name="trainName" value="${trainName}" onchange="fetchTrainDetails()" readonly>

            <label for="seatPreference">Seat Preference:</label>
            <select id="seatPreference" name="seatPreference">
                <option value="">--No Preference--</option>
                <option value="Window">Window</option>
                <option value="Middle">Middle</option>
                <option value="Aisle">Aisle</option>
            </select>
            
            <label>Status</label>
            <input type="text" name="trainStatus" value="${trainStatus}" readonly>

            <button type="submit" class="btn">Book Ticket</button>
		</form>
	</div>
</body>
</html>