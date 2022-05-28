package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;


public class DataBaser {

    public static void addNewAccountToDB(long number, int pin) {
        String sql = "INSERT INTO card(number,pin) VALUES (?,?)";

        try(Connection conn = DataBaser.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(number));
            pstmt.setString(2, String.valueOf(pin));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static Connection connect() throws SQLException {
        String url = "jdbc:sqlite:" + Main.pathToDB;
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void connectByStart() {
        String url = "jdbc:sqlite:" + Main.pathToDB;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try(Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                // Statement execution
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY," +
                        "number TEXT NOT NULL," +
                        "pin TEXT NOT NULL," +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
