package com.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/tasks")
public class TaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // db configuration
    private String url = "jdbc:mysql://localhost:3306/taskdb";
    private String username = "root";
    private String password = "siva@1234"; 

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        // get request parameters
        String employeeId = request.getParameter("employeeId");
        String employeeName = request.getParameter("employeeName");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String status = request.getParameter("status"); 

        try {
            // load database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            
            // insert query
            String query = "INSERT INTO tasks (employee_id, employee_name, title, description, status) VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, employeeId);
            pstmt.setString(2, employeeName);
            pstmt.setString(3, title);
            pstmt.setString(4, description);
            pstmt.setString(5, status); 
            
            int rowsInserted = pstmt.executeUpdate();
            
            if (rowsInserted > 0) {
                out.print("{\"status\":\"success\", \"message\":\"Task saved successfully!\"}");
            }
            
            pstmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            out.print("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
        }
    }
}