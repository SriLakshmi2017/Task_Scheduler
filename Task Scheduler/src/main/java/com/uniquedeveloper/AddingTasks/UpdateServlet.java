package com.uniquedeveloper.AddingTasks;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	private static final String UPDATE_QUERY = "UPDATE taskadder SET tname = ?, stime = ? WHERE taskid = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Retrieve the taskId from the request parameters
        int taskId = Integer.parseInt(req.getParameter("taskId"));
        //String phonenum=req.getParameter("phone");

        // Retrieve the updated task name and scheduled time from the request parameters
        String updatedTaskName = req.getParameter("updatedTaskName");
        String updatedScheduledTime = req.getParameter("updatedScheduledTime");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tasks?allowPublicKeyRetrieval=true&useSSL=false", "root", "Ganga@123");

            PreparedStatement stm = con.prepareStatement(UPDATE_QUERY);
            stm.setString(1, updatedTaskName);
            stm.setString(2, updatedScheduledTime);
            //stm.setString(3, phonenum);
            stm.setInt(3, taskId);

            int count = stm.executeUpdate();

            if (count == 1) {
		out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"style1.css\" /></head><body>");
		out.println("<div class='navbar'>");
  		out.println("<a href='Main.html'>Home</a>");
  		out.println("</div>");		
     out.println("<h2>Record updated</h2>");
	//	out.println("<form action='DisplayServlet'>");
          //  out.println("<input type='submit' value='Display Tasks'>");
            //out.println("</form>");
		res.sendRedirect("DisplayServlet");
            } else {
                out.println("<h2>Record not updated</h2>");
            }

            con.close();
        } catch (SQLException se) {
            se.printStackTrace();
            out.println("<h1>Error: " + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h1>Error: " + e.getMessage() + "</h1>");
        }
    }
}