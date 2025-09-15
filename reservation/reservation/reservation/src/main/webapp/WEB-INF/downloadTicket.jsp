<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Download Ticket</title>
     <link rel="stylesheet" href="downloadTicketStyle.css">
<body>
    <div class="container">
        <div class="header">
            <h1>🎫 Download Ticket</h1>
            <p>Logged in as: <strong><%= request.getAttribute("username") %></strong></p>
        </div>
        
        <div class="content">
            <div class="search-section">
                <h3>🔍 Search for Your Ticket</h3>
                <form action="${pageContext.request.contextPath}/downloadTicket" method="post">
                    <div class="form-group">
                        <label for="pnrNumber">Enter PNR Number:</label>
                        <input type="text" id="pnrNumber" name="pnrNumber" 
                               placeholder="Enter your PNR number" required>
                    </div>
                    <button type="submit" class="search-btn">Search Ticket</button>
                </form>
            </div>

            <%
                String success = (String) request.getAttribute("success");
                if (success != null && !success.isEmpty()) {
            %>
                <div class="message success"><%= success %></div>
            <%
                }
            %>

            <%
                String error = (String) request.getAttribute("error");
                if (error != null && !error.isEmpty()) {
            %>
                <div class="message error"><%= error %></div>
            <%
                }
            %>

            <%
                Object ticket = request.getAttribute("ticket");
                if (ticket != null) {
                    // Use reflection to access ticket properties from TrainDetails model
                    try {
                        Class<?> ticketClass = ticket.getClass();
                        java.lang.reflect.Method getPnrNumber = ticketClass.getMethod("getPnrNumber");
                        java.lang.reflect.Method getUserId = ticketClass.getMethod("getUserId");
                        java.lang.reflect.Method getTrainNumber = ticketClass.getMethod("gettrainNumber");
                        java.lang.reflect.Method getTrainName = ticketClass.getMethod("getTrainName");
                        java.lang.reflect.Method getOrigin = ticketClass.getMethod("getOrigin");
                        java.lang.reflect.Method getDestination = ticketClass.getMethod("getDestination");
                        java.lang.reflect.Method getJourneyDate = ticketClass.getMethod("getJourneyDate");
                        java.lang.reflect.Method getPassengerName = ticketClass.getMethod("getPassengerName");
                        java.lang.reflect.Method getPassengerAge = ticketClass.getMethod("getPassengerAge");
                        java.lang.reflect.Method getGender = ticketClass.getMethod("getGender");
                        java.lang.reflect.Method getClassType = ticketClass.getMethod("getClassType");
                        java.lang.reflect.Method getSeatPreference = ticketClass.getMethod("getSeatPreference");
                        java.lang.reflect.Method getStatus = ticketClass.getMethod("getStatus");
                        
                        String pnrNumber = (String) getPnrNumber.invoke(ticket);
                        Long userId = (Long) getUserId.invoke(ticket);
                        String trainNumber = (String) getTrainNumber.invoke(ticket);
                        String trainName = (String) getTrainName.invoke(ticket);
                        String origin = (String) getOrigin.invoke(ticket);
                        String destination = (String) getDestination.invoke(ticket);
                        String journeyDate = (String) getJourneyDate.invoke(ticket);
                        String passengerName = (String) getPassengerName.invoke(ticket);
                        Integer passengerAge = (Integer) getPassengerAge.invoke(ticket);
                        String gender = (String) getGender.invoke(ticket);
                        String classType = (String) getClassType.invoke(ticket);
                        String seatPreference = (String) getSeatPreference.invoke(ticket);
                        String status = (String) getStatus.invoke(ticket);
            %>
                <div class="ticket-section">
                    <div class="ticket-header">
                        <h2>🚂 Train Ticket</h2>
                        <p>PNR: <strong><%= pnrNumber %></strong></p>
                    </div>
                    
                    <div class="ticket-details">
                        <div class="ticket-detail">
                            <strong>Train Number:</strong>
                            <span><%= trainNumber != null ? trainNumber : "N/A" %></span>
                        </div>
                        <div class="ticket-detail">
                            <strong>Train Name:</strong>
                            <span><%= trainName != null ? trainName : "N/A" %></span>
                        </div>
                        <div class="ticket-detail">
                            <strong>From Station:</strong>
                            <span><%= origin != null ? origin : "N/A" %></span>
                        </div>
                        <div class="ticket-detail">
                            <strong>To Station:</strong>
                            <span><%= destination != null ? destination : "N/A" %></span>
                        </div>
                        <div class="ticket-detail">
                            <strong>Journey Date:</strong>
                            <span><%= journeyDate != null ? journeyDate : "N/A" %></span>
                        </div>
                        <div class="ticket-detail">
                            <strong>Passenger Name:</strong>
                            <span><%= passengerName != null ? passengerName : "N/A" %></span>
                        </div>
                        <div class="ticket-detail">
                            <strong>Passenger Age:</strong>
                            <span><%= passengerAge != null ? passengerAge.toString() : "N/A" %></span>
                        </div>
                        <div class="ticket-detail">
                            <strong>Gender:</strong>
                            <span><%= gender != null ? gender : "N/A" %></span>
                        </div>
                        <div class="ticket-detail">
                            <strong>Class Type:</strong>
                            <span><%= classType != null ? classType : "N/A" %></span>
                        </div>
                        <div class="ticket-detail">
                            <strong>Seat Preference:</strong>
                            <span><%= seatPreference != null ? seatPreference : "N/A" %></span>
                        </div>
                        <div class="ticket-detail">
                            <strong>Status:</strong>
                            <span><%= status != null ? status : "N/A" %></span>
                        </div>
                    </div>
                    
                    <div class="download-actions">
                        <button class="download-btn" onclick="downloadTicket()">📥 Download PDF</button>
                        <button class="print-btn" onclick="window.print()">🖨️ Print Ticket</button>
                    </div>
                </div>
            <%
                    } catch (Exception e) {
                        // Handle reflection errors
            %>
                <div class="message error">Error displaying ticket details.</div>
            <%
                    }
                }
            %>

            <div class="back-to-dashboard">
                <a href="${pageContext.request.contextPath}/">← Back to Dashboard</a>
            </div>
        </div>
    </div>

    <script>
        function downloadTicket() {
            // This is a placeholder for PDF download functionality
            // In a real application, you would implement actual PDF generation
            alert('PDF download functionality would be implemented here.\nFor now, you can use the Print button to save as PDF.');
        }
    </script>
</body>
</html>
