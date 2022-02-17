package projecte_base_dades.activitat;


import java.sql.*;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class importarProvincies {

    public static int codiComunAuto,ineProvin,numEscons,ineComunAut;
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
            String query = " INSERT INTO provincies (provincia_id,comunitat_aut_id,nom,codi_ine,num_escons)"
                    + " values (?,?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,p);
            preparedStmt.setInt (2, codiComunAuto);
            preparedStmt.setString (3, nom);
            preparedStmt.setInt (4, ineProvin);
            preparedStmt.setInt(5,numEscons);



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
        ineComunAut = stringAInt(linea.substring(9,11));
        ineProvin = stringAInt(linea.substring(11,13));
        nom = linea.substring(14,64).trim();
        numEscons = stringAInt(linea.substring(149,155));

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


    public static void select(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.102:3306/Eleccions_Generals_GrupB","perepi","pastanaga");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT * " +
                    " FROM comunitats_autonomes " +
                    "WHERE codi_ine = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            //Preparem les dates
            Calendar cDataInici = Calendar.getInstance();
            cDataInici.add(Calendar.YEAR, -50);

            Calendar cDataFi = Calendar.getInstance();
            cDataFi.add(Calendar.DATE,1);

            java.sql.Date dataInici = new java.sql.Date(cDataInici.getTime().getTime());
            java.sql.Date dataFi = new java.sql.Date(cDataFi.getTime().getTime());

            preparedStmt.setInt(1,ineComunAut);


            ResultSet rs = preparedStmt.executeQuery();

            /*
            Sentència sense paràmetres.
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM empleats");
            */
            /* while(rs.next()) Obtenir els valors per índex de columnes.
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));*/

            while(rs.next()) {
                codiComunAuto =  rs.getInt("comunitat_aut_id");
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e);}
    }

}
