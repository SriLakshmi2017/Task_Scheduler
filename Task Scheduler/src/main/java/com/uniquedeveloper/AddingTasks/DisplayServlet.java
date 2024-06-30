
package com.uniquedeveloper.AddingTasks;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession();
    	String phoneNumber=session.getAttribute("phoneNumber").toString();

        // Check if delete parameter is present
        String deleteId = req.getParameter("delete");

        if (deleteId != null) {
            // Delete the task
            int taskId = Integer.parseInt(deleteId);
            boolean deleteStatus = deleteTask(taskId);
            if (deleteStatus) {
                out.println("<div class='container-fluid'><div class='alert alert-success alert-dismissible fade show'>");
                out.println("<button type='button' class='btn-close' data-bs-dismiss=alert></button>");
                out.println("<strong>Record is Deleted Successfully</strong></div></div>");
            } else {
                req.setAttribute("deleteStatus", "Record is not deleted Successfully");
            }
        }

        out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'/>");
        out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js' integrity='sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p' crossorigin='anonymous'></script></head><body>");
        out.println("<div class='container'>");
        out.println("<h1><center>Tasks</center></h1>");
        out.println("<hr>");
        out.println("<script>");
        out.println("function funtime(time, taskIndex) {");
        out.println("var countDownDate = new Date(time).getTime();");
        out.println("var x = setInterval(function() {");
        out.println("var now = new Date().getTime();");
        out.println("var distance = countDownDate - now;");
        out.println("var days = Math.floor(distance / (1000 * 60 * 60 * 24));");
        out.println("var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));");
        out.println("var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));");
        out.println("var seconds = Math.floor((distance % (1000 * 60)) / 1000);");
        out.println("var timer = days + 'd ' + hours + 'h ' + minutes + 'm ' + seconds + 's';");
        out.println("var timerDivs = document.getElementsByName('timer');");
        out.println("if (taskIndex <= timerDivs.length) {");
        out.println("timerDivs[taskIndex-1].innerHTML = timer;");
        out.println("}");
        out.println("if (distance < 0) {");
        out.println("clearInterval(x);");
        out.println("if (taskIndex <= timerDivs.length) {");
        out.println("timerDivs[taskIndex-1].innerHTML = 'Expired';");
        sendSMS(phoneNumber,"your task is due");
        out.println("}");
        out.println("}");
        out.println("}, 1000);");
        out.println("}");
        out.println("</script>");

        displayTasks(out, phoneNumber);

        out.println("</div></body></html>");
    }

    private boolean deleteTask(int taskId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tasks?allowPublicKeyRetrieval=true&useSSL=false", "root", "Ganga@123");
            String deleteQuery = "DELETE FROM taskadder WHERE taskid = ?";
            PreparedStatement stm = con.prepareStatement(deleteQuery);
            stm.setInt(1, taskId);
            int count = stm.executeUpdate();
            con.close();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private void sendSMS(String phoneNumber, String messageBody) {
    	String ACCOUNT_SID = "ACa213a075946031bd5c201ad17addfda5";
        String AUTH_TOKEN = "efb8eaab3229f912cb8f1d70929b163a";
        String TWILIO_PHONE_NUMBER = "+14176474705";

        // Initialize Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Send SMS
        Message message = Message.creator(
                new PhoneNumber(phoneNumber),       // Destination phone number
                new PhoneNumber(TWILIO_PHONE_NUMBER), // Twilio phone number
                messageBody)                         // SMS body
                .create();

        System.out.println("SMS sent! SID: " + message.getSid());
    }

    private void displayTasks(PrintWriter out,String phonenumber) {
        int taskIndex = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tasks?allowPublicKeyRetrieval=true&useSSL=false", "root", "Ganga@123");
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM taskadder where uphone='"+phonenumber+"'");

            while (rs.next()) {
            	taskIndex++;
                out.println("<div class='side'>");
                out.println("<div class='child' id='c1'>");
                out.println(rs.getString(2));
                out.println("</div>");
                LocalDateTime dateTime = LocalDateTime.parse(rs.getString(3));
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss");
                String formattedDate = now.format(myFormatObj);
                String formatDate = dateTime.format(myFormatObj);
                out.println("<div class='child' id='c1' name='timer'></div>");
                out.println("<script>funtime('" + formatDate + "', " + taskIndex + ");</script>");
                out.println("<div>");
                out.println("<input type='hidden' name='id' value='" + rs.getInt(1) + "'/>");
                out.println("<a style='text-decoration:none;' class='button' href='?delete=" + rs.getInt(1) + "'>DELETE</a>");
                out.println("<a style='text-decoration:none;' class='button' href='update.jsp?id=" + rs.getInt(1) + "'>UPDATE</a>");
                out.println("</div></div>");                // Check if the task is expired
                
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


