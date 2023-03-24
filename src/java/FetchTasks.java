import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class FetchTasks extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
PrintWriter out = response.getWriter();
        try {
            Class.forName("com.mysql.jdbc.Driver");  
            HttpSession session = request.getSession(false);
            String username = (String) session.getAttribute("username");
             Connection con=DriverManager.getConnection("jdbc:mysql://localhost/todolist?characterEncoding=utf8","root","");  

            String query = "SELECT * FROM incompletedtasks where name = " + username;
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<JSONObject> tasks = new ArrayList<>();

            while (resultSet.next()) {
                JSONObject task = new JSONObject();
                    task.put("id", resultSet.getInt("id"));
                    task.put("text", resultSet.getString("task"));
                    task.put("completed", false);
                    tasks.add(task);
                
            }


            query = "SELECT * FROM completedtasks where name=" + username;
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                JSONObject task = new JSONObject();
                
                task.put("id", resultSet.getInt("id"));
                task.put("text", resultSet.getString("task"));
                task.put("completed", true);
                tasks.add(task);
            }

            JSONArray jsonArray = new JSONArray(tasks);
            response.setContentType("application/json");

            response.getWriter().write(jsonArray.toString());

            resultSet.close();
            statement.close();
            con.close();
        } catch (Exception e) {
out.println("ERRor");
        }
    }
}
