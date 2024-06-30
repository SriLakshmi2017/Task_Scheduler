package com.uniquedeveloper.AddingTasks;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/TaskServlet")
public class TaskServlet extends HttpServlet{
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException{
        res.setContentType("text/html");
        PrintWriter pw=res.getWriter();
        String tname=req.getParameter("new-task-input");
	String stime=req.getParameter("scheduledtime");
	String uphone ="+91"+req.getParameter("contact");
	HttpSession session = req.getSession();
	session.setAttribute("phoneNumber",uphone);
	try{
            Class.forName("com.mysql.cj.jdbc.Driver");
           Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/tasks?allowPublicKeyRetrieval=true&useSSL=false","root","Ganga@123");
	    int taskno=0;
            PreparedStatement ps=con.prepareStatement("select max(taskid) from taskadder");
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
		taskno=rs.getInt(1);
		taskno++;

            String q="insert into taskadder values(?,?,?,?)";
            PreparedStatement stm=con.prepareStatement(q);
            stm.setInt(1,taskno); 
           stm.setString(2,tname);
	   stm.setString(3,stime);
	   stm.setString(4,uphone);
            int x=stm.executeUpdate();
	
            	if(x>0){
                res.sendRedirect("DisplayServlet");}
            else{
                pw.println("<html>Register not Successful</html>");
            }
		con.close();
}        }
        catch(Exception e){
            pw.println(e.getMessage());
        }
	}}

	