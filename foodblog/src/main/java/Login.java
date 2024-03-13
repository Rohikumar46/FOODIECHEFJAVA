

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("serial")
@WebServlet("/Login")
public class Login extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		PrintWriter out = response.getWriter();

		// Initializing PreparedStatement and Connection
		PreparedStatement stmt = null;
		Connection conn = null;

		try {

		    Class.forName("com.mysql.jdbc.Driver");

		    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodiechef", "root", "root");


		    String sql = "SELECT * FROM details WHERE Username = ? AND Password = ?";
		    stmt = conn.prepareStatement(sql);

		    stmt.setString(1, username);
		    stmt.setString(2, password);
		    

		    ResultSet result = stmt.executeQuery();

		    if (result.next()) {
		    	response.sendRedirect("food.html");
		    	
		    } else {

		        response.sendRedirect("/login.html");
		    }

	
		} catch (ClassNotFoundException | SQLException e) {
		    e.printStackTrace();
		    out.println("<h2>Error: " + e.getMessage() + "</h2>");
		} finally {
		
		    try {
		        if (stmt != null) {
		            stmt.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}

	}

}
