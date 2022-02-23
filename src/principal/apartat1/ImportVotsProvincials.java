package projecte_base_dades.apartat1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import java.nio.file.*;
import java.sql.*;
import java.util.Calendar;

public class ImportVotsProvincials {

    static int provincia_id, provincia_ine, candidatura_id, candidatura_ine, numVots, numCandidats_obtinguts;

    public static void llegir() {

        BufferedReader bfLector = null;
        try {

            //Obtenim el directori actual
            Path pathActual = Paths.get(System.getProperty("user.dir"));

            //Concatenem el directori actual amb un subdirectori "dades" i afegim el fitxer "05021904.DAT"
            Path pathFitxer = Paths.get(pathActual.toString(), "dades", "08021904.DAT");

            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;
            while ((strLinia = bfLector.readLine()) != null) {

                //Defineix la longintud dels camps
                String comunidad_id = strLinia.substring(9, 11);
                String provincia = strLinia.substring(11, 13);
                String candidatura = strLinia.substring(14,20);
                String vots = strLinia.substring(20,28);
                String candidats_obtinguts = strLinia.substring(28,33);

                //Fa una comprovacio i converteix a enter l'String estret.
                if (!comunidad_id.equals("99") && !provincia.equals("99")){
                    provincia_ine = Integer.parseInt(provincia);
                    candidatura_ine = Integer.parseInt(candidatura);
                    numVots = Integer.parseInt(vots);
                    numCandidats_obtinguts = Integer.parseInt(candidats_obtinguts);
                } else continue;
                insert();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bfLector != null)
                    bfLector.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    static void insert(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/Eleccions_Generals_GrupB","perepi","pastanaga");

            selectProvincies();
            selectComunitats();

            // the mysql insert statements
            String query = " INSERT INTO vots_candidatures_prov (provincia_id,candidatura_id,vots,candidats_obtinguts)"
                    + " values (?, ?, ?, ?)";

            // Inserta en la tabla les dades.
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, provincia_id);
            preparedStmt.setInt (2, candidatura_id);
            preparedStmt.setInt (3, numVots);
            preparedStmt.setInt (4, numCandidats_obtinguts);

            // execute the preparedstatement
            preparedStmt.execute();

            //Tanquem la connexió
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    static void selectProvincies() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/Eleccions_Generals_GrupB","perepi","pastanaga");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT * " +
                    " FROM provincies " +
                    "WHERE codi_ine = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,provincia_ine);

            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                provincia_id = rs.getInt("provincia_id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e);}
    }

    static void selectComunitats() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/Eleccions_Generals_GrupB","perepi","pastanaga");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT * " +
                    " FROM candidatures " +
                    "WHERE codi_candidatura = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,candidatura_ine);

            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                candidatura_id = rs.getInt("candidatura_id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e);}
    }

}
