package projecte_base_dades.apartat1;

import projecte_base_dades.ConnexioDBGrup2;
import projecte_base_dades.apartat4.DescomprimirZip;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ImportarPersones {

    static String nom,nom0, cog1, cog2;

    public static void ImportarPersones() {
        BufferedReader bfLector = null;
        try {
            Connection con= ConnexioDBGrup2.getConnection();
            String strLinia;
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Path pathActual = Paths.get(System.getProperty("user.dir"));
           Path pathFitxer = Paths.get(pathActual.toString(), "temp", "04"+ DescomprimirZip.zipId +".DAT");
            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);


                //persone


                while ((strLinia = bfLector.readLine()) != null) {

                     nom = strLinia.substring(25, 50).trim();
                     cog1 = strLinia.substring(50, 75).trim();
                     cog2 = strLinia.substring(75, 100).trim();
                    String sexe = (strLinia.substring(101, 102));
                    selectPersones();

                    if (nom0.equals(" ")) {
                        //Preparem el Date
                        Calendar calendar = Calendar.getInstance();
                        java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
                        String query = " INSERT INTO persones (nom,cog1,cog2,sexe)"
                                + " values (  ?, ?, ?, ?)";


                        // create the mysql insert preparedstatement
                        PreparedStatement preparedStmt = con.prepareStatement(query);

                        preparedStmt.setString(1, nom);
                        preparedStmt.setString(2, cog1);
                        preparedStmt.setString(3, cog2);
                        preparedStmt.setString(4, sexe);


                        // execute the preparedstatement
                        preparedStmt.execute();

                    }

                }

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    static void selectPersones(){
        try {
            Connection con= ConnexioDBGrup2.getConnection();
            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT * " +
                    " FROM persones " +
                    "WHERE nom = ? && cog1 = ? && cog2 = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);


            preparedStmt.setString(1,nom);
            preparedStmt.setString(2,cog1);
            preparedStmt.setString(3,cog2);


            ResultSet rs = preparedStmt.executeQuery();

            nom0 = " ";

            while(rs.next()) {
                nom0 = rs.getString("nom");
                nom0 += rs.getString("cog1");
                nom0 += rs.getString("cog2");
            }
        }
        catch(Exception e){
            System.out.println(e);}
    }





    }

