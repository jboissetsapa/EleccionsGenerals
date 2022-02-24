import java.sql.*;

public class ConnexioDBGrup2 {

    // Propietats
    private static Connection conn = null;
    private final String driver = "com.mysql.cj.jdbc.Driver"; //com.mysql.jdbc.Driver
    private final String url;
    private final String usuari ="perepi";
    private final String contrasenya = "pastanaga";
    private final String host = "192.168.56.101";
    private final String base_dades = "Eleccions_Generals_GrupB";

    // Constructors
    private ConnexioDBGrup2(){

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
