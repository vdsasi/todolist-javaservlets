/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vdsas
 */
public class NewTask extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String task = request.getParameter("task");
    HttpSession session = request.getSession(false);
    String username = (String) session.getAttribute("username");
    PrintWriter out = response.getWriter();
    
    Connection connection = null;
    PreparedStatement statement = null;
    
    try {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost/todolist?characterEncoding=utf8", "root", "");
        String query = "INSERT INTO incompletedtasks (name, task) VALUES (?, ?)";
        statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, task);
        statement.executeUpdate();
        
        
response.sendRedirect("content.html");
    } catch (Exception e) {
        out.println("Error adding task: " + e.getMessage());
    } 
    }

    

}
