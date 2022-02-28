package projecte_base_dades;

import org.apache.commons.io.FileUtils;
import projecte_base_dades.apartat1.*;
import projecte_base_dades.apartat4.DescomprimirZip;

import java.io.IOException;


public class Main {

    public static void main(String[] args) {
        int bucle = 1;
        boolean descomprimit;
        descomprimit = DescomprimirZip.descomprimirZip();
        ImportarPersones.ImportarPersones();
        
        boolean descomprimit;
        try {
            while (!FileUtils.isEmptyDirectory(DescomprimirZip.origin)) {
                descomprimit = DescomprimirZip.descomprimirZip();
                if (bucle == 1) {
                    ImportComunAutonom.importar();
                    System.out.println("Importació de comunitats autònomes fet");
                    ImportProvincies.importar();
                    System.out.println("Importació de provincies fet");
                    ImportMunicpis.importar();
                    System.out.println("Importació de municipis fet");
                }

                IntroduirDadesBasiques.dadesBasicIntro();
                System.out.println("Importació de eleccions fet");
                ImportEleccionsMunicpis.importar();
                System.out.println("Importació de eleccions municipals fet");
                ImportCandidatures.llegir();
                System.out.println("Importació de candidatures fet");
                ImportarPersones.ImportarPersones();
                System.out.println("Importació de persones fet");
                ImportarCandidats.ImportarCandidats();
                System.out.println("Importació de candidats fet");
                ImportVotsAutonomic.lligirText();
                System.out.println("Importació de vots autonòmics fet");
                ImportVotsProvincials.llegir();
                System.out.println("Importació de vots provincials fet");
                VotsMunicipals.llegir();
                System.out.println("Importació de vots municipals fet");

                DescomprimirZip.procesarZips(descomprimit);
                bucle++;
            }
        } catch (IOException x){
        }

    }
}
