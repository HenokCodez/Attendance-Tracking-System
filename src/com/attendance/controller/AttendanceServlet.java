package com.attendance.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import com.attendance.util.DBConnection;

public class AttendanceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String rollNo = request.getParameter("code");
        // String code = request.getParameter("code");
        // int studentId = Integer.parseInt(code); <-- Caused Error

        try (Connection con = DBConnection.getConnection()) {
            // 1. Find Student ID from Roll Number
            PreparedStatement psFind = con.prepareStatement("SELECT id FROM students WHERE roll_no = ?");
            psFind.setString(1, rollNo);
            ResultSet rs = psFind.executeQuery();

            if (rs.next()) {
                int studentId = rs.getInt("id");

                // 2. Mark Attendance
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO attendance(student_id,date,status) VALUES(?,CURDATE(),'Present')");
                ps.setInt(1, studentId);
                ps.executeUpdate();

                // Show Success
                response.setContentType("text/html");
                java.io.PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html>");
                out.println(
                        "<html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Attendance Recorded</title>");
                out.println("<link rel='stylesheet' href='css/style.css'>");
                out.println(
                        "<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");
                out.println("</head><body>");
                out.println("<div class='container' style='text-align: center;'>");
                out.println("<h2 style='color: #10b981;'>Success!</h2>");
                out.println("<p>Attendance recorded for Roll No: <b>" + rollNo + "</b></p>");
                out.println("<p style='font-size: 0.9em; color: gray;'>(Student ID: " + studentId + ")</p>");
                out.println("<div style='margin-top: 1.5rem;'><a href='admin.html'>Record Another</a></div>");
                out.println("</div></body></html>");
            } else {
                // Student Not Found
                response.setContentType("text/html");
                java.io.PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html>");
                out.println(
                        "<html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Student Not Found</title>");
                out.println("<link rel='stylesheet' href='css/style.css'>");
                out.println(
                        "<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");
                out.println("</head><body>");
                out.println("<div class='container' style='text-align: center;'>");
                out.println("<h2 style='color: #ef4444;'>Error</h2>");
                out.println("<p>Student with Roll No <b>" + rollNo + "</b> not found.</p>");
                out.println("<div style='margin-top: 1.5rem;'><a href='admin.html'>Try Again</a></div>");
                out.println("</div></body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Show meaningful error to user
            response.setContentType("text/html");
            java.io.PrintWriter out = response.getWriter();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
