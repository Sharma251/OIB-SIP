<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Online Reservation System</title>
  <link rel="stylesheet" href="dashboardStyle.css">
</head>
<body>
  <div class="container">
    <h2>Online Reservation System</h2>
    <div class="grid">
      <a class="card" href="/trainSearchForm">
        <img src="images/calendar.png" alt="Book Ticket">
        <span>Book Ticket</span>
      </a>
      <a class="card" href="/bookingHistory">
        <img src="images/history.png" alt="Booking History">
        <span>Booking History</span>
      </a>
      <a class="card" href="/cancelTicket">
        <img src="images/cancel.png" alt="Cancel Ticket">
        <span>Cancel Ticket</span>
      </a>
      <a class="card" href="/profile">
        <img src="images/user.png" alt="View Profile">
        <span>View Profile</span>
      </a>
      <a class="card" href="/pnrStatusForm">
        <img src="images/pnr.png" alt="PNR Status">
        <span>PNR Status</span>
      </a>
      <a class="card" href="/cancelledHistory">
        <img src="images/refund.png" alt="Canceled History">
        <span>Canceled History</span>
      </a>
      <a class="card download-ticket" href="/downloadTicket">
        <img src="images/download.png" alt="Download Ticket">
        <span>Download Ticket</span>
      </a>
    </div>
    <button class="logout" onclick="window.location.href='/logout'">LOGOUT</button>
  </div>
</body>
</html>
