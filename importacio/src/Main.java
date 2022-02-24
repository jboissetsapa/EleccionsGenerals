import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import java.nio.file.*;
import java.sql.*;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) {
        
        ImportComAutonomes.importar();
        System.out.println("Importació de comunitats autònomes fet");
        ImportProvincies.importar();
        System.out.println("Importació de provincies fet");
        ImportMunicipis.importar();
        System.out.println("Importació de municipis fet");
        ImportEleccions.dadesBasicIntro();
        System.out.println("Importació de eleccions fet");
        ImportCandidatures.llegir();
        System.out.println("Importació de candidatures fet");
        ImportPersones.ImportarPersones();
        System.out.println("Importació de persones fet");
        ImportEleccionsMun.importar();
        System.out.println("Importació de eleccions municipals fet");
        ImportVotsAutonomics.lligirText();
        System.out.println("Importació de vots autonòmics fet");
        ImportVotsProvincials.llegir();
        System.out.println("Importació de vots provincials fet");
        ImportVotsMunicipals.llegir();
        System.out.println("Importació de vots municipals fet");
        System.out.println("\nProcés complet!");

    }
}
