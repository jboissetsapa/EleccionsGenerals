package projecte_base_dades.apartat5;

import projecte_base_dades.ConnexioDBGrup2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;

public class lleidHondt {
    public static void resultatProvincies(String nomProvincia) {
        try {


            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = ConnexioDBGrup2.getConnection();


            String query = "SELECT p.nom, sum(em.vots_emesos), sum(em.vots_blanc), sum(em.vots_nuls) FROM eleccions_municipis em INNER JOIN municipis m on m.municipi_id = em.municipi_id INNER JOIN provincies p  on m.provincia_id = p.provincia_id  GROUP BY p.nom HAVING p.nom = ?";


            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, nomProvincia);
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                String nomProv = rs.getString(1);
                int votsEmesos = rs.getInt(2);
                int votsBlanc = rs.getInt(3);
                int votsNuls = rs.getInt(4);
                System.out.println("PROVINCIA " + nomProv.toUpperCase(Locale.ROOT));
                System.out.println("----------------------");
                System.out.println("Vots: " + votsEmesos);
                System.out.println("Vots blanc: " + votsBlanc);
                System.out.println("Vots nuls: " + votsNuls);


            }

            query = "SELECT c.nom_curt, vp.candidats_obtinguts FROM candidatures c INNER JOIN vots_candidatures_prov vp ON c.candidatura_id = vp.candidatura_id INNER JOIN provincies p ON p.provincia_id = vp.provincia_id WHERE p.nom = ? ORDER BY vp.candidats_obtinguts DESC";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, nomProvincia);
            rs = preparedStmt.executeQuery();

            while (rs.next()) {
                String nomCandidautra = rs.getString(1);
                int escons = rs.getInt(2);
                System.out.println(nomCandidautra + ": " + escons);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
