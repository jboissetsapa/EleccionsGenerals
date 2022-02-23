package projecte.sapa.apartat1;

import java.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class ImportProvincies {

    static int codiComunAuto,ineProvin,numEscons,ineComunAut;
    static String nom;

    static void introducir(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.102:3306/Eleccions_Generals_GrupB","perepi","pastanaga");

            // the mysql insert statement
            String query = " INSERT INTO provincies (comunitat_aut_id,nom,codi_ine,num_escons)"
                    + " values (?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, codiComunAuto);
            preparedStmt.setString (2, nom);
            preparedStmt.setInt (3, ineProvin);
            preparedStmt.setInt(4,numEscons);

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
                if (ineProvin != 99) {
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

        ineComunAut = Integer.parseInt(linea.substring(9,11));
        ineProvin = Integer.parseInt(linea.substring(11,13));
        nom = linea.substring(14,64).trim();
        numEscons = Integer.parseInt(linea.substring(149,155));
    }

    static void select(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.102:3306/Eleccions_Generals_GrupB","perepi","pastanaga");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT * " +
                    " FROM comunitats_autonomes " +
                    "WHERE codi_ine = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,ineComunAut);


            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                codiComunAuto =  rs.getInt("comunitat_aut_id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e);}
    }

}
