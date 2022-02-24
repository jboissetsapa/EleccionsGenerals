package cat.sapa;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Calendar;
import java.io.FileReader;

public class ImportarCandidats {

    static int provincia_id,provinciaId , persona_id;
    static int candidatura_id,candidaturaID;
    static String nom, cog1, cog2;
    public static void ImportarCandidats() {

        BufferedReader bfLector = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/Eleccions_Generals_GrupB ", "perepi", "pastanaga");

            String strLinia;

            //Preparem el Date
            Calendar calendar = Calendar.getInstance();
            Date startDate = new Date(calendar.getTime().getTime());
            Path pathActual = Paths.get(System.getProperty("user.dir"));
             Path pathFitxer = Paths.get(pathActual.toString(), "temp", "04"+ DescomprimirZip.zipId +".DAT");
            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            while ((strLinia = bfLector.readLine()) != null) {



                //candidats
                int    num_ordre_id;

                candidatura_id = Integer.parseInt(strLinia.substring(15, 21));
                provincia_id = Integer.parseInt( strLinia.substring(9, 11));
                persona_id = Integer.parseInt(strLinia.substring(12,16));
                nom = strLinia.substring(25,50).trim();
                cog1 = strLinia.substring(50,75).trim();
                cog2 = strLinia.substring(75,100).trim();
                num_ordre_id = Integer.parseInt( strLinia.substring(21, 24));
                String tipus = strLinia.substring(24, 25);


                selectProvincies();
                selectCandidatures();
                selectPersones();




                // the mysql insert statement
                String query = " INSERT INTO candidats (candidatura_id,persona_id,provincia_id,num_ordre,tipus)"
                        + " values ( ?, ?, ?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setInt(1, candidaturaID);
                preparedStmt.setInt(2, persona_id);
                preparedStmt.setInt(3, provinciaId);
                preparedStmt.setInt(4, num_ordre_id);
                preparedStmt.setString(5,tipus);


                // execute the preparedstatement
                preparedStmt.execute();



            }
        //Tanquem la conexio
            con.close();

        }catch (Exception e) {
            System.out.println(e);
        }
    }


    static void selectProvincies(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/Eleccions_Generals_GrupB","perepi","pastanaga");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT * " +
                    " FROM provincies " +
                    "WHERE codi_ine = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);


            preparedStmt.setInt(1,provincia_id);

            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                provinciaId =  rs.getInt("provincia_id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e);}
    }

    static void selectCandidatures(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/Eleccions_Generals_GrupB","perepi","pastanaga");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT * " +
                    " FROM candidatures " +
                    "WHERE codi_candidatura = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);


            preparedStmt.setInt(1,candidatura_id);

            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                candidaturaID =  rs.getInt("candidatura_id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e);}
    }

    static void selectPersones(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/Eleccions_Generals_GrupB","perepi","pastanaga");

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

            while(rs.next()) {
                persona_id = rs.getInt("persona_id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e);}
    }

}

