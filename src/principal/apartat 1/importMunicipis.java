package projecte_base_dades.activitat;


import java.sql.*;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
public class importMunicipis {

    static int codiIne,provinciaId,ineProvin,districteMunicipal;
    static String nom;
    static int municipiId = 1;

    static void introducir(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //***************************************************
            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.102:3306/Eleccions_Generals_GrupB","perepi","pastanaga");


            //Preparem el Date
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

            // the mysql insert statement
            String query = " INSERT INTO municipis (municipi_id,nom,codi_ine,provincia_id,districte)"
                    + " values (?,?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,municipiId);
            preparedStmt.setString (2, nom);
            preparedStmt.setInt (3, codiIne);
            preparedStmt.setInt (4,provinciaId);
            preparedStmt.setInt(5,districteMunicipal);



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
            Path pathFitxer = Paths.get(pathActual.toString(), "temp", "05021904.DAT");

            //objReader = new BufferedReader(new FileReader(pathFitxer.toString()));

            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;
            while ((strLinia = bfLector.readLine()) != null) {
                separar(strLinia);
                if (districteMunicipal == 99) {
                    select();
                    introducir();
                    municipiId++;
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
        ineProvin = stringAInt(linea.substring(11,13));
        codiIne = stringAInt(linea.substring(13,16));
        districteMunicipal = stringAInt(linea.substring(16,18));
        nom = linea.substring(18,118).trim();




    }


    static int stringAInt (String s) {
        int x;
        try {
            x = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            x = 0;
        }
        return x;
    }


    static void select(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.102:3306/Eleccions_Generals_GrupB","perepi","pastanaga");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT * " +
                    " FROM provincies " +
                    "WHERE codi_ine = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            //Preparem les dates
            Calendar cDataInici = Calendar.getInstance();
            cDataInici.add(Calendar.YEAR, -50);

            Calendar cDataFi = Calendar.getInstance();
            cDataFi.add(Calendar.DATE,1);

            java.sql.Date dataInici = new java.sql.Date(cDataInici.getTime().getTime());
            java.sql.Date dataFi = new java.sql.Date(cDataFi.getTime().getTime());

            preparedStmt.setInt(1,ineProvin);


            ResultSet rs = preparedStmt.executeQuery();

            /*
            Sentència sense paràmetres.
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM empleats");
            */
            /* while(rs.next()) Obtenir els valors per índex de columnes.
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));*/


            while(rs.next()) {
                provinciaId =  rs.getInt("provincia_id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e);}
    }


}


