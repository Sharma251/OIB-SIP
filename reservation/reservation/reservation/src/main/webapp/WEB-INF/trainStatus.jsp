<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Train Status</title>
    <link rel="stylesheet" href="/trainStatusStyle.css"></head>
<body>
<div class="container">
    <h2>Train Status Info</h2>
    <pre>${trainData}</pre>
    <p>Train: ${trainInfo.get("data").get("train_name").asText()}</p>
	<p>Status: ${trainInfo.get("data").get("status").asText()}</p>
    
</div>
</body>
</html>


