import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.*;
import java.util.Calendar;

public class ImportVotsMunicipals {
    static int codiElectoral;
    static int codiCandidatura;
    static int codiIneMun;
    static int numVots;

    public static void llegir() {

        BufferedReader bfLector = null;

        try {

            //Obtenim el directori actual
            Path pathActual = Paths.get(System.getProperty("user.dir"));

            //Concatenem el directori actual amb un subdirectori "temp" i afegim el fitxer "06021904.DAT"
            Path pathFitxer =  Paths.get(pathActual.toString(),  "temp", "06021904.DAT");

            //objReader = new BufferedReader(new FileReader(pathFitxer.toString()));
            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;

            while((strLinia = bfLector.readLine()) !=null) {
                //Definir longitud dels camps
                String municipi_id = strLinia.substring(11, 14);
                String districte = strLinia.substring(14, 16);
                String candidatura_id = strLinia.substring(16,22);
                String vots = strLinia.substring(22,30);

                if (!districte.equals("99")){
                    codiIneMun = Integer.parseInt(municipi_id);
                    codiCandidatura = Integer.parseInt(candidatura_id);
                    numVots = Integer.parseInt(vots);
                } else continue;
                insert();
            }

        }catch(IOException e){
            e.printStackTrace();
        } finally{
            try{
                if(bfLector != null)
                    bfLector.close();
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    static void insert(){
        try{
            Connection con = ConnexioDBGrup2.getConnection();

            int codiEleccio = selectEleccioId(2019, 4);
            int codiMunicipi = selectMunicipi(codiIneMun);
            int candidaturaId = selectCandidaturaId(codiCandidatura);

            String query = " INSERT INTO vots_candidatures_mun (eleccio_id, municipi_id, candidatura_id, vots)"
                    + " values (?, ?, ?, ?)";

            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, codiEleccio);
            preparedStmt.setInt(2, codiMunicipi);
            preparedStmt.setInt(3, candidaturaId);
            preparedStmt.setInt(4, numVots);


            //execute the preparedstatement
            preparedStmt.execute();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static int selectMunicipi(int codiIneM) {

        int a = 0;
        int ine = codiIneM;
        try {
            Connection con = ConnexioDBGrup2.getConnection();
            //Sentència SELECT: es prepara sentència amb paràmetres
            String query = "SELECT municipi_id " +
                    " FROM municipis " +
                    "WHERE codi_ine = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, ine);

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                a = rs.getInt("municipi_id");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return a;
    }

    public static int selectCandidaturaId(int codicandidatura){
        int a = 0;
        int CodiCandidatura = codicandidatura;

        try{
            Connection con = ConnexioDBGrup2.getConnection();

            //Sentència SELECT: es prepara sentència amb paràmetres
            String query = "SELECT candidatura_id " +
                    "FROM candidatures " +
                    "WHERE codi_candidatura = ?" ;
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,CodiCandidatura);

            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()){
                a=rs.getInt("candidatura_id");
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return a;
    }

    public static int selectEleccioId(int year,int month){
        int a = 0;
        try{
            Connection con = ConnexioDBGrup2.getConnection();

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
