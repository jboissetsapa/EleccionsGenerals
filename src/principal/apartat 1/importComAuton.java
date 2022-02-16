package projecte_base_dades.activitat;

import java.sql.*;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class importComAuton {


    public static int codiComunAuto,codiProvinciaINE;
    public static String nom;
    public static int p = 1;

    public static void introducir(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.102:3306/Eleccions_Generals_GrupB","perepi","pastanaga");


            //Preparem el Date
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

            // the mysql insert statement
            String query = " INSERT INTO comunitats_autonomes (comunitat_aut_id,nom,codi_ine)"
                    + " values (?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,p);
            preparedStmt.setString (2, nom);
            preparedStmt.setInt (3, codiComunAuto);




            // execute the preparedstatement
            preparedStmt.execute();

            //Tanquem la connexió
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }



    public static void importar(){
        BufferedReader bfLector = null;
        try {

            //Obtenim el directori actual
            Path pathActual = Paths.get(System.getProperty("user.dir"));

            //Concatenem el directori actual amb un subdirectori "dades" i afegim el fitxer "03021911.DAT"
            Path pathFitxer = Paths.get(pathActual.toString(), "temp", "07021904.DAT");

            //objReader = new BufferedReader(new FileReader(pathFitxer.toString()));

            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;
            while ((strLinia = bfLector.readLine()) != null) {
                separar(strLinia);
                if (codiProvinciaINE == 99 && codiComunAuto != 99) {
                    introducir();
                    p++;
                }
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



    public static void separar(String linea){
        try{

        }catch (NumberFormatException e){

        }
        codiComunAuto = stringAInt(linea.substring(9,11));
        codiProvinciaINE = stringAInt(linea.substring(11,13));
        nom = linea.substring(14,64).trim();

    }


    public static int stringAInt (String s) {
        int x;
        try {
            x = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            x = 0;
        }
        return x;
    }

}
