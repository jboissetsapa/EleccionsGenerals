package projecte_base_dades.apartat1;

import java.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class ImportEleccionsMunicpis {

    static int numMeses,cens,votsBlanc,votsNull,votsCandidat,districteMunicipal,codiIne,ineProvin,provinciaId,municipiId,any,mes,eleccioId;

    static void introducir(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //***************************************************
            Connection con= DriverManager.getConnection("jdbc:mysql://192.168.56.102:3306/Eleccions_Generals_GrupB","perepi","pastanaga");

            // the mysql insert statement
            String query = " INSERT INTO eleccions_municipis (eleccio_id,municipi_id,num_meses,cens,vots_candidatures,vots_blanc,vots_nuls)"
                    + " values (?,?,?,?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, eleccioId);
            preparedStmt.setInt (2, municipiId);
            preparedStmt.setInt (3, numMeses);
            preparedStmt.setInt (4, cens);
            preparedStmt.setInt (5, votsCandidat);
            preparedStmt.setInt (6, votsBlanc);
            preparedStmt.setInt (7, votsNull);

            // execute the preparedstatement
            preparedStmt.execute();

            //Tanquem la connexió***********************************
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
            Path pathFitxer = Paths.get(pathActual.toString(), "temp", "05"+ DescomprimirZip.zipId +".DAT");

            //objReader = new BufferedReader(new FileReader(pathFitxer.toString()));

            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;
            while ((strLinia = bfLector.readLine()) != null) {
                separar(strLinia);
                if (districteMunicipal == 99) {
                    selectProvincia();
                    selectMunicipi();
                    selectEleccio();
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
        any = Integer.parseInt(linea.substring(2,6));
        mes = Integer.parseInt(linea.substring(6,8));
        numMeses = Integer.parseInt(linea.substring(136,141));
        cens = Integer.parseInt(linea.substring(141,149));
        votsBlanc = Integer.parseInt(linea.substring(189,197));
        votsNull = Integer.parseInt(linea.substring(197,205));
        votsCandidat = Integer.parseInt(linea.substring(205,213));
        districteMunicipal = Integer.parseInt(linea.substring(16,18));
        codiIne = Integer.parseInt(linea.substring(13,16));
        ineProvin = Integer.parseInt(linea.substring(11,13));
    }


    static void selectProvincia(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.102:3306/Eleccions_Generals_GrupB","perepi","pastanaga");

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
            con.close();
        }
        catch(Exception e){
            System.out.println(e);}
    }


    static void selectMunicipi(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.102:3306/Eleccions_Generals_GrupB","perepi","pastanaga");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT * " +
                    " FROM municipis " +
                    "WHERE codi_ine = ? && provincia_id = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setInt(1,codiIne);
            preparedStmt.setInt(2,provinciaId);

            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                municipiId =  rs.getInt("municipi_id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e);}
    }

    static void selectEleccio(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.102:3306/Eleccions_Generals_GrupB","perepi","pastanaga");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT * " +
                    " FROM eleccions " +
                    "WHERE any = ? && mes = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setInt(1,any);
            preparedStmt.setInt(2,mes);

            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                eleccioId =  rs.getInt("eleccio_id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e);}
    }
}
