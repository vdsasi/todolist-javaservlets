import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vdsas
 */
public class CompleteTaskServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String task = request.getParameter("task");
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost/todolist?characterEncoding=utf8","root","");
            String query = "DELETE FROM incompletedtasks WHERE name='" + username + "' AND task='" + task + "'";
            PreparedStatement statement = con.prepareStatement(query);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new Exception("No rows deleted");
            }
            query = "INSERT INTO completedtasks(name, task) VALUES('" + username + "','" + task + "')";
            statement = con.prepareStatement(query);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new Exception("No rows inserted");
            }
            response.sendRedirect("content.html");
        }
        catch(Exception e) {
            PrintWriter out = response.getWriter();
            out.println("Error: " + e.getMessage());
        }
    }
}
