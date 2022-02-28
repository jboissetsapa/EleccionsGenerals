package projecte_base_dades.apartat1;

import projecte_base_dades.ConnexioDBGrup2;
import java.sql.*;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import projecte_base_dades.apartat4.DescomprimirZip;
import java.nio.file.*;


public class importCandidatures {


    static String zipId = DescomprimirZip.zipId;
    static int candidatura_id, eleccio_id;
    static String codi_candidatura, nom_curt, nom_llarg,codi_acomulacio_provincia,codi_acomulacio_ca,codi_acomulacio_nacional;
    // Llegir les dades

    // Executar amb llegir()
    public static void llegir() {

        BufferedReader bfLector = null;
        try {

            //Obtenim el directori actual
            Path pathActual = Paths.get(System.getProperty("user.dir"));

            //Concatenem el directori actual amb un subdirectori "dades" i afegim el fitxer "03021911.DAT"
            Path pathFitxer = Paths.get(pathActual.toString(), "temp", "03"+ zipId + ".DAT");

            //objReader = new BufferedReader(new FileReader(pathFitxer.toString()));

            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;
            while ((strLinia = bfLector.readLine()) != null) {
                codi_candidatura = strLinia.substring(8, 14);
                nom_curt = strLinia.substring(14, 64).trim();
                nom_llarg = strLinia.substring(64, 214).trim();
                codi_acomulacio_provincia = strLinia.substring(214,220);
                codi_acomulacio_ca = strLinia.substring(220,226);
                codi_acomulacio_nacional = strLinia.substring(226,232);
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

    private static void insert() {
        PreparedStatement preparedStmt = null;
        try {
            Connection con= ConnexioDBGrup2.getConnection();


            //Preparem el Date
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

            // the mysql insert statement
            String query = " INSERT INTO candidatures (codi_candidatura,nom_curt,nom_llarg,eleccio_id,codi_acumulacio_provincia,codi_acumulacio_ca,codi_acumulario_nacional)"
                    + " values (?, ?, ?, ?,?,?,?)";

            // create the mysql insert preparedstatement
            preparedStmt = con.prepareStatement(query);

            preparedStmt.setString(1, codi_candidatura);
            preparedStmt.setString(2, nom_curt);
            preparedStmt.setString(3, nom_llarg);
            int year = 2000 + Integer.parseInt(zipId.substring(2, 4));
            int month = Integer.parseInt(zipId.substring(4));
            int a = selectEleccioId(year,month);
            preparedStmt.setInt(4,a);
            preparedStmt.setString(5,codi_acomulacio_provincia);
            preparedStmt.setString(6,codi_acomulacio_ca);
            preparedStmt.setString(7,codi_acomulacio_nacional);


            // execute the preparedstatement
            preparedStmt.execute();

        } catch (Exception e) {
            System.out.println(e);
            System.out.println(preparedStmt.toString());
        }
    }

    public static int selectEleccioId(int year,int month) {
        int a = 0;
        try{
            Connection con= ConnexioDBGrup2.getConnection();

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT eleccio_id " +
                    " FROM eleccions " +
                    "WHERE any = ? AND mes = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,year);
            preparedStmt.setInt(2,month);

            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                a = rs.getInt("eleccio_id");

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return a;

    }
}
