import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import java.nio.file.*;
import java.sql.*;
import java.util.Calendar;

public class VotsProvincials {

    public static void main(String[] args) {


        BufferedReader bfLector = null;
        try {

            //Obtenim el directori actual
            Path pathActual = Paths.get(System.getProperty("user.dir"));

            //Concatenem el directori actual amb un subdirectori "dades" i afegim el fitxer "05021904.DAT"
            Path pathFitxer = Paths.get(pathActual.toString(), "dades", "08021904.DAT");

            //objReader = new BufferedReader(new FileReader(pathFitxer.toString()));

            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;
            while ((strLinia = bfLector.readLine()) != null) {

                String comunidad_id = strLinia.substring(9, 11);
                String provincia = strLinia.substring(11, 13);
                String candidatura = strLinia.substring(14,20);
                String vots = strLinia.substring(20,28);
                String candidats_obtinguts = strLinia.substring(28,33);

                int provincia_id; int candidatura_id; int numVots; int numCandidats_obtinguts;

                if (!comunidad_id.equals("99")){
                    provincia_id = Integer.parseInt(provincia);
                    candidatura_id = Integer.parseInt(candidatura);
                    numVots = Integer.parseInt(vots);
                    numCandidats_obtinguts = Integer.parseInt(candidats_obtinguts);
                } else continue;



                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/Eleccions_Generals_GrupB","perepi","pastanaga");



                    // the mysql insert statements
                    String query = " INSERT INTO vots_candidatures_prov (provincia_id,candidatura_id,vots,candidats_obtinguts)"
                            + " values (?, ?, ?, ?)";

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    preparedStmt.setInt    (1, provincia_id);
                    preparedStmt.setInt (2, candidatura_id);
                    preparedStmt.setInt (3, numVots);
                    preparedStmt.setInt (4, numCandidats_obtinguts);

                    // execute the preparedstatement
                    preparedStmt.execute();

                    //Tanquem la connexió
                    con.close();
                }
                catch(Exception e){
                    System.out.println(e);
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
}
