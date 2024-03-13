

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
import java.sql.SQLException;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  PrintWriter out = response.getWriter();

          String username = request.getParameter("Username");
          String dob = request.getParameter("Dob");
          String email = request.getParameter("Email");
          String password = request.getParameter("Password");

          Connection conn = null;
          PreparedStatement stmt = null;

          try {              Class.forName("com.mysql.jdbc.Driver");

              conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodiechef", "root", "root");

               String sql = "INSERT INTO details (Username, dob, Email, Password) VALUES ( ?, ?, ?, ?)";

              stmt = conn.prepareStatement(sql);
              stmt.setString(1, username);
              stmt.setString(2, dob);
              stmt.setString(3, email);
              stmt.setString(4, password);
              int rowsAffected = stmt.executeUpdate();

              if (rowsAffected > 0) {
            	  out.println("Registration ok!");
            	  response.sendRedirect("login.html");
              } else {
                  out.println("Registration failed!");
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
