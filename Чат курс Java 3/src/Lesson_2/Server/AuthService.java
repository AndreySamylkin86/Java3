package Lesson_2.Server;

import java.sql.*;

public class AuthService {
    private static Connection connection;
    private static Statement stmt;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void disconnect() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        try {
            String sql = String.format("select nickname from main where login = '%s' and password = '%s'", login, pass);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static boolean addUser (String login, String pass, String nickname) {

        try {
            Statement stmt = connection.createStatement();
            String query = String.format("INSERT INTO 'main' ('login','password','nickname' ) "
                    + "VALUES ('%s', '%s', '%s')", login, pass, nickname);

            stmt.execute(query);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
        public static String changeNick(String oldNick, String newNick) {
            try {
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery("select nickname from main where nickname = '" + oldNick + "'");

                if (resultSet.next()) {
                    stmt.executeUpdate("update main  SET nickname = '" + newNick + "' where nickname = '" + oldNick + "'");
                }
                resultSet = stmt.executeQuery("select nickname from main  where nickname = '" + newNick + "'");
                if (resultSet.next()) {
                }

                return resultSet.getString("nickname");


            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
}
