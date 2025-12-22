package com.attendance.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import com.attendance.util.DBConnection;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("DEBUG: Login Attempt - Username: " + username + ", Password: " + password);
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("user", username);
                response.sendRedirect("admin.html");
            } else {
                response.sendRedirect("index.html?error=Invalid credentials");
            }
        } catch (Exception e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body><h2>Error Processing Login</h2>");
            out.println(
                    "<p style='color:red;'>Make sure you have downloaded <b>mysql-connector-j.jar</b> and placed it in <b>WebContent/WEB-INF/lib/</b>.</p>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre></body></html>");
        }
    }
}
