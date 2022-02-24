import java.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class ImportMunicipis {

    static int codiIne,provinciaId,ineProvin,districteMunicipal;
    static String nom;

    static void introducir(){

        try {
            Connection con = ConnexioDBGrup2.getConnection();

            // the mysql insert statement
            String query = " INSERT INTO municipis (nom,codi_ine,provincia_id,districte)"
                    + " values (?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, nom);
            preparedStmt.setInt (2, codiIne);
            preparedStmt.setInt (3,provinciaId);
            preparedStmt.setInt(4,districteMunicipal);


            // execute the preparedstatement
            preparedStmt.execute();
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
            Path pathFitxer = Paths.get(pathActual.toString(), "temp", "05021904.DAT");

            //objReader = new BufferedReader(new FileReader(pathFitxer.toString()));

            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;
            while ((strLinia = bfLector.readLine()) != null) {
                separar(strLinia);
                if (districteMunicipal == 99) {
                    select();
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
        ineProvin = Integer.parseInt(linea.substring(11,13));
        codiIne = Integer.parseInt(linea.substring(13,16));
        districteMunicipal = Integer.parseInt(linea.substring(16,18));
        nom = linea.substring(18,118).trim();
    }

    static void select(){
        try {
            Connection con = ConnexioDBGrup2.getConnection();

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT * " +
                    " FROM provincies " +
                    "WHERE codi_ine = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setInt(1,ineProvin);

            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                provinciaId =  rs.getInt("provincia_id");
            }
        }
        catch(Exception e){
            System.out.println(e);}
    }
}
