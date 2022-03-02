package projecte_base_dades;

import java.sql.*;

public class ConnexioDBGrup2 {

    // Propietats
    private static Connection conn = null;
    String driver = "com.mysql.cj.jdbc.Driver"; //com.mysql.jdbc.Driver
    String url;
    String usuari ="perepi";
    String contrasenya = "pastanaga";
    String host = "192.168.56.101";
    String base_dades = "Eleccions_Generals_GrupB";

    // Constructors
    ConnexioDBGrup2(){

        this.url = "jdbc:mysql://" + host + ":3306/" + base_dades;

        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, usuari, contrasenya);
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    // Mètodes
    public static Connection getConnection() {

        if (conn == null){
            new ConnexioDBGrup2();
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
