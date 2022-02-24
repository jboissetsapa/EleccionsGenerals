package votsmunicipals;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.*;
import java.util.Calendar;

public class VotsMunicipals {
    static String codiDistricteElectoral;
    static String codiCandidatura;
    static String codiIneMun;
    static String vots;

    public static void llegir() {

        BufferedReader bfLector = null;

            try {

                //Obtenim el directori actual
                Path pathActual = Paths.get(System.getProperty("user.dir"));

                //Concatenem el directori actual amb un subdirectori "temp" i afegim el fitxer "06021904.DAT"
                Path pathFitxer =  Paths.get(pathActual.toString(),  "../trabajo dml/votsmunicipals", "08021904.DAT");

                //objReader = new BufferedReader(new FileReader(pathFitxer.toString()));
                bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
                String strLinia;

                while((strLinia = bfLector.readLine()) !=null) {
                    //Definir longitud dels camps
                    codiIneMun = strLinia.substring(12,15);
                     codiDistricteElectoral=strLinia.substring(13,14)
                       codiIneMun = strLinia.substring(12,15);
                       codiCandidatura = strLinia.substring(14,20);
                       vots = strLinia.substring(20,28);
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
                       Class.forName("com.mysql.cj.jdbc.Driver");
                       Connection con = DriverManager.getConnection("jdbc:mysql://192.168.108.129:3306/Eleccions_Generals_GrupB","perepi","pastanaga");
                       String query = " INSERT INTO vots_candidatures_mun (eleccio_id, municipi_id, candidatura_id, vots)"
                               + " values (?, ?, ?, ?)";

                       PreparedStatement preparedStmt = con.prepareStatement(query);
                       preparedStmt.setInt(1, selectMunicipi());
                       preparedStmt.setInt(2, selectEleccioId());
                       preparedStmt.setInt(3, selectCandidaturaId());
                       preparedStmt.setInt(4, Integer.parseInt(vots));


                       //execute the preparedstatement
                       preparedStmt.execute();

                       //Es tanca connexió
                       con.close();
                   }
                  catch(Exception e){
                       System.out.println(e);
                  }
                }

                public static int selectMunicipi(String codiIneM) {

                    int a = 0;
                    int ine = Integer.parseInt(codiIneM);
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql:// 192.168.108.129:3306 /Eleccions_Generals_GrupB", "perepi", "pastanaga");

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

                    } catch (ClassNotFoundException | SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    return a;
                }

                public static int selectCandidaturaId(String codicandidatura){
                int a = 0;
                int CodiCandidatura = Integer.parseInt(codicandidatura);

                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql:// 192.168.108.129:3306 /Eleccions_Generals_GrupB", "perepi", "pastanaga");

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
                }catch (ClassNotFoundException | SQLException throwables){
                    throwables.printStackTrace();
                }

                return a;
                }

                public static int selectEleccioId(String codieleccio){
                int a = 0;
                int CodiEleccio = Integer.parseInt(codieleccio);

                    try{
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql:// 192.168.108.129:3306 /Eleccions_Generals_GrupB", "perepi", "pastanaga");

                        //Sentència SELECT: es prepara sentència amb paràmetres
                        String query = "SELECT eleccio_id " +
                                "FROM eleccions " +
                                "WHERE codi_eleccio = ?" ;
                        PreparedStatement preparedStmt = con.prepareStatement(query);
                        preparedStmt.setInt(1,CodiEleccio);

                        ResultSet rs = preparedStmt.executeQuery();

                        while(rs.next()){
                            a=rs.getInt("eleccio_id");
                        }
                    }catch (ClassNotFoundException | SQLException throwables){
                        throwables.printStackTrace();
                    }

                    return a;
    }

}



