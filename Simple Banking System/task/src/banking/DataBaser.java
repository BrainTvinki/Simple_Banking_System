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

    public static int loggInDB(long cardNumber, int pinCode) {
        String sql = "SELECT id FROM card WHERE number = ? AND pin = ?";
        ResultSet resultSet;
        try(Connection conn = DataBaser.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, cardNumber);
            pstmt.setInt(2, pinCode);
            resultSet = pstmt.executeQuery();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            return 0;
        }
    }

    public static int getIdByCardNumber(Long cardNumber) {
        String sql = "SELECT id FROM card WHERE number = ?";
        ResultSet resultSet;
        try(Connection conn = DataBaser.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, cardNumber);
            resultSet = pstmt.executeQuery();
            if(resultSet != null) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            return 0;
        }
    }

    public static long getCardNumber(int idCard){
        String sql = "SELECT number FROM card WHERE id = ?";
        ResultSet resultSet;
        try(Connection conn = DataBaser.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCard);
            resultSet = pstmt.executeQuery();
            if(resultSet != null) {
                return resultSet.getLong(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public static int getCardPin(int idCard){
        String sql = "SELECT pin FROM card WHERE id = ?";
        ResultSet resultSet;
        try(Connection conn = DataBaser.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCard);
            resultSet = pstmt.executeQuery();
            if(resultSet != null) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static int getBalance(int idCard) {
        String sql = "SELECT balance FROM card WHERE id = ?";
        ResultSet resultSet;
        try(Connection conn = DataBaser.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCard);
            resultSet = pstmt.executeQuery();
            if(resultSet != null) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            return 0;
        }
        //return 0;
    }

    public static boolean setBalance(long money,int idCard) {
        String sql = "UPDATE card SET balance = ? WHERE id = ?";
        try(Connection conn = DataBaser.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, money);
            pstmt.setInt(2, idCard);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteAccount(int idCard){
        String sql = "DELETE FROM card WHERE id = ?";
        try(Connection conn = DataBaser.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCard);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
