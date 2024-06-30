<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Task List 2021</title>

	<link rel="stylesheet" href="main.css" />
	<style>
		.navbar {
			overflow: hidden;
			background-color: #333;
		}

		.navbar a {
			float: left;
			font-size: 16px;
			color: white;
			text-align: center;
			padding: 14px 16px;
			text-decoration: none;
		}

		.dropdown {
			float: left;
			overflow: hidden;
		}

		.dropdown .dropbtn {
			font-size: 16px;
			border: none;
			outline: none;
			color: white;
			padding: 14px 16px;
			background-color: inherit;
			font-family: inherit;
			margin: 0;
		}

		.navbar a:hover,
		.dropdown:hover .dropbtn {
			background-color: red;
		}

		.dropdown-content {
			display: none;
			position: absolute;
			background-color: #f9f9f9;
			min-width: 160px;
			box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
			z-index: 1;
		}

		.dropdown-content a {
			float: none;
			color: black;
			padding: 12px 16px;
			text-decoration: none;
			display: block;
			text-align: left;
		}

		.dropdown-content a:hover {
			background-color: #ddd;
		}

		.dropdown:hover .dropdown-content {
			display: block;
		}
	</style>
</head>
<body>
	<div class="navbar">
		<a href="Main.jsp">Home</a>
	</div>

	<header>
		<h1>Task List</h1>
		<form id="new-task-form" action="TaskServlet" method="POST">
			<input 
				type="text" 
				name="new-task-input" 
				id="new-task-input" 
				placeholder="What do you have planned?" />
			<input
				type="datetime-local"
				id="new-task-input"
				name="scheduledtime"
				placeholder="Enter Scheduled Time"
				required/>
			<% String contactNumber = session.getAttribute("contactNumber").toString(); %>
			<input type="hidden" name="contact" value="<%= contactNumber %>">			
			<input 
				type="submit"
				id="new-task-submit" 
				value="Add task" />
			</form>
	</header>
</body>
</html>
