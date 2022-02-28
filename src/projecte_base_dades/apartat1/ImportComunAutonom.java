package projecte_base_dades.apartat1;

import projecte_base_dades.ConnexioDBGrup2;
import projecte_base_dades.apartat4.DescomprimirZip;
import java.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class ImportComunAutonom {
    static int codiComunAuto,codiProvinciaINE;
    static String nom;

    static void introducir(){

        try {
            Connection con= ConnexioDBGrup2.getConnection();
            // the mysql insert statement
            String query = " INSERT INTO comunitats_autonomes (nom,codi_ine)"
                    + " values (?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, nom);
            preparedStmt.setInt (2, codiComunAuto);

            // execute the preparedstatement
            preparedStmt.execute();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void importar(){
        BufferedReader bfLector = null;
        try {

            //Obtenim el directori actual
            Path pathActual = Paths.get(System.getProperty("user.dir"));

            //Concatenem el directori actual amb un subdirectori "dades" i afegim el fitxer "03021911.DAT"
            Path pathFitxer = Paths.get(pathActual.toString(), "temp", "07"+ DescomprimirZip.zipId +".DAT");

            //objReader = new BufferedReader(new FileReader(pathFitxer.toString()));
            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;
            while ((strLinia = bfLector.readLine()) != null) {
                separar(strLinia);
                if (codiProvinciaINE == 99 && codiComunAuto != 99) {
                    introducir();
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

    static void separar(String linea){
        codiComunAuto = Integer.parseInt(linea.substring(9,11));
        codiProvinciaINE = Integer.parseInt(linea.substring(11,13));
        nom = linea.substring(14,64).trim();
    }
}
