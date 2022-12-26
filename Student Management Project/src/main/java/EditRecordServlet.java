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

@WebServlet("/edit")
public class EditRecordServlet extends HttpServlet {
	private final static String query = "update user set fname=?,lname=?,gender=?,mobile=?,birthdate=?,city=? where id=?";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        
        int id = Integer.parseInt(req.getParameter("id"));
        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");
        String gender = req.getParameter("gender");
        String mobile = req.getParameter("mobile");
        String birthdate = req.getParameter("birthdate");
        String city = req.getParameter("city");
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch(Exception e) {
            e.printStackTrace();
        }

        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "V@tsal04");
                PreparedStatement ps = con.prepareStatement(query);){
            //set the values
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, gender);
            ps.setString(4, mobile);
            ps.setString(5, birthdate);
            ps.setString(6, city);
            ps.setInt(7, id);
            
            int count = ps.executeUpdate();
            pw.println("<div class='card' style='margin:auto;width:300px;margin-top:100px'>");
            if(count==1) {
                pw.println("<h2 class='bg-danger text-light text-center'>Record Edited Successfully</h2>");
            }else {
                pw.println("<h2 class='bg-danger text-light text-center'>Record Not Edited</h2>");
            }
        }catch(SQLException se) {
            pw.println("<h2 class='bg-danger text-light text-center'>"+se.getMessage()+"</h2>");
            se.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        pw.println("<a href='Home.html'><button class='btn btn-outline-success'>Home</button></a>");
        pw.println("&nbsp; &nbsp;");
        pw.println("<a href='showdata'><button class='btn btn-outline-success'>Show User</button></a>");
        pw.println("</div>");
        pw.close();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req,res);
    }
}
