import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.Arrays;

@WebServlet("/CheckMatch")
public class CheckMatch extends HttpServlet {
    private Connection conn;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "user";
        String PASS = "password";
        String DB_URL = "jdbc:mysql://localhost:33333/lottery";


        PreparedStatement stmt;

        StringBuilder winningNumber = new StringBuilder();

        HttpSession session = request.getSession(false);
        // get array of draws from session
        String[] userNumbers = (String[]) session.getAttribute("draws");


        try {
            // create database connection and statement
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // select winning draw from matchedDraw table
            stmt = conn.prepareStatement("SELECT Draw FROM matchedDraw");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                // make a winning draw in form of the string
                winningNumber.append(rs.getString("Draw"));
            }

            for(int i=0; i<userNumbers.length; i++) {
                // if winning draw is the same as the draw the user put display congratulations message
                if (winningNumber.toString().equals(userNumbers[i])) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
                    String message = String.format("Congratulations! %s is a winning draw!", userNumbers[i]);
                    request.setAttribute("message", message);
                    dispatcher.forward(request, response);
                    // if winning draw was found, exit the loop
                    break;
                }
            }
            // display the message below in accounts.jsp, pass request and response to accounts.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
            request.setAttribute("message", "What a shame! You don't have any matches this time");
            dispatcher.forward(request, response);

            conn.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // redirect to error page, display error message
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
            request.setAttribute("message", "Something went wrong, please try again");
        }finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
