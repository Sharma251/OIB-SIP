<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Check PNR</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/pnrStatusFormStyle.css" />
</head>
<body>
<div class="container">
<h2>Check Your PNR Status</h2>
    <form action="pnrStatus" method="get">
        <label for="pnr">Enter PNR Number:</label>
        <input type="text" id="pnr" name="pnr" required>
        <button type="submit">Check Status</button>
    </form>
</div>
</body>
</html>