import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Servlet")
public class RegisterServlet extends HttpServlet {
	private final static String query = "insert into students(fname, lname, gender, mobile, birthdate, city) value(?,?,?,?,?,?)";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");

		pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");

		String fname = req.getParameter("fname");
		String lname = req.getParameter("lname");
		String gender = req.getParameter("gender");
		String birthdate = req.getParameter("birthdate");
		String mobile = req.getParameter("mobile");
		String city = req.getParameter("city");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "V@tsal04");
				PreparedStatement ps = con.prepareStatement(query);) {
			ps.setString(1, fname);
			ps.setString(2, lname);
			ps.setString(3, gender);
			ps.setString(4, mobile);
			ps.setString(5, birthdate);
			ps.setString(6, city);
			int count = ps.executeUpdate();

			pw.println("<div class='card' style='margin:auto; width:300px; margin-top:100px;>");
			if (count == 1) {
				pw.println("<h2 class='bg-danger text-white card-header	'>Record registered successfully.</h2>");
			} else {
				pw.println("<h2 class='bg-danger text-light text-center'>Record not registered.</h2>");
			}
		} catch (SQLException se) {
			pw.println("<h2 class='bg-danger text-light text-center'>" + se.getMessage() + "</h2>");
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		pw.println("<a href='Home.html'><button class='btn btn-outline-success'>Home</button></a>");
		pw.println("</div>");
		pw.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
