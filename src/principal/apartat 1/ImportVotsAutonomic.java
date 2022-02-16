package ImportVotsAutonomic;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.*;
import java.util.Calendar;

public class ImportVotsAutonomic {
    static String codiComunitatAutonoma;
    static String codiCandidatura;
    static String vots;
    public static void lligirText(){
        BufferedReader bfLector = null;

        try {

            //Obtenim el directori actual
            Path pathActual = Paths.get(System.getProperty("user.dir"));

            //Concatenem el directori actual amb un subdirectori "temp" i afegim el fitxer "03021911.DAT"
            Path pathFitxer = Paths.get(pathActual.toString(), "../DMLAc1/EleccionsGenerals/temp", "08021904.DAT");

            //objReader = new BufferedReader(new FileReader(pathFitxer.toString()));

            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;
            //todo comunitat_autonoma_id foreing key de comunitats_autonomes.comunitat_aut_id select usando codi_ine en el where sera 99
            //todo candidatura_id fereing key de candidatures.candidatura_id buscar haceiendo select usando codi_candidatura en el where
            while ((strLinia = bfLector.readLine()) != null) {
                codiCandidatura = strLinia.substring(15,20);
                vots = strLinia.substring(21,28);
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

            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.217.130:3306/Eleccions_Generals_GrupB", "perepi", "pastanaga");

            //Preparem el Date
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
            //modificar para que al leer la string lo pase a formato date, la fila de arriba nada mas da la fecha actual
            // the mysql insert statement
            String query = " INSERT INTO vots_candidatures_ca (comunitat_autonoma_id,candidatura_id,vots)"
                    + " values ( ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, selectComunitatAutonomaId());
            preparedStmt.setInt (2, selectCandidaturaId(codiCandidatura));
            preparedStmt.setInt (3, Integer.parseInt(vots));
            // execute the preparedstatement
            preparedStmt.execute();

            //Tanquem la connexió
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public static int selectComunitatAutonomaId() {
        int a = 0;
        int ine = 99;
        try{


            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.217.130:3306/Eleccions_Generals_GrupB", "perepi", "pastanaga");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT comunitat_aut_id " +
                    " FROM comunitats_autonomes " +
                    "WHERE codi_ine != ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,ine);


            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                a = rs.getInt("comunitat_aut_id");

            }

        } catch (ClassNotFoundException | SQLException throwables) {
            throwables.printStackTrace();
        }

        return a;

    }
    public static int selectCandidaturaId(String codiCandidatura) {
        int a = 0;
        int codCandidatura = Integer.parseInt(codiCandidatura);
        try{


            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.217.130:3306/Eleccions_Generals_GrupB", "perepi", "pastanaga");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT candidatura_id " +
                    " FROM candidatures " +
                    "WHERE codi_candidatura = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,codCandidatura);


            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                a = rs.getInt("candidatura_id");

            }

        } catch (ClassNotFoundException | SQLException throwables) {
            throwables.printStackTrace();
        }

        return a;

    }
}
