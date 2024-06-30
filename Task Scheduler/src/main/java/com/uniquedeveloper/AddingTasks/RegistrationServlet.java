package com.uniquedeveloper.AddingTasks;
import java.io.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException{
        res.setContentType("text/html");
        PrintWriter pw=res.getWriter();
        String uname=req.getParameter("name");
        String uemail=req.getParameter("email");
        String upwd=req.getParameter("pass");
        String uphone=req.getParameter("contact");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
           Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/tasks?allowPublicKeyRetrieval=true&useSSL=false","root","Ganga@123");
            String q="insert into users(uname,upwd,uemail,uphone) values(?,?,?,?)";
            PreparedStatement stm=con.prepareStatement(q);
            stm.setString(1,uname);
            stm.setString(2,upwd);
            stm.setString(3,uemail);
            stm.setString(4,uphone);
            int x=stm.executeUpdate();
            if(x>0){
                res.sendRedirect("login.html");
            }
            else{
                pw.println("<html>Register not Successful</html>");
            }
		con.close();
        }
        catch(Exception e){
            pw.println(e.getMessage());
        }
	}}
