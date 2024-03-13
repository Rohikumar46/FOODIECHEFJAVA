

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
/**
 * Servlet implementation class Reciep
 */
@WebServlet("/Reciep")
public class Reciep extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reciep() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Retrieve form data
		String dishName = request.getParameter("dishName");
		System.out.print(dishName);
		if (dishName == null || dishName.isEmpty()) {
		    // Handle the case where dishName is null or empty
		    // For example, you could display an error message to the user
		    response.getWriter().print("Error: Dish name cannot be empty.");
		    return; // Exit the service method to prevent further execution
		}

		// Proceed with the rest of the servlet code
	        String ingredients = request.getParameter("ingredients");
	        String procedureText = request.getParameter("procedure");
	        String timeDurationStr = request.getParameter("timeDuration");
	        int timeDuration = 0; // Default value
	        if (timeDurationStr != null && !timeDurationStr.isEmpty()) {
	            timeDuration = Integer.parseInt(timeDurationStr);
	        }

	        // For image handling, additional logic would be required here.
	        
	        // Database details
	        String dbURL = "jdbc:mysql://localhost:3306/foodiechef";
	        String dbUser = "root"; // Your DB user
	        String dbPassword = "root"; // Your DB password

	        // SQL statement to insert new recipe
	        String sql = "INSERT INTO recipes (dish_name, ingredients, procedure_text, time_duration) VALUES (?, ?, ?, ?)";

	        try {
	            // Load JDBC driver
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // Try-with-resources to ensure closure of database resources
	            try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
	                 PreparedStatement statement = conn.prepareStatement(sql)) {
	                
	                statement.setString(1, dishName);
	                statement.setString(2, ingredients);
	                statement.setString(3, procedureText);
	                statement.setInt(4, timeDuration);

	                int rowsInserted = statement.executeUpdate();
	                if (rowsInserted > 0) {
	                    response.getWriter().print("Recipe added successfully.");
	                } else {
	                    response.getWriter().print("Failed to add the recipe.");
	                }
	            }
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	            response.getWriter().print("JDBC Driver not found.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.getWriter().print("Database error.");
	        }
	}

}
