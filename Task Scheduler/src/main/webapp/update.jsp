<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Task</title>
    <style>
        body {
            background-color: #f2f2f2;
            font-family: Arial, sans-serif;
            padding: 20px;
        }

        h1 {
            color: #333333;
        }

        form {
            background-color: #ffffff;
            border-radius: 5px;
            padding: 20px;
        }

        label {
            display: block;
            margin-bottom: 10px;
            color: #666666;
            font-weight: bold;
        }

        input[type="text"],
        input[type="datetime-local"] {
            width: 100%;
            padding: 10px;
            border-radius: 4px;
            border: 1px solid #cccccc;
            margin-bottom: 15px;
        }

        input[type="submit"] {
            background-color: #4caf50;
            color: #ffffff;
            border: none;
            padding: 12px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }
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

.navbar a:hover, .dropdown:hover .dropbtn {
  background-color: red;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
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
  <a href="Main.html">Home</a> 
</div>
    <h1>Update Task</h1>

    <form action="UpdateServlet" method="GET">
        <input type="hidden" name="taskId" value="<%= request.getParameter("id") %>">
         <label for="updatedTaskName">Updated Task Name:</label>
        <input type="text" name="updatedTaskName" required><br>
        <label for="updatedScheduledTime">Updated Scheduled Time:</label>
        <input type="datetime-local" name="updatedScheduledTime" required><br>
        <input type="submit" value="Update Task">
    </form>
</body>
</html>
