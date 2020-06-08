
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    // Declaring the variables
    private static String Url;
    private static Connection Connect = null;
    public static PreparedStatement PreStatement;
    public static ResultSet Result_Set;

    /*
	 * This function is establishing a connection
	 * between sqlite database and screen
     */
    public Connection GetConnection() {

        if (Connect == null) {
            try {
                Url = "jdbc:sqlite:Database/Assets.db";
                Connect = DriverManager.getConnection(Url);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return Connect;

    }

    /*
	 * This function is creating tables for us to
	 * store data
     */
    public Database() {

        String Users = "CREATE TABLE IF NOT EXISTS Users ( "
                + "Username text PRIMARY KEY NOT NULL,"
                + "FirstName text,"
                + "LastName text,"
                + "Phone text,"
                + "Password text,"
                + "Role text"
                + ");";

        try {

            Statement Stmt = GetConnection().createStatement();
            Stmt.execute(Users);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static String generateRandomPassword(int len) {
        // ASCII range - alphanumeric (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // each iteration of loop choose a character randomly from the given ASCII range
        // and append it to StringBuilder instance
        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

}
