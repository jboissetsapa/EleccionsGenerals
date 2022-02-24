import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;

public class ImportEleccions {

    public static void dadesBasicIntro(){
        try {
            Connection con = ConnexioDBGrup2.getConnection();

            //Preparem el Date
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
            //modificar para que al leer la string lo pase a formato date, la fila de arriba nada mas da la fecha actual
            // the mysql insert statement
            String query = " INSERT INTO eleccions (nom,data)"
                    + " values ( ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, "Eleccions al congreso de los diputados");
            preparedStmt.setDate (2, Date.valueOf("2019-04-01"));
            // execute the preparedstatement
            preparedStmt.execute();

        }catch(Exception e){
            System.out.println(e);
        }

    }
}