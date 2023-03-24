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

public class RemoveTaskServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String task = request.getParameter("task");
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/todolist?characterEncoding=utf8", "root", "");
            String query = "DELETE FROM completedtasks WHERE name=? AND task=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, task);
            int rowsDeleted = statement.executeUpdate();
            
            response.sendRedirect("content.html");
        }
        catch(Exception e) {
            PrintWriter out = response.getWriter();
            out.println("Error: " + e.getMessage());
        }
    }
}

