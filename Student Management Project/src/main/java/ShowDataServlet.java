import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/showdata")
public class ShowDataServlet extends HttpServlet {
	private final static String query = "select id, fname, lname, gender, mobile, birthdate, city from students";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");

		pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "V@tsal04");
				PreparedStatement ps = con.prepareStatement(query);) {

			ResultSet rs = ps.executeQuery(query);
			pw.println("<div style='margin:auto; width:800px; margin-top:50px;'>");
			pw.println("<table class='table table-hover table-striped'>");
			pw.println("<tr>");
			pw.println("<th>ID</th>");
			pw.println("<th>First Name </th>");
			pw.println("<th>Last Name</th>");
			pw.println("<th>Gender</th>");
			pw.println("<th>Mobile Number</th>");
			pw.println("<th>Birthdate</th>");
			pw.println("<th>City</th>");
			pw.println("<th>Edit</th>");
			pw.println("<th>Delete</th>");
			pw.println("</tr>");

			while (rs.next()) {
				pw.println("<tr>");
				pw.println("<td>" + rs.getInt(1) + "</td>");
				pw.println("<td>" + rs.getString(2) + "</td>");
				pw.println("<td>" + rs.getString(3) + "</td>");
				pw.println("<td>" + rs.getString(4) + "</td>");
				pw.println("<td>" + rs.getString(5) + "</td>");
				pw.println("<td>" + rs.getString(6) + "</td>");
				pw.println("<td>" + rs.getString(7) + "</td>");
				pw.println("<td><a href='editurl?id=" + rs.getInt(1) + "'>Edit</a></td>");
				pw.println("<td><a href='deleteurl?id=" + rs.getInt(1) + "'>Delete</a></td>");
				pw.println("<tr>");
			}
			pw.println("</table>");
		} catch (SQLException se) {
			pw.println("<h2 class='bg-danger text-light text-center'>" + se.getMessage() + "</h2>");
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		pw.println("<a href='Home.html'><button class='btn btn-outline-success'>Home</button></a>");
		pw.println("<div>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
