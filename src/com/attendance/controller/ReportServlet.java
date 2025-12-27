package com.attendance.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import com.attendance.model.AttendanceRecord;
import com.attendance.util.DBConnection;

public class ReportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dataParam = request.getParameter("date");
        String date = (dataParam != null && !dataParam.isEmpty()) ? dataParam
                : new java.sql.Date(System.currentTimeMillis()).toString();

        List<AttendanceRecord> records = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT s.id, s.name, s.roll_no, a.id as attendance_id, a.date, a.status " +
                    "FROM students s " +
                    "LEFT JOIN attendance a ON s.id = a.student_id AND a.date = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("attendance_id"); // May be 0 if null
                int studentId = rs.getInt("id");
                String name = rs.getString("name");
                String rollNo = rs.getString("roll_no");
                java.sql.Date attDate = rs.getDate("date");
                if (attDate == null)
                    attDate = java.sql.Date.valueOf(date);

                String status = rs.getString("status");
                if (status == null)
                    status = "Absent";

                records.add(new AttendanceRecord(id, studentId, name, rollNo, attDate, status));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println(
                "<html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Attendance Report</title>");
        out.println("<link rel='stylesheet' href='css/style.css'>");
        out.println(
                "<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");
        out.println("</head><body>");
        out.println("<div class='container' style='max-width: 900px;'>");

        out.println(
                "<div style='display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;'>");
        out.println("<h2>Daily Report</h2>");
        out.println("<form method='GET' style='flex-direction: row; gap: 1rem; align-items: center;'>");
        out.println("<label for='date'>Date:</label>");
        out.println("<input type='date' id='date' name='date' value='" + date + "' onchange='this.form.submit()'>");
        out.println("</form>");
        out.println("</div>");

        out.println("<div class='table-container'><table>");
        out.println("<tr><th>Student Name</th><th>Roll No</th><th>Date</th><th>Status</th></tr>");
        for (AttendanceRecord r : records) {
            String statusColor = "Absent".equals(r.getStatus()) ? "color: #ef4444; font-weight: 600;"
                    : "color: #10b981; font-weight: 600;";
            out.println("<tr><td>" + r.getStudentName() + "</td><td>" + r.getRollNo() + "</td><td>" + r.getDate()
                    + "</td><td style='" + statusColor + "'>" + r.getStatus() + "</td></tr>");
        }
        out.println("</table></div>");
        out.println(
                "<div style='margin-top: 1.5rem; text-align: center;'><a href='admin.html'>Back to Admin Dashboard</a></div>");
        out.println("</div></body></html>");
    }
}
