package projecte_base_dades.apartat1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;

public class IntroduirDadesBasiques {

    public static void dadesBasicIntro(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.217.130:3306/Eleccions_Generals_GrupB", "perepi", "pastanaga");

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

            //Tanquem la connexió
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }

    }
}
