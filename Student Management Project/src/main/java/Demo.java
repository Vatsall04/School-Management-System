import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Demo extends HttpServlet {
	private final static String query = "select fname, lname, gender, mobile, birthdate, city from students; ";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		resp.setContentType("text/html");
		
		pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		try(Connection con = DriverManager.getConnection("jdbc.mysql://localhost:3306/school","root","V@tsal04");
			PreparedStatement ps = con.prepareStatement(query);)
		{
			ResultSet rs = ps.executeQuery(query);
		} catch (SQLException se) {
			pw.println("<h2>"+se.getMessage()+"</h2>");
			se.printStackTrace();
		}
		
	}
}
