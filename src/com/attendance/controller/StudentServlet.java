package com.attendance.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import com.attendance.util.DBConnection;

public class StudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String rollNo = request.getParameter("rollNo");

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO students(name, roll_no) VALUES(?, ?)");
            ps.setString(1, name);
            ps.setString(2, rollNo);
            ps.executeUpdate();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println(
                    "<html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Student Added</title>");
            out.println("<link rel='stylesheet' href='css/style.css'>");
            out.println(
                    "<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");
            out.println("</head><body>");
            out.println("<div class='container' style='text-align: center;'>");
            out.println("<h2 style='color: #10b981;'>Success!</h2>");
            out.println("<p>Student <b>" + name + "</b> (" + rollNo + ") added successfully.</p>");
            out.println("<div style='margin-top: 1.5rem;'><a href='admin.html'>Add Another</a></div>");
            out.println("</div></body></html>");

        } catch (Exception e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body><h2 style='color:red'>Error Adding Student</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("<a href='admin.html'>Back</a></body></html>");
            e.printStackTrace();
        }
    }
}
