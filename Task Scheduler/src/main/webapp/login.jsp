<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.sql.*" %>
<html>
<body>
<%
  String pwd = request.getParameter("pass");
  String phone = request.getParameter("contact");

  try {
    Class.forName("com.mysql.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tasks?allowPublicKeyRetrieval=true&useSSL=false", "root", "Ganga@123");

    String query = "SELECT * FROM users WHERE uphone = ? AND upwd = ?";
    PreparedStatement stmt = con.prepareStatement(query);
    stmt.setString(1, phone);
    stmt.setString(2, pwd);

    ResultSet rs = stmt.executeQuery();
    if (rs.next()) {
      // Set the contact number as an attribute in the request object
      session.setAttribute("contactNumber", phone);

      // Redirect to Main.jsp
      response.sendRedirect("Main.jsp");
    } else {
      out.println("<html><b><center><h1>Username or Password is wrong</h1></center></b></html>");
    }

    con.close();
  } catch (Exception e) {
    out.println(e.getMessage());
  }
%>
</body>
</html>