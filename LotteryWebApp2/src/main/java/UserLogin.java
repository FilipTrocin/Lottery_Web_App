import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;


@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {

    private Connection conn;
    private Statement stmt;
    private PreparedStatement loginStatement;
    private PreparedStatement detailsStatement;

    private static String hashedPassword;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "user";
        String PASS = "password";

        // URLs to connect to database depending on your development approach
        // (NOTE: please change to option 1 when submitting)

        // 1. use this when running everything in Docker using docker-compose
//        String DB_URL = "jdbc:mysql://db:3306/lottery";

        // 2. use this when running tomcat server locally on your machine and mysql database server in Docker
        String DB_URL = "jdbc:mysql://localhost:33333/lottery";

        // 3. use this when running tomcat and mysql database servers on your machine
        //String DB_URL = "jdbc:mysql://localhost:3306/lottery";

        // returns current session if exist, if not it won't create a new one
        HttpSession session = request.getSession(false);

        // going through all attributes present in a session
        Enumeration<String> enumeration = session.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String att = (String) enumeration.nextElement();
            // delete attribute if it is not keypair or attempt
            if (!att.equals("keypair") && !att.equals("attempt")) {
                session.removeAttribute(att);
            }
        }

            // get input provided by user in login form
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            try {
                // create database connection and statement
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                setHashedPassword(Hasher.generateSHA2(password));

                // check in database whether login credential put by user exist
                loginStatement = conn.prepareStatement("SELECT * FROM userAccounts WHERE Username = ? AND Pwd = ?");
                loginStatement.setString(1, username);
                loginStatement.setString(2, getHashedPassword());

                // select firstname, lastname, email, place attributes of the particular user to display them in account.jsp section
                detailsStatement = conn.prepareStatement("SELECT Firstname, Lastname, Email, Place FROM userAccounts WHERE Username = ? AND Pwd = ?");
                detailsStatement.setString(1, username);
                detailsStatement.setString(2, getHashedPassword());

                ResultSet credentials = loginStatement.executeQuery();
                ResultSet data = detailsStatement.executeQuery();

                if (credentials.next()) {

                    StringBuilder place = new StringBuilder();

                    // add uHashedPassword to the session if does not exist
                    if (session.getAttribute("uHashedPassword") == null) {
                        session.setAttribute("uHashedPassword", hashedPassword);
                    }

                    if (data.next()) {
                        // reading data from `data` ResultSet
                        String firstname = data.getString("Firstname");
                        String lastname = data.getString("Lastname");
                        String email = data.getString("Email");
                        place.append(data.getString("Place"));

                        // setting attributes to the session
                        session.setAttribute("uFirstname", firstname);
                        session.setAttribute("uLastname", lastname);
                        session.setAttribute("uEmail", email);
                        session.setAttribute("uUsername", username);
                    }


                    // check user's place from the session and based on that redirect to the particular panel
                    switch (place.toString()){
                        case "PUBLIC":
                            // redirect to account.jsp page
                            RequestDispatcher usrDispatcher = request.getRequestDispatcher("/account.jsp");
                            request.setAttribute("message", "login success!");
                            usrDispatcher.forward(request, response);
                            break;
                        case "ADMIN":
                            // redirect to admin_hime.jsp page
                            RequestDispatcher admDispatcher = request.getRequestDispatcher("/admin/admin_home.jsp");
                            try {
                                // create database connection and statement
                                Class.forName(JDBC_DRIVER);
                                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                                stmt = conn.createStatement();

                                // query database and get results
                                ResultSet rs = stmt.executeQuery("SELECT * FROM userAccounts");

                                // create HTML table text
                                String content = "<table border='1' cellspacing='2' cellpadding='2' width='100%' align='left'>" +
                                        "<tr><th>First name</th><th>Last name</th><th>Email</th><th>Phone number</th><th>Username</th>" +
                                        "<th>Password</th><th>Place</th></tr>";

                                // add HTML table data using data from database
                                while (rs.next()) {
                                    content += "<tr><td>"+ rs.getString("Firstname") + "</td>" +
                                            "<td>" + rs.getString("Lastname") + "</td>" +
                                            "<td>" + rs.getString("Email") + "</td>" +
                                            "<td>" + rs.getString("Phone") + "</td>" +
                                            "<td>" + rs.getString("Username") + "</td>" +
                                            "<td>" + rs.getString("Pwd") + "</td>" +
                                            "<td>" + rs.getString("Place") + "</td></tr>";
                                }
                                // finish HTML table text
                                content += "</table>";

                                // close connection
                                conn.close();

                                // add table as a data attribute as a request
                                request.setAttribute("data", content);

                                // pass request and response attributes
                                admDispatcher.forward(request, response);
                                break;
                            }

                            catch (Exception se) {
                                se.printStackTrace();
                                // display error.jsp page with given message if successful
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                                request.setAttribute("message", "Database Error, Please try again");
                                dispatcher.forward(request, response);
                            } finally {
                                try {
                                    if (stmt != null)
                                        stmt.close();
                                } catch (SQLException se2) {
                                }
                                try {
                                    if (conn != null)
                                        conn.close();
                                } catch (SQLException se) {
                                    se.printStackTrace();
                                }
                            }
                        }
                    }

                    else {
                        // get attribute attempt from the session
                        int attemptsLeft = (int) session.getAttribute("attempt");

                        // redirect to the error page, cut down amount of attempts left
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                        String message = String.format("login unsuccessful! You have %d attempts left", attemptsLeft - 1);
                        request.setAttribute("message", message);
                        session.setAttribute("attempt", --attemptsLeft);
                        dispatcher.forward(request, response);

                    }
                    conn.close();

                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                    // redirect to the error page
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                    dispatcher.forward(request, response);
                    request.setAttribute("message", "Something went wrong, please try again");

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (loginStatement != null)
                            loginStatement.close();
                    } catch (SQLException se2) {}

                    try {
                        if (conn != null)
                            conn.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                    try {
                        if (detailsStatement != null)
                            detailsStatement.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public static void setHashedPassword(String hashedPassword) {
        UserLogin.hashedPassword = hashedPassword;
    }
    public String getHashedPassword() {
        return hashedPassword;
    }
}
