package projecte_base_dades.apartat1;

import projecte_base_dades.ConnexioDBGrup2;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;

public class IntroduirDadesBasiques {

    public static void dadesBasicIntro(){
        try {
            Connection con= ConnexioDBGrup2.getConnection();

            String[] nomsEleccio = {"Referendum","Elecciones al Congreso de los Diputados", "Elecciones al Senado","Elecciones Municipales","Elecciones Autonómicas", "Elecciones a Cabildos Insulares", "Elecciones al Parlamento Europeo","Elecciones a Partidos Judiciales y Diputaciones Provinciales","Elecciones a Juntas Generales"};
            File f = new File("origen");
            String[] zips = f.list();
            String nomFitxer = zips[0];
            String nomEleccio = nomFitxer.substring(0,2);
            switch (nomEleccio){
                case "01":
                    nomEleccio = nomsEleccio[0];
                    break;
                case "02":
                    nomEleccio = nomsEleccio[1];
                    break;
                case "03":
                    nomEleccio = nomsEleccio[2];
                    break;
                case "04":
                    nomEleccio = nomsEleccio[3];
                    break;
                case "05":
                    nomEleccio = nomsEleccio[4];
                    break;
                case "06":
                    nomEleccio = nomsEleccio[5];
                    break;
                case "07":
                    nomEleccio = nomsEleccio[6];
                    break;
                case "08":
                    nomEleccio = nomsEleccio[7];
                    break;
                case "09":
                    nomEleccio = nomsEleccio[8];
                    break;
            }
            String data = nomFitxer.substring(2,8);
            data = data.substring(0,4) + "-" + data.substring(4) + "-01";
            //Preparem el Date
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
            // the mysql insert statement
            String query = " INSERT INTO eleccions (nom,data)"
                    + " values ( ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, nomEleccio);
            preparedStmt.setDate (2, Date.valueOf(data));
            // execute the preparedstatement
            preparedStmt.execute();

        }catch(Exception e){
            System.out.println(e);
        }

    }
}
